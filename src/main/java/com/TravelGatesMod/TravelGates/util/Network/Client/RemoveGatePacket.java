package com.TravelGatesMod.TravelGates.util.Network.Client;

import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveGatePacket {

    private String ID;

    public RemoveGatePacket()
    {}

    public RemoveGatePacket(GateInfo info)
    {
        ID = info.GATE_ID;
    }

    public static void encode(RemoveGatePacket packet, PacketBuffer buf) {

        buf.writeString(packet.ID);
    }

    public static RemoveGatePacket decode(PacketBuffer buf) {
        RemoveGatePacket packet = new RemoveGatePacket();
        packet.ID = buf.readString();
        return packet;

    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            //PlayerEntity sender = context.get().getSender(); // the client that sent this packet

            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                GateInfoHandler.RemoveID(this.ID);

            });

        });
        context.get().setPacketHandled(true);
    }

    /*
    public void register(int id) {
        TravelGatesPacketHandler.
    }

     */
}
