package re.forestier.edu;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import org.junit.jupiter.api.Test;

import re.forestier.edu.rpg.Item;

class ItemTest {

    @Test
    void testItemCreation() {
        Item item = new Item("Magic Sword", "A powerful sword", 2.5, 100);

        assertNotNull(item);
        assertEquals("Magic Sword", item.getName());
        assertEquals("A powerful sword", item.getDescription());
        assertEquals(2.5, item.getWeight());
        assertEquals(100, item.getValue());
    }

    @Test
    void testItemEquality() {
        Item item1 = new Item("Magic Sword", "A powerful sword", 2.5, 100);
        Item item2 = new Item("Magic Sword", "A powerful sword", 2.5, 100);

        assertNotSame(item1, item2); // Par défaut, les objets sont différents
    }

    @Test
    void testItemWithDifferentAttributes() {
        Item item1 = new Item("Magic Sword", "A powerful sword", 2.5, 100);
        Item item2 = new Item("Magic Shield", "A magical shield", 3.0, 80);

        assertNotEquals(item1.getName(), item2.getName());
        assertNotEquals(item1.getDescription(), item2.getDescription());
        assertNotEquals(item1.getWeight(), item2.getWeight());
        assertNotEquals(item1.getValue(), item2.getValue());
    }
}
