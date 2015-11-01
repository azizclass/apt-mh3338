import java.io.Reader;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.Character;

public class LowercaseReader extends Reader {

    private Reader reader;

    LowercaseReader(Reader r) {
        reader = r;
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        int num_read =  reader.read(cbuf, off, len);
        for(int i = off; i < off + num_read; i++) {
            cbuf[i] = Character.toLowerCase(cbuf[i]);
        }
        return num_read;
    }

    public void close() throws IOException {
        reader.close();
    }

    public static void main(String []args) {
        BufferedReader br = new BufferedReader(new LowercaseReader(new InputStreamReader(System.in))); 
        System.out.print("Enter text to make all lowercase (press enter when complete)\n>>>");
        try {
            String s = br.readLine();
            System.out.println(s);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
