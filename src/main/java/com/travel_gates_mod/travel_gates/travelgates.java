package com.travel_gates_mod.travel_gates;

import com.travel_gates_mod.travel_gates.util.GateInfo;
import com.travel_gates_mod.travel_gates.util.GateInfoHandler;
import com.travel_gates_mod.travel_gates.util.network.TravelGatesPacketHandler;
import com.travel_gates_mod.travel_gates.util.RegistryHandler;
import com.travel_gates_mod.travel_gates.util.TravelGatesEventBusHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("travelgates")
public class travelgates
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    //final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
    public static final String MOD_ID = "travelgates";

    public travelgates() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        RegistryHandler.init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(new TravelGatesEventBusHandler());

        GateInfoHandler.GATE_DIRECTORY = new ArrayList<GateInfo>();


    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("TravelGates version 1.0.5 Setup executing");
        TravelGatesPacketHandler.registerMessages();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }




    public static class TravelGatesItemGroup extends ItemGroup
    {
        public static final TravelGatesItemGroup instance = new TravelGatesItemGroup(ItemGroup.GROUPS.length, "Travel_Gates");
        private TravelGatesItemGroup(int index, String label)
        {
            super(index, label);
        }

        @Override
        public ItemStack createIcon()
        {
            return new ItemStack(RegistryHandler.GATE_BLOCK.get());
        }
    }
}
