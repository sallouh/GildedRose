package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DegradePredicateTest {

    @Test
    void degradeTest() {
        DegradePredicate degradePredicate  = new DegradePredicate();
        assertTrue(degradePredicate.test(new Item("foo", 0, 0)));
        assertFalse(degradePredicate.test(new Item("Aged Brie", 0, 0)));
        assertFalse(degradePredicate.test(new Item("Backstage passes to a TAFKAL80ETC concert", 0, 0)));
        assertFalse(degradePredicate.test(new Item("Sulfuras, Hand of Ragnaros", 0, 0)));
    }
}
