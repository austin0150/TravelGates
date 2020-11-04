package com.travel_gates_mod.travel_gates.util.network.client;

import com.travel_gates_mod.travel_gates.gui.GateScreen;
import com.travel_gates_mod.travel_gates.util.GateInfo;
import com.travel_gates_mod.travel_gates.util.network.server.UpdateGateIDPacket;
import com.travel_gates_mod.travel_gates.util.network.server.UpdateGatePacket;
import com.travel_gates_mod.travel_gates.util.network.TravelGatesPacketHandler;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class ClientUtil {


    public static void SendUpdateToServer(GateInfo info)
    {
        TravelGatesPacketHandler.INSTANCE.sendToServer(new UpdateGatePacket(info));
    }

    public static void SendIDUpdateToServer(GateInfo info, String newID)
    {
        TravelGatesPacketHandler.INSTANCE.sendToServer(new UpdateGateIDPacket(info,newID));
    }


    public static void OpenGateScreen(GateInfo info, List<String> IDs)
    {
        GateScreen screen = new GateScreen();
        screen.CallingGateInfo = info;
        screen.DirIDs = IDs;
        screen.open();
    }


}
