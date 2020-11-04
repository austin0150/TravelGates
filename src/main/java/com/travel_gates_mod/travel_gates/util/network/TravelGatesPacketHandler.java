package com.travel_gates_mod.travel_gates.util.network;

import com.travel_gates_mod.travel_gates.travelgates;
import com.travel_gates_mod.travel_gates.util.network.client.SendGateScreenPacket;
import com.travel_gates_mod.travel_gates.util.network.server.UpdateGateIDPacket;
import com.travel_gates_mod.travel_gates.util.network.server.UpdateGatePacket;
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

    private TravelGatesPacketHandler () {

    }
    public static void registerMessages(){

        //Packets received by server
        INSTANCE.registerMessage(2,UpdateGatePacket.class,UpdateGatePacket::encode,UpdateGatePacket::decode,UpdateGatePacket::handle);
        INSTANCE.registerMessage(3, UpdateGateIDPacket.class,UpdateGateIDPacket::encode,UpdateGateIDPacket::decode,UpdateGateIDPacket::handle);


        //Packets received by client
        INSTANCE.registerMessage(4, SendGateScreenPacket.class,SendGateScreenPacket::encode, SendGateScreenPacket::decode,SendGateScreenPacket::handle);


    }


}
