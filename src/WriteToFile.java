import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class WriteToFile {

    FileWriter writer = null;

    public void writeToFile(String text) {
        File path = new File("won toys.txt");
        try {
            writer = new FileWriter(path);
            writer.write(text);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToFileClose() {
        if (writer == null) return;
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
