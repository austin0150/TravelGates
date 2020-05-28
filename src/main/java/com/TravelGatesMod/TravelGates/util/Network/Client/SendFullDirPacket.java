package com.TravelGatesMod.TravelGates.util.Network.Client;

import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import com.TravelGatesMod.TravelGates.util.Network.Server.ServerUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.function.Supplier;

public class SendFullDirPacket {

    List<GateInfo> FullDir;
    private static final Logger LOGGER = LogManager.getLogger();

    public SendFullDirPacket ()
    {

    }


    public static void encode(SendFullDirPacket packet,PacketBuffer buf) {
        buf.writeCompoundTag(ServerUtil.WriteDirToNBT(new CompoundNBT()));
    }

    public static SendFullDirPacket decode(PacketBuffer buf) {
        SendFullDirPacket packet = new SendFullDirPacket();
        packet.FullDir = ClientUtil.ParseNbtToDir(buf.readCompoundTag());
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            // Work that needs to be threadsafe (most work)
            //PlayerEntity sender = context.get().getSender(); // the client that sent this packet
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                //Add gate to the clients directory
                GateInfoHandler.GATE_DIRECTORY.clear();
                GateInfoHandler.GATE_DIRECTORY = this.FullDir;
                LOGGER.info("Full Directory received from server");

            });
            // do stuff
        });

        context.get().setPacketHandled(true);
    }
}
