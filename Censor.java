import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Censor {
    private int censorCount;
    private Path censorReference;

    public Censor(Path path) {
        this.censorReference = path;
        censorCount = 0;
    }

    public int getCensorCount() {
        return censorCount;
    }

    private Map<String, String> wordsToMap() throws IOException {
        Map<String, String> wordMap = new HashMap<>();
        String line;
        try (BufferedReader br = Files.newBufferedReader(censorReference)) {
            while ((line = br.readLine()) != null) {
                String line2 = br.readLine();
                wordMap.put(line, line2);
            }
        }
        return wordMap;
    }

    public String censor(String text) throws IOException {
        var wordArray = text.split("((?<=\\W+)|(?=\\W+))"); //is something before or after a non alphabetic character
        var wordMap = wordsToMap();
        for (int i = 0; i < wordArray.length; i++) {
            
            var chars = wordArray[i].toCharArray();
            // var wordSb = new StringBuilder();
            // var charSb = new StringBuilder();
            // for (int j = 0; j < chars.length; j++) {
            //     char c = chars[j];
            //     if (Character.isAlphabetic(c)) {
            //         wordSb.append(c);
            //     }
            //     else {
            //         charSb.append(c);
            //     }
            // }
           
            var word = wordArray[i].toLowerCase();
            if ((wordMap.containsKey(word))) {
                censorCount++;
                if (Character.isUpperCase(chars[0])) {
                    String mapValueUpper = wordMap.get(word).substring(0, 1).toUpperCase() + wordMap.get(word).substring(1);
                    wordArray[i] = mapValueUpper;
                }
                else {
                    wordArray[i] = wordMap.get(word);
                }
            }
        }

        return String.join("", wordArray);
    }
}