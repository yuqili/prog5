package assignment;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.IOException;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class BoggleGameImplTest {
    static BoggleGame boggleGame;

    @BeforeAll
    static void setUp() throws IOException {
        BoggleDictionary boggleDictionary = new BoggleDictionarySet();
        boggleDictionary.loadDictionary("words.txt");
        boggleGame = new BoggleGameImpl();
        boggleGame.newGame(4, 1, "singleCube.txt", boggleDictionary);
    }

    @Test
    void newGame() {
    }

    @Test
    void getBoard() {
        char[][] board = boggleGame.getBoard();
        assertEquals(4, board.length);
        assertEquals(4, board[0].length);
        assertEquals('l', board[0][0]);
        assertEquals('m', board[1][3]);
        assertEquals('o', board[3][3]);

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                System.out.print(board[i][j]);
                System.out.print(' ');
            }
            System.out.println("");
        }
    }

    @Test
    void addWord() {
    }

    @Test
    void getLastAddedWord() {
    }

    @Test
    void setGame() {
    }

    @Test
    void getAllWordsByBoard() {
        boggleGame.setSearchTactic(BoggleGame.SearchTactic.SEARCH_BOARD);
        Collection<String> allWords = boggleGame.getAllWords();
        assertEquals(106, allWords.size());
        assertTrue(allWords.contains("some"));
    }

    @Test
    void getAllWordsByDict() {
        boggleGame.setSearchTactic(BoggleGame.SearchTactic.SEARCH_DICT);
        Collection<String> allWords = boggleGame.getAllWords();
        assertEquals(106, allWords.size());
        assertTrue(allWords.contains("some"));
    }

    @Test
    void checkWord() {
        Collection<Point> points = ((BoggleGameImpl)boggleGame).checkWord("some");
        System.out.println("Found at: " + points);
    }

    @Test
    void setSearchTactic() {
    }

    @Test
    void getScores() {
    }
}