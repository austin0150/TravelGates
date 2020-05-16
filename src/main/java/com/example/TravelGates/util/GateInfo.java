package com.example.TravelGates.util;

import net.minecraft.dispenser.Position;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import sun.rmi.runtime.Log;

import javax.swing.plaf.basic.ComboPopup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class GateInfo {
    public String GATE_ID;
    public String DESTINATION_GATE_ID;
    public BlockPos pos;
    public List<String> ARRIVAL_WHITELIST;
    public List<String> ARRIVAL_BLACKLIST;
    public boolean WHITELIST_ACTIVE;

    private static final Logger LOGGER = LogManager.getLogger();

    public GateInfo(BlockPos pos, String id)
    {
        this.GATE_ID = id;
        this.pos = pos;
        this.DESTINATION_GATE_ID = GATE_ID;

        ARRIVAL_BLACKLIST = new ArrayList<String>();
        ARRIVAL_WHITELIST = new ArrayList<String>();
        WHITELIST_ACTIVE = false;

    }

    public GateInfo(CompoundNBT nbt)
    {
        this(new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z")), nbt.getString("ID"));
        AddSecondaryValues(nbt.getList("WHITELIST",8),nbt.getList("BLACKLIST", 8),nbt.getBoolean("WHITELIST_ACTIVE"), nbt.getString("DESTINATION_ID"));
    }

    public void AddSecondaryValues(ListNBT white, ListNBT black, boolean whiteList, String DestinationId)
    {
        //This is really long because I have to manually convert ListNBT to ArrayList, why? i don't know.
        List<String> tempWhiteList = new ArrayList<String>();
        List<String> tempBlackList = new ArrayList<String>();

        LOGGER.debug("Read ListNBT with length: " + white.size());

        ListIterator iterator = white.listIterator();
        for(int i = 0; i < white.size(); i++)
        {
            //String tempString = iterator.next().toString();
            String tempString = iterator.next().toString();
            tempString = tempString;
            tempWhiteList.add(tempString);

            LOGGER.debug("Reading into whitelist: " + tempString);

        }

        iterator = black.listIterator();
        for(int i = 0; i < black.size(); i++)
        {
            String tempString = iterator.next().toString();
            tempBlackList.add(tempString);
        }

        this.ARRIVAL_WHITELIST = tempWhiteList;
        this. ARRIVAL_BLACKLIST = tempBlackList;
        this.WHITELIST_ACTIVE = whiteList;
        this.DESTINATION_GATE_ID = DestinationId;

        LOGGER.debug("ID read as:" + this.GATE_ID);
        LOGGER.debug("destination read as:" + this.DESTINATION_GATE_ID);
        LOGGER.debug("NBT Whitelist read with length: " + this.ARRIVAL_WHITELIST.size());
    }

    public CompoundNBT WriteNBT(CompoundNBT nbt)
    {
        nbt.putString("ID", this.GATE_ID);

        nbt.putInt("x", this.pos.getX());
        nbt.putInt("y", this.pos.getY());
        nbt.putInt("z", this.pos.getZ());

        nbt.putBoolean("WHITELIST_ACTIVE", this.WHITELIST_ACTIVE);

        //Write the lists
        ListNBT NBTWhiteList = new ListNBT();
        ListNBT NBTBlackList = new ListNBT();

        LOGGER.debug("Writing NBT, whitelist size: " + ARRIVAL_WHITELIST.size());
        if(ARRIVAL_WHITELIST.size() > 0)
        {
            Iterator iterator = this.ARRIVAL_WHITELIST.iterator();

            //This function inside the StringNBT class appears to convert a string to an NBT

            for(int i = 0; i < ARRIVAL_WHITELIST.size(); i++)
            {
                StringNBT tempNBTString = StringNBT.func_229705_a_(iterator.next().toString());
                NBTWhiteList.add(tempNBTString);
                LOGGER.debug("Added: " + tempNBTString.toString() + " to WhiteList NBT");

                /*
                if(i < ARRIVAL_WHITELIST.size() - 1)
                {
                    tempNBTString = StringNBT.func_229705_a_(iterator.next().toString());
                }

                 */

            }


        }

        LOGGER.debug("Tag type: " + NBTWhiteList.getTagType());

        nbt.put("WHITELIST", NBTWhiteList);

        if(ARRIVAL_BLACKLIST.size() > 0)
        {
            Iterator iterator = this.ARRIVAL_BLACKLIST.iterator();

            StringNBT tempNBTString = StringNBT.func_229705_a_(iterator.toString());
            for(int i= 0; i < ARRIVAL_BLACKLIST.size();i++)
            {
                NBTBlackList.add(tempNBTString);
                if(i < ARRIVAL_BLACKLIST.size() - 1)
                {
                    tempNBTString = StringNBT.func_229705_a_(iterator.next().toString());
                }
            }
        }

        nbt.put("BLACKLIST", NBTBlackList);

        nbt.putString("DESTINATION_ID", this.DESTINATION_GATE_ID);
        LOGGER.debug("Wrote dest:" + this.DESTINATION_GATE_ID);

        LOGGER.debug("About to return written NBT");
        return nbt;

    }


    /*
     * used to remove the gateinfo from all instances where it's used in the game
     */
    public void RemoveGate()
    {
        //Scan through all the gates
        ListIterator <GateInfo>iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
        {
            GateInfo info = iterator.next();
            if(!info.pos.equals(this.pos))
            {
                if(info.ARRIVAL_WHITELIST.contains(this.GATE_ID)) {

                    info.ARRIVAL_WHITELIST.remove(this.GATE_ID);
                }

                if(info.ARRIVAL_BLACKLIST.contains(this.GATE_ID))
                {
                    info.ARRIVAL_BLACKLIST.remove(this.GATE_ID);
                }

                if(info.DESTINATION_GATE_ID.equals(this.GATE_ID))
                {
                    info.DESTINATION_GATE_ID = info.GATE_ID;
                }
            }
        }

        GateInfoHandler.GATE_DIRECTORY.remove(this);
    }

}
