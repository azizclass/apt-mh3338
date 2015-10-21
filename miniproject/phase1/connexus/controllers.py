#!/usr/bin/env python

import datetime
import logging
import jinja2
import json
import os
import webapp2
from google.appengine.api import blobstore, images, mail, users
from google.appengine.ext import ndb
from google.appengine.ext.webapp import blobstore_handlers
from models import Image, SearchTerm, Stream, TrendingEmailConfig

LOGIN_TEMPLATE = 'views/login.html'
CREATE_STREAM_TEMPLATE = 'views/create.html'
VIEW_STREAM_TEMPLATE = 'views/create.html'
SECS_PER_HOUR = 3600

JINJA_ENVIRONMENT = jinja2.Environment(
    loader=jinja2.FileSystemLoader(os.path.dirname(__file__)),
    extensions=['jinja2.ext.autoescape'],
    autoescape=True
)


class ConnexusHandler(webapp2.RequestHandler):
    def template(self, template, **template_values):
        template = JINJA_ENVIRONMENT.get_template(template)
        self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
        self.response.write(template.render(template_values))

    def login_page(self):
        self.template(CREATE_STREAM_TEMPLATE,
                      login_url=users.create_login_url('/'))

    def create_stream_page(self):
        self.template('views/create.html')


class MainPage(ConnexusHandler):
    def get(self):
        self.login_page()


class CreatePage(ConnexusHandler):
    def get(self):
        user = users.get_current_user()
        if not user:
            self.login_page()
        else:
            self.create_stream_page()

    def post(self):
        user = users.get_current_user()
        if not user:
            self.login_page()
        else:
            name = self.request.get('name')
            message = self.request.get('message')
            key = ndb.Key('Stream', name)
            stream = key.get()
            if stream:
                self.template('views/error.html',
                              error='Error: you tried to create a new stream ' +
                                     'whose name is the same as an existing ' +
                                     'stream; operation did not complete')
            else:
                subscribers = filter(lambda s: s != '', map(unicode.lower,
                                     map(unicode.strip,
                                     self.request.get('subscribers').split(','))
                                     ))
                message = self.request.get('message')
                tags = filter(lambda t: t != '', map(unicode.strip,
                              self.request.get('tags').split(',')))
                cover_image_url = self.request.get('cover_image_url')
                cover_image_url = cover_image_url if cover_image_url \
                    else 'http://placehold.it/190x190.png'
                stream = Stream(id=name, name=name, owner=user.email().lower(),
                                subscribers=subscribers, tags=tags,
                                cover_image_url=cover_image_url,
                                images=[])
                body = ('You are now subscribed to the %s stream:\n' +
                        'http://mh33338-connexus.appspot.com/view?' +
                        'stream_name=%s&increment=1\n%s' % (name, message))
                sender_address = 'Matthew Halpern <matthalp@gmail.com>'
                subject = '[mh33338-conexus] Subscription to %s Stream' % name
                stream.put()
                for subscriber in subscribers:
                    mail.send_mail(sender_address, subscriber, subject, body)
                self.redirect('/manage')


class ViewPage(webapp2.RequestHandler):
    def get(self):
        stream_name = self.request.get('stream_name')
        if stream_name:
            key = ndb.Key('Stream', stream_name)
            stream = key.get()
        else:
            stream = None
        if stream:
            increment = self.request.get('increment')
            if increment:
                stream.views += int(increment)
                stream.recent_views.append(datetime.datetime.now())
                stream.put()
            cursor = self.request.get('cursor')
            cursor = int(cursor) if cursor else 0
            upload_url = blobstore.create_upload_url('/upload_photo')
            template_values = {
                'stream': stream,
                'cursor': cursor,
                'upload_url': upload_url
            }
            template = JINJA_ENVIRONMENT.get_template('views/view-stream.html')
            self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
            self.response.write(template.render(template_values))
        else:
            streams = Stream.query().order(Stream.date).fetch()
            template_values = {
                'streams': streams,
            }
            template = JINJA_ENVIRONMENT.get_template('views/view-streams.html')
            self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
            self.response.write(template.render(template_values))


