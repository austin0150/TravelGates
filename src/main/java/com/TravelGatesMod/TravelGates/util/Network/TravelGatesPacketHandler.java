package com.TravelGatesMod.TravelGates.util.Network;

import com.TravelGatesMod.TravelGates.travelgates;
import com.TravelGatesMod.TravelGates.util.Network.Client.AddGatePacket;
import com.TravelGatesMod.TravelGates.util.Network.Client.RemoveGatePacket;
import com.TravelGatesMod.TravelGates.util.Network.Client.SendFullDirPacket;
import com.TravelGatesMod.TravelGates.util.Network.Shared.UpdateGatePacket;
import com.TravelGatesMod.TravelGates.util.Network.Server.RequestFullDirPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class TravelGatesPacketHandler {

    private static final String PROTOCOL_VERSION = "1";
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(travelgates.MOD_ID, "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals
    );

    public TravelGatesPacketHandler ()
    {
        //Packets received by clients
        //new RemoveGatePacket().register(0);
        //new AddGatePacket().register(1);
        new SendFullDirPacket().register(2);
        new UpdateGatePacket().register(3);

        //Packets received by server
        INSTANCE.registerMessage(4, RequestFullDirPacket.class,RequestFullDirPacket::encode,RequestFullDirPacket::decode,RequestFullDirPacket::handle);


        //Packets received by client
        INSTANCE.registerMessage(0,RemoveGatePacket.class,RemoveGatePacket::encode,RemoveGatePacket::decode,RemoveGatePacket::handle);
        INSTANCE.registerMessage(1, AddGatePacket.class,AddGatePacket::encode,AddGatePacket::decode,AddGatePacket::handle);

        //Packets received by both server and client
    }


}
