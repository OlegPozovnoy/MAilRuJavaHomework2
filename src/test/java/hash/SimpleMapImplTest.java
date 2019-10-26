package hash;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMapImplTest {
    private SimpleMap<Integer, String> map = new SimpleMapImpl<>();

    @BeforeEach
    void setUp() throws Exception {
        map.put(1, "adin");
        map.put(2, "dva");
        map.put(3, "pyat");
        map.put(null, "null");
    }

    @Test
    void put() {
        String str = map.put(4, "www");
        assertEquals("www", map.get(4));
        assertEquals(5, map.size());
        assertEquals("www", str);
        str = map.put(4, "aa");
        assertEquals("aa", map.get(4));
        assertEquals(5, map.size());
        assertEquals("aa", str);
        str = map.put(null, "null2");
        assertEquals("null2", map.get(null));
        assertEquals(5, map.size());
        assertEquals("null2", str);

        for (int i = 0; i < 100; i++)
            map.put(i, String.valueOf(i));

        assertEquals(101, map.size());
    }

    @Test
    void get() {
        assertEquals("adin", map.get(1));
        assertEquals("null", map.get(null));
        assertNull(null, map.get(99));
    }

    @Test
    void remove() {
        assertNotNull(map.get(1));
        assertEquals("adin", map.remove(1));
        assertNull(map.get(1));
    }

    @Test
    void contains() {
        assertTrue(map.contains(1));
        assertFalse(map.contains(66));
        assertTrue(map.contains(null));
    }

    @Test
    void size() {
        assertEquals(4, map.size());
        map.put(99, "99");
        assertEquals(5, map.size());
        map.remove(null);
        assertEquals(4, map.size());
    }

    @Test
    void keySet() {
        Set<Integer> set = map.keySet();
        assertTrue(set.contains(1));
        assertTrue(set.contains(2));
        assertTrue(set.contains(3));
        assertTrue(set.contains(null));
        assertEquals(4, set.size());
    }

    @Test
    void values() {
        Collection<String> list = map.values();
        assertEquals(4, list.size());
        assertTrue(list.contains("adin"));
        assertTrue(list.contains("dva"));
        assertTrue(list.contains("pyat"));
        assertTrue(list.contains("null"));
    }
}