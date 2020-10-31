package assignment;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BoggleDictionarySet implements BoggleDictionary {
    private String[] words;

    @Override
    public void loadDictionary(String filename) throws IOException {
        File dictFile = new File(filename);
        Scanner scanner = new Scanner(dictFile);
        Set<String> dict = new TreeSet<>();
        while(scanner.hasNextLine()) {
            dict.add(scanner.nextLine());
        }
        words = new String[dict.size()];
        words = dict.toArray(words);
    }

    @Override
    public boolean isPrefix(String prefix) {
        int idx = Arrays.binarySearch(words, prefix);

        if (idx < 0 && -idx < words.length) {
            if (words[-idx-1].startsWith(prefix))
                return true;
            else
                return false;
        } else
            return true;
    }

    @Override
    public boolean contains(String word) {
        return Arrays.binarySearch(words, word) >= 0;
    }

    @Override
    public Iterator<String> iterator() {
        return Arrays.asList(words).iterator();
    }
}
