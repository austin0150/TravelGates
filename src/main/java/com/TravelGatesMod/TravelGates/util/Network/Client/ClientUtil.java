package com.TravelGatesMod.TravelGates.util.Network.Client;

import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.Network.Server.RequestFullDirPacket;
import com.TravelGatesMod.TravelGates.util.Network.Shared.UpdateGatePacket;
import com.TravelGatesMod.TravelGates.util.Network.TravelGatesPacketHandler;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClientUtil {

    public static List<GateInfo> ParseNbtToDir(CompoundNBT compound)
    {
        List<GateInfo> gateList = new ArrayList<GateInfo>();
        ListNBT nbtList = compound.getList("DIRECTORY", 10); //this might need changing
        for(int i = 0; i < nbtList.size(); i++)
        {
            GateInfo tempInfo = new GateInfo(nbtList.getCompound(i));
            gateList.add(tempInfo);
        }


        return gateList;
    }

    public static void SendUpdateToServer(GateInfo info)
    {
        TravelGatesPacketHandler.INSTANCE.sendToServer(new UpdateGatePacket(info));
    }

    public static void RequestFullDir()
    {
        TravelGatesPacketHandler.INSTANCE.sendToServer(new RequestFullDirPacket());
    }


}
