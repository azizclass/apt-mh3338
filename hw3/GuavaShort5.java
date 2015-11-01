import java.util.Arrays;
import com.google.common.primitives.Ints;
/* If you maintain two separate maps there may be a case where one of the two
 * values is already a key in one of the other maps. This means that one both
 * elements from the previous mapping will now be invalid because this approach
 * does not guarantee unique two-way mappings of values. Ideally, you want a
 * data structure that is able to make the mapping coherent, or fail otherwise
 * (like Guava's BiMap!). More illustratively:
 */

public class GuavaShort5 {
    public static void main(String []args) {
        int[] array = {1, 2, 3, 4, 5};
        System.out.println("Array contents: " + Arrays.toString(array));
        System.out.println("Array contains 2: " + Ints.contains(array, 2));
        System.out.println("Array contains 7: " + Ints.contains(array, 7));
    }
}
