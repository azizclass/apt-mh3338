import com.google.common.collect.HashBiMap;
import com.google.common.collect.BiMap;
import java.util.HashMap;
import java.util.Map;

/* If you maintain two separate maps there may be a case where one of the two
 * values is already a key in one of the other maps. This means that one both
 * elements from the previous mapping will now be invalid because this approach
 * does not guarantee unique two-way mappings of values. Ideally, you want a
 * data structure that is able to make the mapping coherent, or fail otherwise
 * (like Guava's BiMap!). More illustratively:
 */

public class GuavaShort3 {
    public static void main(String []args) {
        Map<String, Integer> str2Int = new HashMap();
        Map<Integer, String> int2Str = new HashMap();
        str2Int.put("odd", 1);
        int2Str.put(1, "odd");
        str2Int.put("odd", 2);
        int2Str.put(2, "odd");
        System.out.println("FAILED as expected: expected 1 but got " + str2Int.get("odd"));
        try {
        BiMap<Integer, String> intAndStr = HashBiMap.create();
        intAndStr.put(1, "odd");
        intAndStr.put(2, "odd");
        } catch (IllegalArgumentException e) {
            System.out.println("FAILED as expected: caught invalid insertion of additional  mapping to \"odd\" value");
        }
    }
}
