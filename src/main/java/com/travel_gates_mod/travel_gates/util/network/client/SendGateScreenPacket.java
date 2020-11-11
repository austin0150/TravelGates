package com.travel_gates_mod.travel_gates.util.network.client;

import com.travel_gates_mod.travel_gates.util.GateInfo;
import com.travel_gates_mod.travel_gates.util.network.server.ServerUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.network.NetworkEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.function.Supplier;

public class SendGateScreenPacket {

    GateInfo info;
    List<String> idDir;

    private static final Logger LOGGER = LogManager.getLogger();

    public SendGateScreenPacket() {
    }

    public SendGateScreenPacket(GateInfo info)
    {
        this.info = info;
    }

    public static void encode(SendGateScreenPacket packet, PacketBuffer buf) {
        CompoundNBT compound = new CompoundNBT();
        compound = packet.info.writeNBT(compound);
        compound = ServerUtil.addIDsToNBT(compound);
        buf.writeCompoundTag(compound);
    }

    public static SendGateScreenPacket decode(PacketBuffer buf) {
        SendGateScreenPacket packet = new SendGateScreenPacket();
        CompoundNBT compound = buf.readCompoundTag();
        packet.info = new GateInfo(compound);
        ListNBT nbtList = compound.getList("ID_DIR",8);

        List<String> parsedList = new ArrayList<>();
        ListIterator iterator = nbtList.listIterator();
        for(int i = 0; i < nbtList.size(); i++) {
            String tempString = iterator.next().toString();
            tempString = tempString.substring(1,(tempString.length() -1) );
            parsedList.add(tempString);
        }

        packet.idDir = parsedList;
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                LOGGER.info("Client received packet with Gate info ID: " + this.info.GATE_ID);
                ClientUtil.OpenGateScreen(this.info,this.idDir);

            });
        });

        context.get().setPacketHandled(true);
    }
}
