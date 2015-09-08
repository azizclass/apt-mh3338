from gofish1 import drawCard
from gofish1_fixed import drawCardFixed
import unittest


class DrawCardTests(unittest.TestCase):

    def test_lay_down(self):
        ranks = ["2"]
        suits = ["spades", "hearts", "diamonds", "clubs"]
        deck = [(rank, suit) for rank in ranks for suit in suits]
        hand = {}
        for i in range(1, 4):
            drawCard("Matt", deck, hand)
            self.assertTrue("2" in hand and len(hand["2"]) == i)
        drawCard("Matt", deck, hand)
        self.assertFalse("2" in hand)  # 2s should be laid down

    def test_no_lay_down(self):
        ranks = ["2", "3", "4", "5"]
        suits = ["spades"]
        deck = [(rank, suit) for rank in ranks for suit in suits]
        hand = {}
        for rank in ranks:
            drawCard("Matt", deck, hand)
            self.assertTrue(rank in hand and len(hand[rank]) == 1)


class DrawCardFixedTests(unittest.TestCase):

    def test_lay_down(self):
        ranks = ["2"]
        suits = ["spades", "hearts", "diamonds", "clubs"]
        deck = [(rank, suit) for rank in ranks for suit in suits]
        hand = {}
        for i in range(1, 4):
            drawCardFixed("Matt", deck, hand)
            self.assertTrue("2" in hand and len(hand["2"]) == i)
        drawCardFixed("Matt", deck, hand)
        self.assertFalse("2" in hand)  # 2s should be laid down

    def test_no_lay_down(self):
        ranks = ["2", "3", "4", "5"]
        suits = ["spades"]
        deck = [(rank, suit) for rank in ranks for suit in suits]
        hand = {}
        for i in range(len(ranks)):
            drawCardFixed("Matt", deck, hand)
        for rank in ranks:
            self.assertTrue(rank in hand)  # no ranks should be laid down


if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(DrawCardTests)
    unittest.TextTestRunner(verbosity=2).run(suite)
    suite = unittest.TestLoader().loadTestsFromTestCase(DrawCardFixedTests)
    unittest.TextTestRunner(verbosity=2).run(suite)
