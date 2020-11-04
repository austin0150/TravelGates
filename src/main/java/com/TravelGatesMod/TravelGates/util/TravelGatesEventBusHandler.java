package com.TravelGatesMod.TravelGates.util;

import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;

@Mod.EventBusSubscriber
public class TravelGatesEventBusHandler {

    //Need this otherwise the Gate Directory wont save at server close time
    @SubscribeEvent
    public static void ServerClosing(FMLServerStoppingEvent event) {
        ServerWorld overworld = event.getServer().getWorld(DimensionType.OVERWORLD);
        GateInfoHandler.get(overworld).markDirty();
    }
}
