package com.TravelGatesMod.TravelGates.util.Network.Shared;

import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import com.TravelGatesMod.TravelGates.util.Network.Server.ServerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateGatePacket {

    private GateInfo info;

    public UpdateGatePacket()
    {}

    public UpdateGatePacket(GateInfo info)
    {
        this.info = info;
    }

    public static void encode(UpdateGatePacket packet, PacketBuffer buf) {
        buf.writeCompoundTag(packet.info.WriteNBT(new CompoundNBT()));
    }

    public static UpdateGatePacket decode(PacketBuffer buf) {
        UpdateGatePacket packet = new UpdateGatePacket();
        packet.info = new GateInfo(buf.readCompoundTag());
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)

            GateInfoHandler.UpdateGate(this.info);
            DistExecutor.runWhenOn(Dist.DEDICATED_SERVER, () -> () -> {
                PlayerEntity sender = context.get().getSender(); // the client that sent this packet
                ServerUtil.BroadcastUpdate(this.info);

            });
            // do stuff
        });

        context.get().setPacketHandled(true);
    }
}
