package com.travel_gates_mod.travel_gates.util.network.server;

import com.travel_gates_mod.travel_gates.util.GateInfo;
import com.travel_gates_mod.travel_gates.util.GateInfoHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.function.Supplier;

public class UpdateGateIDPacket {

    private static final Logger LOGGER = LogManager.getLogger();

    private GateInfo info;
    private String newID;

    public UpdateGateIDPacket() {
    }

    public UpdateGateIDPacket(GateInfo info,String id) {
        this.info = info;
        this.newID = id;
    }

    public static void encode(UpdateGateIDPacket packet, PacketBuffer buf) {
        CompoundNBT compound = packet.info.writeNBT(new CompoundNBT());
        compound.putString("NEW_ID",packet.newID);
        buf.writeCompoundTag(compound);
    }

    public static UpdateGateIDPacket decode(PacketBuffer buf) {
        UpdateGateIDPacket packet = new UpdateGateIDPacket();
        CompoundNBT compound = buf.readCompoundTag();
        packet.info = new GateInfo(compound);
        packet.newID = compound.getString("NEW_ID");

        LOGGER.debug("Server recieved ID update to : " + packet.newID);
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {


            PlayerEntity sender = context.get().getSender(); // the client that sent this packet
            GateInfoHandler.updateGateID(this.info,this.newID, sender);


        });

        context.get().setPacketHandled(true);
    }
}
