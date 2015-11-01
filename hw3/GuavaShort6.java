import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.google.common.base.Strings;
import com.google.common.base.Function;

/* (Before writing the code) The String.split specifies that it "Splits this string 
 * around matches of the given regular expression." This means that trailing
 * empty strings are omitted from the final result. So ",a,,b,".split(",")
 * results in "", "a", "", "b" when we ideally want "", "a", "", "b", "".
 */

public class GuavaShort6 {
    public static void main(String []args) {
        System.out.println(Iterables.transform(Splitter.on(',').split(",a,,b,"), new Function<String, String>() { public String apply(String s) { return  Strings.emptyToNull(s); }; }));
    }
}
