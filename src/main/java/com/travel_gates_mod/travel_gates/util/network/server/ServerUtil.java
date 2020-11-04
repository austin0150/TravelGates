package com.travel_gates_mod.travel_gates.util.network.server;

import com.travel_gates_mod.travel_gates.util.GateInfo;
import com.travel_gates_mod.travel_gates.util.GateInfoHandler;
import com.travel_gates_mod.travel_gates.util.network.client.SendGateScreenPacket;
import com.travel_gates_mod.travel_gates.util.network.TravelGatesPacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.PacketDistributor;

public class ServerUtil {

    public static void sendGateScreenToClient(PlayerEntity player, BlockPos pos) {
        GateInfo gate = GateInfoHandler.GetGateFromPos(pos);
        TravelGatesPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SendGateScreenPacket(gate));
    }

    public static CompoundNBT addIDsToNBT(CompoundNBT compound) {
        ListNBT idsList = new ListNBT();
        if(!GateInfoHandler.GATE_DIRECTORY.isEmpty()) {
            for(GateInfo info :  GateInfoHandler.GATE_DIRECTORY) {
                idsList.add(StringNBT.func_229705_a_(info.GATE_ID));
            }
        }

        compound.put("ID_DIR",idsList);
        return compound;
    }
}
