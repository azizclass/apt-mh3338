import unittest
import Experiment
import sys

class MyTestCase(unittest.TestCase):

  def test_t1(self):
    r1 = Experiment.largest([3,2,3,4])
    self.assertEqual(r1, 4)

  def test_t2(self):
    r1 = Experiment.largest([-1, -2, -3, -4])
    self.assertEqual(r1, -1)

  def test_t3(self):
    self.assertRaises(ValueError, Experiment.largest, [])

if __name__ == '__main__':
  unittest.main()
