class Student(object):

    def __init__(self, name, gpa, age):
        self.name = name
        self.gpa = gpa
        self.age = age

    def __str__(self):
        return "Name: %s GPA: %f Age: %d" % (self.name, self.gpa, self.age)

    def __lt__(self, other):
        """ comparison based on:
            https://piazza.com/class/idrrytrfdew2ns?cid=23
        """
        return any([self.name < other.name,
                    self.name == other.name and self.age < other.age,
                    self.name == other.name and self.age == other.age and
                    self.gpa < other.gpa])

    def __eq__(self, other):
        return all([self.name == other.name, self.gpa == other.gpa,
                    self.age == other.age])

    def __hash__(self):
        return hash((self.name, self.age, self.gpa))
