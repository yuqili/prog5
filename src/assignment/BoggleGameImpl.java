package assignment;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class BoggleGameImpl implements BoggleGame {
    private final static int MINIMAL_WORD_LENGTH = 4;

    private int size;
    private int numPlayers;
    private String cubeFile;
    private char[][] board;
    private BoggleDictionary dict;
    private SearchTactic tactic = BoggleGame.SEARCH_DEFAULT;


    @Override
    public void newGame(int size, int numPlayers, String cubeFile, BoggleDictionary dict) throws IOException {
        this.size = size;
        this.numPlayers = numPlayers;
        this.cubeFile = cubeFile;
        this.dict = dict;

        startGame();
    }

    private void startGame() throws FileNotFoundException {
        this.board = new char[size][size];
        loadCubeFile();
    }

    private void loadCubeFile() throws FileNotFoundException {
        List<String> cubes = new ArrayList<>();
        File cubeFile = new File(this.cubeFile);
        Scanner scanner = new Scanner(cubeFile);
        while(scanner.hasNextLine()) {
            cubes.add(scanner.nextLine().toLowerCase());
        }

        int boards = cubes.get(0).length();
        char[][] cubeArray = new char[cubes.size()][boards];
        int r = 0;
        for(String cube: cubes)
            cubeArray[r++] = cube.toCharArray();

        int boardIdx = new Random().nextInt(boards);
        for(int i = 0; i < cubes.size(); i++) {
            int row = i / size;
            int col = i % size;
            board[row][col] = cubeArray[i][boardIdx];
        }
    }

    @Override
    public char[][] getBoard() {
        return board;
    }

    @Override
    public int addWord(String word, int player) {
        return 0;
    }

    @Override
    public List<Point> getLastAddedWord() {
        return null;
    }

    @Override
    public void setGame(char[][] board) {
        this.board = board;
    }

    @Override
    public Collection<String> getAllWords() {
        if (tactic == SearchTactic.SEARCH_BOARD)
            return getAllWordsByBoard();
        else
            return getAllWordsByDict();
    }

    private Collection<String> getAllWordsByBoard() {
        Set<String> words = new HashSet<>();

        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) {
                List<Point> points = new ArrayList<>();
                points.add(new Point(r, c));
                collectWords(r, c, points, words);
            }

        return words;
    }

    private void collectWords(int row, int col, List<Point> points, Set<String> words) {
        String prefix = "";
        for(Point point: points) {
            prefix += board[point.x][point.y];
        }

        for (int rd = -1; rd <=1; rd++) {
            int newRow = row + rd;
            if (newRow >= 0 && newRow < size) {
                for (int cd = -1; cd <= 1; cd++) {
                    int newCol = col + cd;
                    if (newCol >= 0 && newCol < size) {
                        Point point = new Point(newRow, newCol);
                        if (!points.contains(point)) {
                            String word = prefix + board[newRow][newCol];
                            if (dict.contains(word))
                                words.add(word);
                            if (dict.isPrefix(word)) {
                                List<Point> newPoints = new ArrayList<>(points);
                                newPoints.add(point);
                                collectWords(newRow, newCol, newPoints, words);
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean checkWord(int row, int col, String word, List<Point> points) {
        if (board[row][col] != word.charAt(0))
            return false;
        else {
            points.add(new Point(row, col));
            String newWord = word.substring(1);
            if (newWord.length() == 0)
                return true;

            for (int rd = -1; rd <= 1; rd++) {
                int newRow = row + rd;
                if (newRow >= 0 && newRow < size) {
                    for (int cd = -1; cd <= 1; cd++) {
                        int newCol = col + cd;
                        if (newCol >= 0 && newCol < size) {
                            Point point = new Point(newRow, newCol);
                            if (!points.contains(point)) {
                                List<Point> newPoints = new ArrayList<>(points);
                                if (checkWord(newRow, newCol, newWord, newPoints)) {
                                    points.removeAll(points);
                                    points.addAll(newPoints);
                                    return true;
                                }
                            }
                        }
                    }
                }
            }

            return false;
        }
    }

    public List<Point> checkWord(String word) {
        for (int r = 0; r < size; r++)
            for (int c = 0; c < size; c++) {
                List<Point> points = new ArrayList<>();
                if (checkWord(r, c, word, points)) {
                  return points;
                }
            }
        return null;
    }

    private Collection<String> getAllWordsByDict() {
        Set<String> words = new HashSet<>();

        for (String word: dict) {
            if (checkWord(word) != null) {
                words.add(word);
            }
        }

        return words;
    }

    @Override
    public void setSearchTactic(SearchTactic tactic) {
        this.tactic = tactic;
    }

    @Override
    public int[] getScores() {
        return new int[0];
    }
}
