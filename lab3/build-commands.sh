cd lab3
PYTHONPATH=''
/usr/local/bin/nosetests --with-xunit --all-modules --traverse-namespace
--with-coverage --cover-package=./ --cover-inclusive
/usr/bin/python -m coverage xml --include=./*.py
/usr/bin/pylint -f parseable -d I0011,R0801 *.py | tee pylint.out
