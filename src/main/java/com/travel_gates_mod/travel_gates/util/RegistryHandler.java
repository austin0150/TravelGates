package com.travel_gates_mod.travel_gates.util;

import com.travel_gates_mod.travel_gates.blocks.*;
import com.travel_gates_mod.travel_gates.TravelGates;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    private static final Item.Properties GATE_ITEM_PROPERTIES = new Item.Properties().group(TravelGates.TravelGatesItemGroup.instance);

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TravelGates.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TravelGates.MOD_ID);

    //Blocks
    public static final RegistryObject<Block> GATE_BLOCK = BLOCKS.register("gate", GateBlock::new);
    public static final RegistryObject<Block> QUICK_GATE_BLOCK = BLOCKS.register("quick_gate", QuickGateBlock::new);

    //Block Items
    public static final RegistryObject<Item> GATE_BLOCK_ITEM = ITEMS.register("gate", () -> new BlockItem(GATE_BLOCK.get(), GATE_ITEM_PROPERTIES));
    public static final RegistryObject<Item> QUICK_GATE_BLOCK_ITEM = ITEMS.register("quick_gate", () -> new BlockItem(QUICK_GATE_BLOCK.get(), GATE_ITEM_PROPERTIES));

    public static void init() {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }
}
