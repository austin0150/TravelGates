package com.TravelGatesMod.TravelGates.util.Network.Client;

import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AddGatePacket{

    private GateInfo info;
    public AddGatePacket()
    {

    }

    public AddGatePacket(GateInfo info)
    {
        this.info = info;
    }

    public static void encode(AddGatePacket packet,PacketBuffer buf) {
        buf.writeCompoundTag(packet.info.WriteNBT(new CompoundNBT()));
    }

    public static AddGatePacket decode(PacketBuffer buf) {
        AddGatePacket packet = new AddGatePacket();
        packet.info = new GateInfo(buf.readCompoundTag());
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            //PlayerEntity sender = context.get().getSender(); // the client that sent this packet
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                //Add gate to the clients directory
                GateInfoHandler.AddGate(this.info);

            });
            // do stuff
        });

        context.get().setPacketHandled(true);
    }

}
