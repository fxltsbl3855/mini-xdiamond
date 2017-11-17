package io.github.xdiamond.client.load;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Charlie
 *         To change this template use File | Settings | File Templates.
 */
import java.io.FileInputStream;
import java.util.Properties;

public class PropertyUtils {
    public static Properties getProperties(String filename) {
        FileInputStream fis = null;
        Properties p = new Properties();

        try {
            fis = new FileInputStream(filename);
            p.load(fis);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(fis != null) {
                try {
                    fis.close();
                }catch (Exception e) {}
            }
        }
        return p;
    }
}
