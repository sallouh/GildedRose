package com.gildedrose;

import java.util.function.Predicate;

import static com.gildedrose.GildedRose.*;

public class DegradePredicate implements Predicate<Item> {

    @Override
    public boolean test(Item item) {
        return !AGED_BRIE.equals(item.name)
            && !BACKSTAGE_PASSES.equals(item.name)
            && !SULFURAS.equals(item.name);
    }
}
