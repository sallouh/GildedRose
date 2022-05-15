package com.gildedrose;

class GildedRose {
    public static final String AGED_BRIE = "Aged Brie";
    public static final String BACKSTAGE_PASSES = "Backstage passes to a TAFKAL80ETC concert";
    public static final String SULFURAS = "Sulfuras, Hand of Ragnaros";
    public static final String CONJURED = "Conjured Mana Cake";
    private final Item[] items;

    public GildedRose(Item... items) {
        this.items = items;
    }

    public Item[] getItems() {
        return items;
    }

    public void updateQuality() {
        for (Item item : items) {
            updateItemQuality(item);
        }
    }

    private void updateItemQuality(Item item) {
        DegradePredicate degradePredicate = new DegradePredicate();
        boolean isExpired = item.sellIn < 1;
        int degradeRate = getDegradeRate(item, isExpired);
        if (degradePredicate.test(item)) {
            adjustQuality(item, degradeRate);
        }
        if (AGED_BRIE.equals(item.name)) {
            adjustQuality(item, 1);
        }
        if (BACKSTAGE_PASSES.equals(item.name)) {
            adjustQuality(item, 1);
            if (item.sellIn < 11) {
                adjustQuality(item, 1);
            }
            if (item.sellIn < 6) {
                adjustQuality(item, 1);
            }
            if (isExpired) {
                item.quality = 0;
            }
        }
        if (!SULFURAS.equals(item.name)) {
            item.sellIn = item.sellIn - 1;
        }
        if (isExpired && AGED_BRIE.equals(item.name)) {
                adjustQuality(item, 1);
        }
    }

    private int getDegradeRate(Item item, boolean isExpired) {
        int rate = CONJURED.equals(item.name) ? -2 : -1;
        if (isExpired) {
            rate *= 2;
        }
        return rate;
    }


    private void adjustQuality(Item item, int adjustment) {
        if (item.quality > 0 && item.quality < 50) {
            item.quality = item.quality + adjustment;
        }
    }
}
