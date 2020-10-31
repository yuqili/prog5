package assignment;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BoggleDictionaryTrie implements BoggleDictionary {
    private static Comparator<Character> trieNodeComparator = new Comparator<Character>() {
        @Override
        public int compare(Character o1, Character o2) {
            return o1.compareTo(o2) * -1;
        }
    };
    private TrieNode root;

    private class TrieNode {
        private final Map<Character, TrieNode> children = new TreeMap<>(trieNodeComparator);
        private String content;

        public Map<Character, TrieNode> getChildren() {
            return children;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public BoggleDictionaryTrie() {
        root = new TrieNode();
    }

    @Override
    public void loadDictionary(String filename) throws IOException {
        File speciesFile = new File(filename);
        Scanner scanner = new Scanner(speciesFile);
        while(scanner.hasNextLine()) {
            insert(scanner.nextLine());
        }
    }

    private void insert(String word) {
        TrieNode current = root;

        for(Character l: word.toCharArray()) {
            current = current.getChildren().computeIfAbsent(l, c -> new TrieNode());
        }
        current.setContent(word);
    }

    @Override
    public boolean isPrefix(String prefix) {
        TrieNode current = root;

        for(Character l: prefix.toCharArray()) {
            current = current.getChildren().get(l);
            if(current == null)
                return false;
        }
        return true;
    }

    @Override
    public boolean contains(String word) {
        TrieNode current = root;

        for(Character l: word.toCharArray()) {
            current = current.getChildren().get(l);
            if(current == null)
                return false;
        }
        return current.getContent() != null;
    }

    @Override
    public Iterator<String> iterator() {
        class TrieNodeIterator implements Iterator<TrieNode> {
            Stack<TrieNode> stack = new Stack<>();

            public TrieNodeIterator() {
                if (root.getChildren().size() > 0)
                    stack.push(root);
            }

            @Override
            public boolean hasNext() {
                if (stack.size() > 0)
                    return true;
                else
                    return false;
            }

            @Override
            public TrieNode next() {
                TrieNode current;

                do {
                  current = stack.pop();
                  for(TrieNode node: current.getChildren().values())
                      stack.push(node);
                } while(current.getContent() == null);

                return current;
            }
        }

        return new Iterator<String>() {
            TrieNodeIterator trieNodeIterator = new TrieNodeIterator();

            @Override
            public boolean hasNext() {
                return trieNodeIterator.hasNext();
            }

            @Override
            public String next() {
                return trieNodeIterator.next().getContent();
            }
        };
    }
}
