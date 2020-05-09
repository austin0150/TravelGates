package com.example.TravelGates.util;

import com.example.TravelGates.blocks.Gate;
import com.example.TravelGates.travelgates;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.NBTTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.lighting.BlockLightStorage;
import net.minecraft.world.storage.WorldSavedData;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class GateInfoHandler extends WorldSavedData
{
    public static List<GateInfo> GATE_DIRECTORY;
    private static final String DATA_NAME = travelgates.MOD_ID + "_GateInfoHandler";
    private static final Logger LOGGER = LogManager.getLogger();

    public GateInfoHandler() {
        super(DATA_NAME);
        //GATE_DIRECTORY = new ArrayList<GateInfo>();
    }

    @Override
    public void read(CompoundNBT nbt) {

        List<GateInfo> gateList = new ArrayList<GateInfo>();
        ListNBT nbtList = nbt.getList("DIRECTORY", 10); //this might need changing
        for(int i = 0; i < nbtList.size(); i++)
        {
            gateList.add(new GateInfo(nbtList.getCompound(i)));
        }
        this.GATE_DIRECTORY = gateList;

        LOGGER.debug("Handler Read: DIR size=" + this.GATE_DIRECTORY.size());

    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {

        ListNBT nbtList = new ListNBT();
        if(!GATE_DIRECTORY.isEmpty())
        {
            for(GateInfo info :  GATE_DIRECTORY)
            {
                nbtList.add(info.WriteNBT(new CompoundNBT()));
            }
        }

        compound.put("DIRECTORY",nbtList);

        LOGGER.debug("Handler Write: nbtList Size = " + nbtList.size());
        LOGGER.debug("Gate Dir size: " + GATE_DIRECTORY.size());

        return compound;
    }

    public static GateInfoHandler get(World world) {

        LOGGER.debug("handler.get executing");

        ServerWorld overworld = ((ServerWorld) world).getServer().getWorld(DimensionType.OVERWORLD);
        return overworld.getSavedData().getOrCreate(GateInfoHandler::new, DATA_NAME);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = travelgates.MOD_ID)
    public static class WorldDataHandlerSaveEvent {

        @SubscribeEvent
        public static void onWorldSave(WorldEvent.Save event) {

            if (event.getWorld() instanceof ServerWorld) {
                ServerWorld server = (ServerWorld) event.getWorld();
                ServerWorld overworld = server.getServer().getWorld(DimensionType.OVERWORLD);
                GateInfoHandler.get(overworld).markDirty();

            }
        }

        @SubscribeEvent
        public static void onWorldLoad(WorldEvent.Load event) {

            LOGGER.debug("Caught onWorldLoadEvent");
            if (event.getWorld() instanceof ServerWorld) {
                ServerWorld server = (ServerWorld) event.getWorld();
                ServerWorld overworld = server.getServer().getWorld(DimensionType.OVERWORLD);
                // simply calling an instance will load it's data
                GateInfoHandler.get(overworld);

                LOGGER.debug("Entered log on worldLoad event");
            }
        }
    }
}
