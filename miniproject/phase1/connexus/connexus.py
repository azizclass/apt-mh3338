import webapp2

import controllers

app = webapp2.WSGIApplication([
    ('/',  controllers.ManagePage),
    ('/create',  controllers.CreatePage),
    ('/manage',  controllers.ManagePage),
    ('/view',  controllers.ViewPage),
    ('/delete',  controllers.DeleteStream),
    ('/leaderboard_calc',  controllers.LeaderboardCalculation),
    ('/search',  controllers.SearchPage),
    ('/social',  controllers.SocialPage),
    ('/trending',  controllers.TrendingPage),
    ('/trend_email',  controllers.TrendingEmail),
    ('/unsubscribe',  controllers.UnsubscribeToStream),
    ('/upload_photo', controllers.PhotoUploadHandler),
], debug=True)
