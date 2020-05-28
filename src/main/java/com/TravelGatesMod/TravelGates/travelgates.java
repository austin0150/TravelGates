package com.TravelGatesMod.TravelGates;

import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import com.TravelGatesMod.TravelGates.util.Network.Server.ServerUtil;
import com.TravelGatesMod.TravelGates.util.Network.TravelGatesPacketHandler;
import com.TravelGatesMod.TravelGates.util.RegistryHandler;
import com.TravelGatesMod.TravelGates.util.TravelGatesEventBusHandler;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

        RegistryHandler.Init();

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        //DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
            LOGGER.info("TravelGates: Registered server Event");
            MinecraftForge.EVENT_BUS.register(new TravelGatesEventBusHandler());

        //});



    }

    private void setup(final FMLCommonSetupEvent event)
    {
        LOGGER.info("TravelGates version 1.0.5 Alpha Setup executing");
        new TravelGatesPacketHandler();
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
