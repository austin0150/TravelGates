package com.TravelGatesMod.TravelGates.util;

import com.TravelGatesMod.TravelGates.travelgates;
import com.TravelGatesMod.TravelGates.util.Network.Server.ServerUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class GateInfoHandler extends WorldSavedData
{
    public static List<GateInfo> GATE_DIRECTORY;
    private static final String DATA_NAME = travelgates.MOD_ID + "_GateInfoHandler";
    private static final Logger LOGGER = LogManager.getLogger();
    public static long TeleportDelayTimer = 0;

    public GateInfoHandler() {
        super(DATA_NAME);
    }

    public static void ValidateGateDirectory(ServerWorld world)
    {

        ListIterator<GateInfo> iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
        {
            GateInfo info = iterator.next();
            if((!world.getBlockState(info.pos).getBlock().equals(RegistryHandler.GATE_BLOCK.get())) && (!world.getBlockState(info.pos).getBlock().equals(RegistryHandler.QUICK_GATE_BLOCK.get())))
            {
                LOGGER.warn("Found invalid Gate - Removed Gate of ID: " + info.GATE_ID);
                GateInfoHandler.GATE_DIRECTORY.remove(info);

            }
        }
    }

    public static GateInfo GetGateFromPos(BlockPos pos)
    {
        for(GateInfo info :  GATE_DIRECTORY)
        {
            if(info.pos.equals(pos))
            {
                LOGGER.info("Found Gate at pos with ID: " + info.GATE_ID);
                return info;
            }
        }
        LOGGER.error("Failed to find block matching pos when looking for info by pos");
        return null;
    }

    public static void RemoveID(String ID)
    {
        ListIterator<GateInfo> iter = GATE_DIRECTORY.listIterator();
        for(int i = 0; i < GATE_DIRECTORY.size();i++)
        {
            GateInfo info = iter.next();
            if(info.GATE_ID.equals(ID))
            {
                info.removeGate();
                break;
            }
        }
    }

    public static void AddGate(GateInfo info)
    {
        ListIterator<GateInfo> iter = GATE_DIRECTORY.listIterator();
        for(int i=0; i < GATE_DIRECTORY.size();i++)
        {
            GateInfo iterInfo = iter.next();
            if(iterInfo.CompareInfoPos(info))
            {
                return;
            }
        }

        GATE_DIRECTORY.add(info);


    }

    public static void UpdateGate(GateInfo info)
    {
        ListIterator <GateInfo> iter = GATE_DIRECTORY.listIterator();
        for(int i = 0; i < GATE_DIRECTORY.size();i++)
        {
            GateInfo gate = iter.next();
            if(gate.CompareInfoPos(info))
            {
                GATE_DIRECTORY.remove(gate);
                GATE_DIRECTORY.add(info);
                return;
            }
        }

        LOGGER.warn("Failed to find gate to update");
    }


    public static void UpdateGateID(GateInfo info, String newID, PlayerEntity player)
    {
        String oldId = info.GATE_ID;

        //Replace the old id in the whitelist/blacklist of the other gates and update Destinations using that ID
        ListIterator<GateInfo> iterator = GATE_DIRECTORY.listIterator();
        for(int i = 0; i < GATE_DIRECTORY.size();i++)
        {
            GateInfo Iterrinfo = iterator.next();

            if(Iterrinfo.GATE_ID.equals(oldId))
            {
                Iterrinfo.GATE_ID = newID;
            }

            if(Iterrinfo.ARRIVAL_WHITELIST.contains(oldId))
            {
                Iterrinfo.ARRIVAL_WHITELIST.remove(oldId);
                Iterrinfo.ARRIVAL_WHITELIST.add(newID);
            }

            if(Iterrinfo.ARRIVAL_BLACKLIST.contains(oldId))
            {
                Iterrinfo.ARRIVAL_BLACKLIST.remove(oldId);
                Iterrinfo.ARRIVAL_BLACKLIST.add(newID);
            }

            if(Iterrinfo.DESTINATION_GATE_ID.equals(oldId))
            {
                Iterrinfo.DESTINATION_GATE_ID = newID;
            }

        }

        ServerUtil.sendGateScreenToClient(player,info.pos);
    }


    @Override
    public void read(CompoundNBT nbt) {

        List<GateInfo> gateList = new ArrayList<GateInfo>();
        ListNBT nbtList = nbt.getList("DIRECTORY", 10); //this might need changing
        for(int i = 0; i < nbtList.size(); i++)
        {
            GateInfo tempInfo = new GateInfo(nbtList.getCompound(i));
            gateList.add(tempInfo);
            LOGGER.info("TravelGates:Found gate with ID:" + tempInfo.GATE_ID + " in nbt");
        }
        this.GATE_DIRECTORY = gateList;

        LOGGER.info("TravelGates:Loaded Directory from nbt with a size of:"+this.GATE_DIRECTORY.size()+" entries");
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {

        ListNBT nbtList = new ListNBT();
        if(!GATE_DIRECTORY.isEmpty())
        {
            for(GateInfo info :  GATE_DIRECTORY)
            {
                nbtList.add(info.writeNBT(new CompoundNBT()));
            }
        }

        compound.put("DIRECTORY",nbtList);

        LOGGER.info("TravelGates:Wrote Directory to nbt with a size of:" + nbtList.size() + " entries");
        return compound;
    }

    public static GateInfoHandler get(World world) {

        ServerWorld overworld = ((ServerWorld) world).getServer().getWorld(DimensionType.OVERWORLD);
        return overworld.getSavedData().getOrCreate(GateInfoHandler::new, DATA_NAME);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = travelgates.MOD_ID)
    public static class WorldDataHandlerSaveEvent {

        @SubscribeEvent
        public static void onWorldSave(WorldEvent.Save event) {

            if (!event.getWorld().isRemote()) {
                ServerWorld server = (ServerWorld) event.getWorld();
                ServerWorld overworld = server.getServer().getWorld(DimensionType.OVERWORLD);
                GateInfoHandler.get(overworld).markDirty();

            }
        }

        @SubscribeEvent
        public static void onWorldLoad(WorldEvent.Load event) {

            if (!event.getWorld().isRemote()) {
                ServerWorld server = (ServerWorld) event.getWorld();
                ServerWorld overworld = server.getServer().getWorld(DimensionType.OVERWORLD);
                GateInfoHandler.get(overworld);

                GateInfoHandler.ValidateGateDirectory(overworld);
            }
        }
    }
}
