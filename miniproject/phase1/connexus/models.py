#!/usr/bin/env python

from google.appengine.ext import ndb


class Image(ndb.Model):
    """Sub model for representing a stream image."""
    date = ndb.DateTimeProperty(auto_now_add=True)
    url = ndb.StringProperty(indexed=False)
    photo_key = ndb.BlobKeyProperty(indexed=False)
    comment = ndb.StringProperty(indexed=False)


class Stream(ndb.Model):
    """Sub model for representing a stream."""
    date = ndb.DateTimeProperty(auto_now_add=True)
    name = ndb.StringProperty(indexed=True)
    subscribers = ndb.StringProperty(repeated=True)
    tags = ndb.StringProperty(indexed=False, repeated=True)
    cover_image_url = ndb.StringProperty(indexed=False)
    images = ndb.StructuredProperty(Image, indexed=False, repeated=True)
    views = ndb.IntegerProperty(indexed=False, default=0)
    recent_views = ndb.DateTimeProperty(indexed=False, repeated=True)
    owner = ndb.StringProperty()


class TrendingEmailConfig(ndb.Model):
    """Sub model for storing trending e-mail information."""
    mode = ndb.StringProperty(indexed=False, default="none")
    ticks = ndb.IntegerProperty(indexed=False, default=0)
