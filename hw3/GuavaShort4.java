import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import com.google.common.collect.Sets;
import java.util.HashMap;
import java.util.Map;

/* If you maintain two separate maps there may be a case where one of the two
 * values is already a key in one of the other maps. This means that one both
 * elements from the previous mapping will now be invalid because this approach
 * does not guarantee unique two-way mappings of values. Ideally, you want a
 * data structure that is able to make the mapping coherent, or fail otherwise
 * (like Guava's BiMap!). More illustratively:
 */

public class GuavaShort4 {
    public static void main(String []args) {
        Set<Integer> numbers = new HashSet<Integer>(Arrays.asList(1, 2 ,3 ,4 ,5, 6)); 
        Set<Integer> oddNumbers = new HashSet<Integer>(Arrays.asList(1, 3 ,5)); 
        Set<Integer> repeatedOddNumbers = Sets.intersection(numbers, oddNumbers);
        System.out.println("numbers: " + numbers);
        System.out.println("oddNumbers: " + oddNumbers);
        System.out.println("numbers ^ oddNumbers: " + repeatedOddNumbers);
        System.out.println("oddNumbers == repeatedOddNumbers: " + oddNumbers.equals( repeatedOddNumbers));
    }
}