class ManagePage(webapp2.RequestHandler):
    def get(self):
        user = users.get_current_user()
        if not user:
            template_values = {
                'login_url': users.create_login_url(self.request.uri)
            }
            template = JINJA_ENVIRONMENT.get_template('views/login.html')
            self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
            self.response.write(template.render(template_values))
        else:
            owned_streams = (
                Stream.query(Stream.owner == user.email().lower())
                .order(-Stream.name).fetch()
            )
            subscribed_streams = (
                Stream.query(Stream.subscribers == user.email().lower())
                .order(-Stream.name).fetch()
            )
            template_values = {
                'owned_streams': owned_streams,
                'subscribed_streams': subscribed_streams
            }
            template = JINJA_ENVIRONMENT.get_template('views/manage.html')
            self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
            self.response.write(template.render(template_values))


class UnsubscribeToStream(webapp2.RequestHandler):
    def post(self):
        user = users.get_current_user()
        if user:
            stream_names = self.request.POST.getall('stream')
            if stream_names:
                stream_names = self.request.POST.getall('stream')
                if stream_names:
                    streams = Stream.query(Stream.name.IN(stream_names)).fetch()
                    for stream in streams:
                        stream.subscribers.remove(user.email().lower())
                        stream.put()
            self.redirect('/manage')
        else:
            template_values = {
                'error': 'Error: user not logged in.'
            }
            template = JINJA_ENVIRONMENT.get_template('views/error.html')
            self.response.headers['Content-Type'] = \
                'text/html; charset=utf-8'
            self.response.write(template.render(template_values))


class SubscribeToStream(webapp2.RequestHandler):
    def post(self):
        user = users.get_current_user()
        if user:
            stream_names = self.request.POST.getall('stream')
            if stream_names:
                streams = Stream.query(Stream.name.IN(stream_names)).fetch()
                for stream in streams:
                    stream.subscribers.append(user.email().lower())
                    stream.put()
            self.redirect('/view?stream_name=%s' % stream_names[0])
        else:
            template_values = {
                'error': 'Error: user not logged in.'
            }
            template = JINJA_ENVIRONMENT.get_template('views/error.html')
            self.response.headers['Content-Type'] = \
                'text/html; charset=utf-8'
            self.response.write(template.render(template_values))


class DeleteStream(webapp2.RequestHandler):
    def post(self):
        user = users.get_current_user()
        if user:
            stream_names = self.request.POST.getall('stream')
            if stream_names:
                streams = Stream.query(Stream.name.IN(stream_names)).fetch()
                ndb.delete_multi(map(lambda s: s.key, streams))
            self.redirect('/manage')
        else:
            template_values = {
                'error': 'Error: user not logged in.'
            }
            template = JINJA_ENVIRONMENT.get_template('views/error.html')
            self.response.headers['Content-Type'] = \
                'text/html; charset=utf-8'
            self.response.write(template.render(template_values))


class PhotoUploadHandler(blobstore_handlers.BlobstoreUploadHandler):
    def post(self):
        try:
            logging.info("ONE")
            upload = self.get_uploads()[0]
            logging.info("TWO")
            stream_name = self.request.get('stream_name')
            logging.info("THREE")
            key = ndb.Key('Stream', stream_name)
            logging.info("FOUR")
            stream = key.get()
            logging.info("FIVE")
            image = Image(url=images.get_serving_url(upload.key()),
                          photo_key=upload.key(), comment="")
            logging.info("SIX")
            stream.images.insert(0, image)
            logging.info("SEVEN")
            stream.put()
            logging.info("EIGHT")
            self.response.write(images.get_serving_url(upload.key()))
        except:
            pass


class SearchPage(webapp2.RequestHandler):
    def get(self):
        query = self.request.get('query')
        if query:
            streams = Stream.query().order(Stream.date).fetch()
            matches = filter(lambda s: query in s.name or query in s.tags,
                             streams)[:5]
        else:
            query = ''
            matches = []
        template_values = {
            'query': query,
            'matches': matches
        }
        template = JINJA_ENVIRONMENT.get_template('views/search.html')
        self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
        self.response.write(template.render(template_values))


class TrendingPage(webapp2.RequestHandler):
    def get(self):
        streams = Stream.query().fetch()
        sorted_streams = sorted(streams, key=lambda s: len(s.recent_views),
                                reverse=True)
        config = TrendingEmailConfig.query().fetch(1)[0]
        if not config:
            config = TrendingEmailConfig()
        template_values = {
            'streams': sorted_streams[:3],
            'email_mode': config.mode
        }
        template = JINJA_ENVIRONMENT.get_template('views/trending.html')
        self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
        self.response.write(template.render(template_values))


