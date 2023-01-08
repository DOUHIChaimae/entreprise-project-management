package ma.enset.projectmanagement.utils.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.Queue;

public final class FileUtils {
    public static String mapperToFIle(Queue<String> lines, String fileName) {
        String file = String.format("%s.txt", fileName);
        try (FileWriter fileWriter= new FileWriter(file); BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            for (String line : lines) {
                bufferedWriter.write(line);
                bufferedWriter.newLine();
            }
            return file;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public static Queue<String> mapperFromFile(String pathName) {
        String file = pathName.endsWith(".txt") ? pathName : String.format("%s.txt", pathName);
        try (FileReader fileReader = new FileReader(file); BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            final Queue<String> lines = new ArrayDeque<>();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            return lines;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
