package com.TravelGatesMod.TravelGates.util.Network.Server;

import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import com.TravelGatesMod.TravelGates.util.Network.Client.SendGateScreenPacket;
import com.TravelGatesMod.TravelGates.util.Network.TravelGatesPacketHandler;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerUtil {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void SendGateScreenToClient(PlayerEntity player, BlockPos pos)
    {
        GateInfo gate = GateInfoHandler.GetGateFromPos(pos);

        CompoundNBT compound = new CompoundNBT();
        //compound = gate.WriteNBT(compound);
        //compound = AddIDsToNBT(compound);

        TravelGatesPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SendGateScreenPacket(gate));

    }

    public static CompoundNBT AddIDsToNBT(CompoundNBT compound)
    {
        ListNBT idsList = new ListNBT();
        if(!GateInfoHandler.GATE_DIRECTORY.isEmpty())
        {
            for(GateInfo info :  GateInfoHandler.GATE_DIRECTORY)
            {
                idsList.add(StringNBT.func_229705_a_(info.GATE_ID));
            }
        }

        compound.put("ID_DIR",idsList);
        return compound;
    }


}
