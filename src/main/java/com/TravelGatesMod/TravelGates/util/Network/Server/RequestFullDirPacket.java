package com.TravelGatesMod.TravelGates.util.Network.Server;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RequestFullDirPacket{


    public RequestFullDirPacket()
    {
    }

    public static void encode(RequestFullDirPacket packet, PacketBuffer buf) {
        //Do nothing, no content needed
    }

    public static RequestFullDirPacket decode(PacketBuffer buf) {
        return new RequestFullDirPacket();
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            PlayerEntity sender = context.get().getSender(); // the client that sent this packet

            DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
                ServerUtil.SendDirToClient();

            });
        });
        context.get().setPacketHandled(true);

    }

}
