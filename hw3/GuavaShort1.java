import com.google.common.math.IntMath;

public class GuavaShort1 {
    public static void main(String []args) {
        int a = Integer.MAX_VALUE;
        int b = Integer.MAX_VALUE;
        try {
             int uncheckedOverflowedSum = a + b;
             int checkedOverflowedSum = IntMath.checkedAdd(a, b);
        } catch(ArithmeticException ae) {
            System.out.println("Caught Overflow from adding " + a + " " + b); 
        }
    }
}
