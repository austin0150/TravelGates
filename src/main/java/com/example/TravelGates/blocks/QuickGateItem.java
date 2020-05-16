package com.example.TravelGates.blocks;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public class QuickGateItem extends BlockItem {
    public QuickGateItem(Block blockIn) {
        super(blockIn, new Item.Properties().group(ItemGroup.REDSTONE));
    }
}