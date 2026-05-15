import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/* Java File Handling
 * Path.of();
 * Files.readString();
 * Files.readAllLines();
 * Files.write();
 * Files.writeString();
 * Files.deleteIfExists();
 * Files.list();
 * Files.exists();
 * 
 * Extra for advanced:
 * Files.lines() (stream, import java.util.stream.Stream)
 * StandardOpenOption.APPEND (import java.nio.file.StandardOpenOption)
 * Path.getFileName()
 */

public class TodoOperations {
    public static List<String> readList(String name) throws IOException {
        name = "lists/"+name;
        return Files.readAllLines(Path.of(name));
    }
    public static void writeList(String name, List<String> content) throws IOException {
        name = "lists/"+name;
        Files.write(Path.of(name), content);
    }
    public static void writeList(String name, String content) throws IOException {
        name = "lists/"+name;
        Files.writeString(Path.of(name), content);
    }
    public static List<String> listDir() throws IOException {
        String name = "lists";
        List<String> output = new ArrayList<>();
        for (Path path : Files.list(Path.of(name)).toList()) {
            output.add(path.getFileName().toString());
        }
        return output;
    }
    public static void delFile(String name) throws IOException {
        name = "lists/"+name;
        Files.deleteIfExists(Path.of(name));
    }
}