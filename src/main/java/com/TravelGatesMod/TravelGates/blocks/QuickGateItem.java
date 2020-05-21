package com.TravelGatesMod.TravelGates.blocks;

import com.TravelGatesMod.TravelGates.travelgates;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;

public class QuickGateItem extends BlockItem {
    public QuickGateItem(Block blockIn) {
        super(blockIn, new Item.Properties().group(travelgates.TravelGatesItemGroup.instance));
    }
}