class SocialPage(webapp2.RequestHandler):
    def get(self):
        template_values = {}
        template = JINJA_ENVIRONMENT.get_template('views/social.html')
        self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
        self.response.write(template.render(template_values))


class TrendingEmail(webapp2.RequestHandler):
    def get(self):
        MINUTES_PER_TICK = 5
        TICKS_PER_HOUR = 60 / MINUTES_PER_TICK
        TICKS_PER_DAY = 24 * TICKS_PER_HOUR
        config = TrendingEmailConfig.query().fetch(1)[0]
        if config:
            if config.mode == "none":
                config.ticks = 0
            elif config.mode == "minutes":
                config.ticks = 0
            elif config.mode == "hourly":
                config.ticks += 1
                config.ticks = config.ticks % TICKS_PER_HOUR
            elif config.mode == "daily":
                config.ticks += 1
                config.ticks = config.ticks % TICKS_PER_DAY
            if config.mode != "none" and config.ticks == 0:
                sender_address = 'Matthew Halpern <matthalp@gmail.com>'
                subject = '[mh33338-conexus] Trending'
                streams = Stream.query().fetch()
                sorted_streams = sorted(streams, key=lambda s: len(s.recent_views),
                                reverse=True)
                body = "Trending Streams:\n\n"
                for idx, stream in enumerate(sorted_streams[:3]):
                    body += "%d. %s %s" % (idx + 1, stream.name,
                                           "http://mh33338-connexus.appspot" +
                                           ".com/view?stream_name=%s" %
                                           stream.name + "&increment=1\n")
                mail.send_mail(sender_address, "nima.dini@utexas.edu", subject,
                               body)

    def post(self):
        config = TrendingEmailConfig.query().fetch(1)[0]
        if not config:
            config = TrendingEmailConfig()
        if self.request.get("none"):
            config.mode = "none"
        elif self.request.get("minutes"):
            config.mode = "minutes"
        elif self.request.get("hourly"):
            config.mode = "hourly"
        elif self.request.get("daily"):
            config.mode = "daily"
        config.put()
        self.redirect('/trending')


class LeaderboardCalculation(webapp2.RequestHandler):
    def get(self):
        streams = Stream.query().fetch()
        logging.info("STREAMS %d" % len(streams))
        now = datetime.datetime.now()
        for stream in streams:
            stream.recent_views = filter(lambda rv: (now - rv).seconds < SECS_PER_HOUR,
                                         stream.recent_views)
            stream.put()


class SearchIndexing(webapp2.RequestHandler):
    def update_term(self, term, stream_name):
        key = ndb.Key('SearchTerm', term)
        search_term = key.get()
        if not search_term:
            search_term = SearchTerm(term=term, stream_names=[stream_name])
        else:
            terms = search_term.stream_names.append(stream_name)
            search_term.stream_names = list(set(terms))
        search_term.put()

    def get(self):
        streams = Stream.query().order(Stream.date).fetch()
        for stream in streams:
            self.update_term(stream.name, stream.name)
            for tag in stream.tags:
                self.update_term(tag, stream.name)


class GeoView(webapp2.RequestHandler):
    def get(self):
        stream_name = self.request.get('stream_name')
        if stream_name:
            key = ndb.Key('Stream', stream_name)
            stream = key.get()
        else:
            stream = None
        if stream:
            increment = self.request.get('increment')
            if increment:
                stream.views += int(increment)
                stream.recent_views.append(datetime.datetime.now())
                stream.put()
            template_values = {
                'stream': stream
            }
            template = JINJA_ENVIRONMENT.get_template('views/geo.html')
            self.response.headers['Content-Type'] = 'text/html; charset=utf-8'
            self.response.write(template.render(template_values))
        else:
            self.template('views/error.html',
                          error='Error: stream does not exist')


class SearchTerms(webapp2.RequestHandler):
    def get(self):
        term = self.request.get('term')
        terms = map(lambda t: t.term,
                    SearchTerm.query().order(SearchTerm.term).fetch())
        matches = list(set(filter(lambda t: term in t, terms)))
        self.response.headers['Content-Type'] = 'application/json'
        self.response.out.write(json.dumps({"matches": matches}))
