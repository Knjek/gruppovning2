import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CensorFile {
    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            System.err.println("You must run the program with arguments.");
            System.exit(2);
        }

        var goodFiles = new ArrayList<Path>();
        for(String arg : args) {
            if(Files.exists(Path.of(arg)) && Files.isReadable(Path.of(arg))) {
                goodFiles.add(Path.of(arg));
            }
            else {
                System.err.printf("The file %s is not readable.", arg);
            }
        }

        for (Path path : goodFiles) {

            try(var reader = Files.newBufferedReader(path); var writer = Files.newBufferedWriter(Path.of(path.toString() + ".clean"))) {
                String line;
                while((line = reader.readLine()) != null) {
                    var censor = new Censor(Path.of("censorship.txt"));
                    var cleanLine = censor.censor(line);
                    writer.write(cleanLine);
                }
            }
        }

    }
}
