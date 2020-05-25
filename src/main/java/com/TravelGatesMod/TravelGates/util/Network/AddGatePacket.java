package com.TravelGatesMod.TravelGates.util.Network;

import com.TravelGatesMod.TravelGates.util.GateInfo;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AddGatePacket implements IPacket{

    private CompoundNBT nbt;
    AddGatePacket(GateInfo info)
    {
        nbt = info.WriteNBT(new CompoundNBT());
    }
    @Override
    public void encode(PacketBuffer buf) {
        buf.writeCompoundTag(nbt);
    }

    @Override
    public void decode(PacketBuffer buf) {
        this.nbt = buf.readCompoundTag();
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
