package assignment;


import java.io.IOException;
import java.util.Iterator;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BoggleDictionaryTest {
    static BoggleDictionary boggleDictionary;

    @BeforeAll
    static void setUp() throws IOException {
        boggleDictionary = new BoggleDictionarySet();
        boggleDictionary.loadDictionary("words.txt");
    }

    @Test
    void isPrefix_true() {
        assertEquals(true, boggleDictionary.isPrefix("a"));
        assertTrue(boggleDictionary.isPrefix("aliv"));
    }

    @Test
    void isPrefix_false() {
        assertEquals(false, boggleDictionary.isPrefix("zx"));
    }

    @Test
    void contains_true() {
        assertTrue(boggleDictionary.contains("aa"));
    }

    @Test
    void contains_false() {
        assertFalse(boggleDictionary.contains("a"));
    }

    @Test
    void iterator() {
        Iterator<String> itr = boggleDictionary.iterator();
        assertEquals("aa", itr.next());
        assertEquals("aah", itr.next());
        itr.next();
        itr.next();
        itr.next();
        assertEquals("aal", itr.next());
    }
}