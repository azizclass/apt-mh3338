import junit.framework.TestCase;

public class RationalTest extends TestCase {

    protected Rational HALF;

    protected void setUp() {
      HALF = new Rational( 1, 2 );
    }

    // Create new test
    public RationalTest (String name) {
        super(name);
    }

    public void testEquality() {
        assertEquals(new Rational(1,3), new Rational(1,3));
        assertEquals(new Rational(1,3), new Rational(2,6));
        assertEquals(new Rational(3,3), new Rational(1,1));
    }

    // Test for nonequality
    public void testNonEquality() {
        assertFalse(new Rational(2,3).equals(
            new Rational(1,3)));
    }

    public void testAccessors() {
    	assertEquals(new Rational(2,3).numerator(), 2);
    	assertEquals(new Rational(2,3).denominator(), 3);
    }

    public void testRoot() {
        Rational s = new Rational( 1, 4 );
        Rational sRoot = null;
        try {
            sRoot = s.root();
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
        }
        assertTrue( sRoot.isLessThan( HALF.plus( Rational.getTolerance() ) ) 
                        && HALF.minus( Rational.getTolerance() ).isLessThan( sRoot ) );
    }

    public void testRootInvalidRange() {
        Rational s = new Rational(-1, 4);
        Rational sRoot = null;
        try {
            sRoot = s.root();
            System.out.println("sRoot is " + sRoot);
        } catch (IllegalArgumentToSquareRootException e) {
            e.printStackTrace();
            assertTrue(true);
        }
        assertTrue(false);
    }

    public void testIsLessThan() {
        assertTrue(new Rational(1,2).isLessThan(new Rational(3,4)));
        assertTrue(new Rational(1,2).isLessThan(new Rational(-1,2)));
    }


    public void testConstructor() {
    	assertEquals(new Rational(1,1).numerator(), 1);
    	assertEquals(new Rational(1,1).denominator(), 1);
    	assertEquals(new Rational(-1,-1).numerator(), -1);
    	assertEquals(new Rational(-1,-1).denominator(), -1);
    	assertEquals(new Rational(1,0).numerator(), 1);
    	assertEquals(new Rational(1,0).denominator(), 0);
    	assertEquals(new Rational(0,0).numerator(), 0);
    	assertEquals(new Rational(0,0).denominator(), 0);
    }

    public void testNotEquals() {
    	assertFalse(new Rational(1,1).equals(null));
    	assertFalse(new Rational(1,1).equals("hello"));
    }

    public void testPlus() {
        assertEquals(new Rational(2, 3),
                     new Rational(1, 3).plus(new Rational(1, 3)));
        assertEquals(new Rational(-2, -3),
                     new Rational(1, -3).plus(new Rational(1, -3)));
        assertEquals(new Rational(2, 3),
                     new Rational(1, 3).plus(new Rational(-1, -3)));
        assertEquals(new Rational(-2, -3),
                     new Rational(-1, 3).plus(new Rational(-1, 3)));
    }

    public void testMinus() {
        assertEquals(new Rational(0, 0),
                     new Rational(1, 3).minus(new Rational(1, 3)));
        assertEquals(new Rational(0, 0),
                     new Rational(1, 3).minus(new Rational(-1, -3)));
        assertEquals(new Rational(-2, 3),
                     new Rational(-1, 3).minus(new Rational(-1, 3)));
        assertEquals(new Rational(-2, 3),
                     new Rational(1, -3).minus(new Rational(1, -3)));
    }

    public void testTimes() {
        assertEquals(new Rational(3, 8),
                     new Rational(3, 4).times(new Rational(1, 2)));
        assertEquals(new Rational(-3, 8),
                     new Rational(-3, 4).times(new Rational(1, 2)));
        assertEquals(new Rational(0, 8),
                     new Rational(0, 4).times(new Rational(1, 2)));
    }

    public void testDivides() {
        assertEquals(new Rational(3, 2),
                     new Rational(3, 4).divides(new Rational(1, 2)));
        assertEquals(new Rational(-3, 2),
                     new Rational(-3, 4).divides(new Rational(1, 2)));
        assertEquals(new Rational(8, 0),
                     new Rational(0, 4).divides(new Rational(0, 2)));
    }

    public void testTolerance() {
        Rational oldTolerance = Rational.getTolerance();
        Rational newTolerance = oldTolerance.plus(HALF);
        Rational.setTolerance(newTolerance);
        assertEquals(Rational.getTolerance(), oldTolerance.plus(HALF));
        Rational.setTolerance(oldTolerance);
    }

    public void testAbs() {
        assertEquals(new Rational(3, 2),
                     new Rational(3, 2).abs());
        assertEquals(new Rational(3, 2),
                     new Rational(-3, 2).abs());
        assertEquals(new Rational(3, 2),
                     new Rational(3, -2).abs());
        assertEquals(new Rational(3, 2),
                     new Rational(-3, -2).abs());
    }

    public static void main(String args[]) {
        String[] testCaseName = 
            { RationalTest.class.getName() };
        junit.textui.TestRunner.main(testCaseName);
    }
}
