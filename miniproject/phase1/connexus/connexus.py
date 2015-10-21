import webapp2

import controllers

app = webapp2.WSGIApplication([
    ('/',  controllers.ManagePage),
    ('/create',  controllers.CreatePage),
    ('/manage',  controllers.ManagePage),
    ('/view',  controllers.ViewPage),
    ('/delete',  controllers.DeleteStream),
    ('/geo',  controllers.GeoView),
    ('/index',  controllers.SearchIndexing),
    ('/leaderboard_calc',  controllers.LeaderboardCalculation),
    ('/search',  controllers.SearchPage),
    ('/social',  controllers.SocialPage),
    ('/subscribe',  controllers.SubscribeToStream),
    ('/terms',  controllers.SearchTerms),
    ('/trending',  controllers.TrendingPage),
    ('/trend_email',  controllers.TrendingEmail),
    ('/unsubscribe',  controllers.UnsubscribeToStream),
    ('/upload_photo', controllers.PhotoUploadHandler),
], debug=True)
