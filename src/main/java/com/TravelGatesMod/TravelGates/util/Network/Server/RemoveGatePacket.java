package com.TravelGatesMod.TravelGates.util.Network.Server;

import com.TravelGatesMod.TravelGates.util.Network.IPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class RemoveGatePacket implements IPacket {

    private String GateID;

    public RemoveGatePacket(String id)
    {
        GateID = id;
    }

    @Override
    public void encode(PacketBuffer buf) {

        buf.writeString(GateID);
    }

    @Override
    public void decode(PacketBuffer buf) {
        GateID = buf.readString();
    }

    @Override
    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            PlayerEntity sender = context.get().getSender(); // the client that sent this packet
            // do stuff
        });
        context.get().setPacketHandled(true);
    }

    @Override
    public void register(int id) {

    }
}
