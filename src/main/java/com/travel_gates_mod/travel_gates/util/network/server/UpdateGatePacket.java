package com.travel_gates_mod.travel_gates.util.network.server;

import com.travel_gates_mod.travel_gates.util.GateInfo;
import com.travel_gates_mod.travel_gates.util.GateInfoHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ListIterator;
import java.util.function.Supplier;

public class UpdateGatePacket {

    private GateInfo info;

    private static final Logger LOGGER = LogManager.getLogger();

    public UpdateGatePacket() {
    }

    public UpdateGatePacket(GateInfo info) {
        this.info = info;
    }

    public static void encode(UpdateGatePacket packet, PacketBuffer buf) {
        LOGGER.debug("Update packet with whitelist length: {}", packet.info.ARRIVAL_WHITELIST.size());

        buf.writeCompoundTag(packet.info.writeNBT(new CompoundNBT()));
    }

    public static UpdateGatePacket decode(PacketBuffer buf) {
        UpdateGatePacket packet = new UpdateGatePacket();
        packet.info = new GateInfo(buf.readCompoundTag());

        ListIterator<String> iter = packet.info.ARRIVAL_WHITELIST.listIterator();
        for(int i=0;i<packet.info.ARRIVAL_WHITELIST.size();i++) {
            String dumbStr = iter.next();
            LOGGER.debug(" Server Decode White: {0}", dumbStr);
        }
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> GateInfoHandler.UpdateGate(this.info));

        context.get().setPacketHandled(true);
    }
}
