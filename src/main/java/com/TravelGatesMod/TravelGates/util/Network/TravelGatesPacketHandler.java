package com.TravelGatesMod.TravelGates.util.Network;

import com.TravelGatesMod.TravelGates.travelgates;
import com.TravelGatesMod.TravelGates.util.Network.Client.SendGateScreenPacket;
import com.TravelGatesMod.TravelGates.util.Network.Server.UpdateGateIDPacket;
import com.TravelGatesMod.TravelGates.util.Network.Server.UpdateGatePacket;
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

        //Packets received by server
        INSTANCE.registerMessage(2,UpdateGatePacket.class,UpdateGatePacket::encode,UpdateGatePacket::decode,UpdateGatePacket::handle);
        INSTANCE.registerMessage(3, UpdateGateIDPacket.class,UpdateGateIDPacket::encode,UpdateGateIDPacket::decode,UpdateGateIDPacket::handle);


        //Packets received by client
        INSTANCE.registerMessage(4, SendGateScreenPacket.class,SendGateScreenPacket::encode, SendGateScreenPacket::decode,SendGateScreenPacket::handle);


    }


}
