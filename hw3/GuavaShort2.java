import com.google.common.math.IntMath;

public class GuavaShort2 {
    public static void main(String []args) {
        int[] arr = {1, 2, 3};
        int i;
        try {
            i = 1;
            if (i >= arr.length) throw new IndexOutOfBoundsException("Index was " + i + ", but array size is only " + arr.length);
            System.out.println("Valid index -- Execution should reach here"); 
            i = 7;
            if (i >= arr.length) throw new IndexOutOfBoundsException("Index was " + i + ", but array size is only " + arr.length);
            System.out.println("Invalid index -- Execution should NOT reach here"); 
        } catch(IndexOutOfBoundsException e) {
            System.out.println("Invalid index -- Execution should reach here"); 
        }
    }
}
