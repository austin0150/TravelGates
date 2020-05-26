package com.TravelGatesMod.TravelGates.util.Network.Server;

import com.TravelGatesMod.TravelGates.blocks.Gate;
import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.Network.Shared.UpdateGatePacket;
import com.TravelGatesMod.TravelGates.util.Network.TravelGatesPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerUtil {

    public static void SendDirToClient()
    {
        //Send Gate dir to client
    }

    public static void BroadcastUpdate(GateInfo info)
    {
        // Send to all connected players
        TravelGatesPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateGatePacket(info));
    }

}
