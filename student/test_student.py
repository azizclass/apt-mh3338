from student import Student
from random import shuffle
import unittest


def custom_cmp(x, y):
    if cmp(x.gpa, y.gpa):
        return cmp(x.gpa, y.gpa)
    elif cmp(x.name, y.name):
        return cmp(x.name, y.name)
    else:
        return cmp(x.age, y.age)


class StudentTests(unittest.TestCase):

    def test__str__(self):
        matt = Student("Matt", 4.000000, 24)
        self.assertEqual(str(matt), "Name: Matt GPA: 4.000000 Age: 24")

    def test__lt__(self):
        jingwen = Student("Jingwen", 3.800000, 26)
        yuhao = Student("Yuhao", 3.800000, 27)
        matt = Student("Matt", 4.000000, 24)
        younger_matt = Student("Matt", 4.000000, 23)
        self.assertTrue(jingwen < matt)  # compare gpa
        self.assertTrue(jingwen < yuhao)  # compare name
        self.assertTrue(younger_matt < matt)  # compare age

    def test__eq__(self):
        jingwen = Student("Jingwen", 3.800000, 26)
        yuhao = Student("Yuhao", 3.800000, 27)
        matt = Student("Matt", 4.000000, 24)
        other_matt = Student("Matt", 4.000000, 24)
        self.assertFalse(jingwen == yuhao)
        self.assertFalse(jingwen == matt)
        self.assertFalse(yuhao == matt)
        self.assertTrue(matt == other_matt)

    def test__hash__(self):
        matt = Student("Matt", 4.000000, 24)
        other_matt = Student("Matt", 4.000000, 24)
        self.assertNotEqual(id(matt), id(other_matt))  # two different objects
        self.assertEqual(hash(matt), hash(other_matt))  # still same hash

    def test_sorted(self):
        jingwen = Student("Jingwen", 3.800000, 26)
        younger_matt = Student("Matt", 4.000000, 23)
        dumber_matt = Student("Matt", 3.800000, 24)
        matt = Student("Matt", 4.000000, 24)
        yuhao = Student("Yuhao", 3.800000, 27)
        students = [jingwen,  younger_matt, dumber_matt, matt, yuhao]
        expected_order = [jingwen,  younger_matt, dumber_matt, matt, yuhao]
        shuffle(students)
        self.assertFalse(all([current == expected for current, expected in
                             zip(students, expected_order)]))
        sorted_order = sorted(students)
        self.assertTrue(all([current == expected for current, expected in
                             zip(sorted_order, expected_order)]))

    def test_dict(self):
        jingwen = Student("Jingwen", 3.800000, 26)
        yuhao = Student("Yuhao", 3.800000, 27)
        matt = Student("Matt", 4.000000, 24)
        other_matt = Student("Matt", 4.000000, 24)
        students = [jingwen, yuhao, matt]
        d = {}
        for student in students:
            d[student] = student.name
        self.assertNotEquals(id(matt), id(other_matt))
        self.assertEquals(d[other_matt], matt.name)
        self.assertEquals(d[matt], matt.name)
        self.assertEquals(d[jingwen], jingwen.name)
        self.assertEquals(d[yuhao], yuhao.name)

    def test_array(self):
        jingwen = Student("Jingwen", 0.000000, 26)
        yuhao = Student("Yuhao", 1.000000, 21)
        matt = Student("Matt", 2.000000, 24)
        daniel = Student("Daniel", 3.000000, 20)
        chuck = Student("Chuck", 4.000000, 24)
        students = [jingwen, yuhao, matt, daniel, chuck]
        expected_order = [jingwen, yuhao, matt, daniel, chuck]
        shuffle(students)
        self.assertFalse(all([current == expected for current, expected in
                             zip(students, expected_order)]))
        sorted_order = sorted(students, cmp=lambda x, y: custom_cmp(x, y))
        self.assertTrue(all([current == expected for current, expected in
                             zip(sorted_order, expected_order)]))


if __name__ == '__main__':
    suite = unittest.TestLoader().loadTestsFromTestCase(StudentTests)
    unittest.TextTestRunner(verbosity=2).run(suite)
