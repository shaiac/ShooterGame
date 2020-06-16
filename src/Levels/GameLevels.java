package Levels;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GameLevels {
    private List<String> levelsList;
    public GameLevels() {
        levelsList = new ArrayList<>();
    }
    public void ReadLevels(String level) {
        Reader reader = null;
        try {
            InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(level);
            reader = new InputStreamReader(is);
            BufferedReader buffer = new BufferedReader(reader);
            this.readFile(buffer);
        } finally {
            try { // try to close the reader
                reader.close();
            } catch (IOException e) {
                System.err.println("Couldn't close reader");
                e.printStackTrace();
            }
        }
    }

    private void readFile(BufferedReader buffer) {
        try {
            String data;
            //while there are still lines in the file.
            buffer.readLine();
            while ((data = buffer.readLine()) != null) {
                levelsList.add(data);
                buffer.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try { // try to close the buffer
                buffer.close();
            } catch (IOException e) {
                System.err.println("Couldn't close reader");
                e.printStackTrace();
            }
        }
    }

    public List<String> getLevelsList() {
        return this.levelsList;
    }
}
