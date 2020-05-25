package com.TravelGatesMod.TravelGates.util.Network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IPacket {
    public void encode(PacketBuffer buf);

    public void decode(PacketBuffer buf);

    public void handle(Supplier<NetworkEvent.Context> context);

    public void register(int id);
}
