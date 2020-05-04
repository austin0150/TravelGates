package com.example.TravelGates.util;

import com.example.TravelGates.blocks.Gate;
import com.example.TravelGates.blocks.GateItem;
import com.example.TravelGates.items.ItemBase;
import com.example.TravelGates.travelgates;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RegistryHandler {

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, travelgates.MOD_ID);
    public static final DeferredRegister<Block> BLOCKS = new DeferredRegister<>(ForgeRegistries.BLOCKS, travelgates.MOD_ID);

    public static void Init()
    {
        ITEMS.register(FMLJavaModLoadingContext.get().getModEventBus());
        BLOCKS.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    //Items
    public static final RegistryObject<Item> TRAVEL_PAPER = ITEMS.register("travel_paper", ItemBase::new);

    //Blocks
    public static final RegistryObject<Block> GATE_BLOCK = BLOCKS.register("gate", Gate::new);

    //Block Items
    public static final RegistryObject<Item> GATE_BLOCK_ITEM = ITEMS.register("gate", () -> new GateItem(GATE_BLOCK.get()));
}
