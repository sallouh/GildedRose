package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

    public static final String CONJURED = "Conjured";
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

    static void assertItemEquals(Item actual, Item expected) {
        assertEquals(expected.name, actual.name);
        assertEquals(expected.quality, actual.quality);
        assertEquals(expected.sellIn, actual.sellIn);
    }

    @Test
    void sellInDateDiminution_butQualityCannotBeNegative() {
        GildedRose gildedRose = new GildedRose(new Item("foo", 0, 0));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item("foo", -1, 0));
    }

    @Test
    void qualityDiminution() {
        GildedRose gildedRose = new GildedRose(new Item("foo", 10, 10));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item("foo", 9, 9));
    }

    @Test
    void qualityDiminutionFasterAfterSellInDateExpired() {
        GildedRose gildedRose = new GildedRose(new Item("foo", 0, 10));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item("foo", -1, 8));
    }

    @Test
    void item_AgedBrie_elevationInQuality() {
        GildedRose gildedRose = new GildedRose(new Item(AGED_BRIE, 2, 2));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(AGED_BRIE, 1, 3));
    }

    @Test
    void item_AgedBrie_elevationInQuality_DoublesWhenOff() {
        GildedRose gildedRose = new GildedRose(new Item(AGED_BRIE, 0, 2));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(AGED_BRIE, -1, 4));
    }

    @Test
    void item_AgedBrie_cannotGoOver50Quality() {
        GildedRose gildedRose = new GildedRose(new Item(AGED_BRIE, 2, 50));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(AGED_BRIE, 1, 50));
    }

    @Test
    void item_BackStagePasses_elevationInQuality_byOneOutside10Days() {
        GildedRose gildedRose = new GildedRose(new Item(BACKSTAGE_PASSES, 20, 2));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(BACKSTAGE_PASSES, 19, 3));
    }

    @Test
    void item_BackStagePasses_elevationInQuality_byTwoInside10Days() {
        GildedRose gildedRose = new GildedRose(new Item(BACKSTAGE_PASSES, 10, 2));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(BACKSTAGE_PASSES, 9, 4));
    }

    @Test
    void item_BackStagePasses_elevationInQuality_byThreeInside5Days() {
        GildedRose gildedRose = new GildedRose(new Item(BACKSTAGE_PASSES, 5, 2));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(BACKSTAGE_PASSES, 4, 5));
    }

    @Test
    void item_BackStagePasses_elevationInQuality_goesToZeroWhenSellInExpires() {
        GildedRose gildedRose = new GildedRose(new Item(BACKSTAGE_PASSES, 0, 20));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(BACKSTAGE_PASSES, -1, 0));
    }

    @Test
    void item_conjured_DiminutionInQuality_twiceTheSpeed() {
        GildedRose gildedRose = new GildedRose(new Item(CONJURED, 3, 6));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(CONJURED, 2, 4));
    }

    @Test
    void item_conjured_DiminutionInQuality_twiceTheSpeed_alsoWhenSellInExpired() {
        GildedRose gildedRose = new GildedRose(new Item(CONJURED, 0, 6));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(CONJURED, -1, 2));
    }

    @Test
    void item_Sulfuras_neverChanges() {
        GildedRose gildedRose = new GildedRose(new Item(SULFURAS, 100, 100));

        gildedRose.updateQuality();

        assertItemEquals(gildedRose.getItems()[0], new Item(SULFURAS, 100, 100));
    }

}
