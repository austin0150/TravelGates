package com.TravelGatesMod.TravelGates.util;

import com.TravelGatesMod.TravelGates.util.Network.Server.ServerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.server.FMLServerStoppingEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//@OnlyIn(Dist.DEDICATED_SERVER)
@Mod.EventBusSubscriber
public class TravelGatesEventBusHandler {

    private static final Logger LOGGER = LogManager.getLogger();


    //Need this otherwise the Gate Directory wont save at server close time
    @SubscribeEvent
    public static void ServerClosing(FMLServerStoppingEvent event)
    {
            ServerWorld overworld = event.getServer().getWorld(DimensionType.OVERWORLD);
            GateInfoHandler.get(overworld).markDirty();
    }
}
