package com.travel_gates_mod.travel_gates;

import com.travel_gates_mod.travel_gates.util.network.TravelGatesPacketHandler;
import com.travel_gates_mod.travel_gates.util.RegistryHandler;
import com.travel_gates_mod.travel_gates.util.TravelGatesEventBusHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(TravelGates.MOD_ID)
public class TravelGates {
    public static final String MOD_ID = "travelgates";
    private static final Logger LOGGER = LogManager.getLogger();

    public TravelGates() {

        RegistryHandler.init();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(TravelGatesEventBusHandler.class);
    }

    private void setup(final FMLCommonSetupEvent event) {
        LOGGER.info("TravelGates version 1.0.6 Setup executing");
        TravelGatesPacketHandler.registerMessages();
    }

    private void clientSetup(final FMLClientSetupEvent event) {
        LOGGER.info("Got game settings " + event.getMinecraftSupplier().get().gameSettings);
    }

    public static class TravelGatesItemGroup extends ItemGroup {
        public static final TravelGatesItemGroup instance = new TravelGatesItemGroup(ItemGroup.GROUPS.length, "travelGates");
        private TravelGatesItemGroup(int index, String label)
        {
            super(index, label);
        }

        @Override
        public ItemStack createIcon() {
            return new ItemStack(RegistryHandler.GATE_BLOCK.get());
        }
    }
}
