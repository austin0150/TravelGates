package com.TravelGatesMod.TravelGates.util.Network.Client;

import com.TravelGatesMod.TravelGates.GUI.GateScreen;
import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import com.TravelGatesMod.TravelGates.util.Network.Server.ServerUtil;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
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
    List<String> IdDir;

    private static final Logger LOGGER = LogManager.getLogger();

    public SendGateScreenPacket()
    {

    }

    public SendGateScreenPacket(GateInfo info)
    {
        this.info = info;
    }

    public static void encode(SendGateScreenPacket packet, PacketBuffer buf) {
        CompoundNBT compound = new CompoundNBT();
        compound = packet.info.WriteNBT(compound);
        compound = ServerUtil.AddIDsToNBT(compound);
        buf.writeCompoundTag(compound);
    }

    public static SendGateScreenPacket decode(PacketBuffer buf) {
        SendGateScreenPacket packet = new SendGateScreenPacket();
        CompoundNBT compound = buf.readCompoundTag();
        packet.info = new GateInfo(compound);
        ListNBT nbtList = compound.getList("ID_DIR",8);

        List<String> parsedList = new ArrayList<String>();
        ListIterator iterator = nbtList.listIterator();
        for(int i = 0; i < nbtList.size(); i++)
        {
            String tempString = iterator.next().toString();
            tempString = tempString.substring(1,(tempString.length() -1) );
            parsedList.add(tempString);
        }

        packet.IdDir = parsedList;
        return packet;
    }

    public void handle(Supplier<NetworkEvent.Context> context) {
        context.get().enqueueWork(() -> {
            DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> {
                LOGGER.info("Client received packet with Gate info ID: " + this.info.GATE_ID);
                ClientUtil.OpenGateScreen(this.info,this.IdDir);

            });
        });

        context.get().setPacketHandled(true);
    }
}
