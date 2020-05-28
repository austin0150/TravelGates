package com.TravelGatesMod.TravelGates.util.Network.Server;

import com.TravelGatesMod.TravelGates.blocks.Gate;
import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import com.TravelGatesMod.TravelGates.util.Network.Client.AddGatePacket;
import com.TravelGatesMod.TravelGates.util.Network.Client.RemoveGatePacket;
import com.TravelGatesMod.TravelGates.util.Network.Client.SendFullDirPacket;
import com.TravelGatesMod.TravelGates.util.Network.Shared.UpdateGatePacket;
import com.TravelGatesMod.TravelGates.util.Network.TravelGatesPacketHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.minecraft.world.biome.Biome.LOGGER;

@OnlyIn(Dist.DEDICATED_SERVER)
public class ServerUtil {

    private static final Logger LOGGER = LogManager.getLogger();

    public static void SendDirToClient(PlayerEntity player)
    {
        TravelGatesPacketHandler.INSTANCE.send(PacketDistributor.PLAYER.with(() -> (ServerPlayerEntity) player), new SendFullDirPacket());
        LOGGER.info("Full Directory sent to client: " + player.getDisplayName());
    }

    public static void BroadcastUpdate(GateInfo info)
    {
        // Send to all connected players
        TravelGatesPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new UpdateGatePacket(info));
        LOGGER.info("Broadcast update");
    }

    public static void BroadcastDelete(GateInfo info)
    {
        TravelGatesPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new RemoveGatePacket(info));
    }

    public static void BroadcastAddGate(GateInfo info)
    {
        TravelGatesPacketHandler.INSTANCE.send(PacketDistributor.ALL.noArg(), new AddGatePacket(info));
        LOGGER.info("Broadcast add gate");
    }

    public static CompoundNBT WriteDirToNBT(CompoundNBT compound)
    {
        ListNBT nbtList = new ListNBT();
        if(!GateInfoHandler.GATE_DIRECTORY.isEmpty())
        {
            for(GateInfo info :  GateInfoHandler.GATE_DIRECTORY)
            {
                nbtList.add(info.WriteNBT(new CompoundNBT()));
            }
        }

        compound.put("DIRECTORY",nbtList);

        LOGGER.info("Converted Server Dir to NBT");
        return compound;
    }



}
