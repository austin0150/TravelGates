package com.travel_gates_mod.travel_gates.util;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.BlockPos;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    public boolean CompareInfoPos(GateInfo info)
    {
        int thisX = this.pos.getX();
        int thisY = this.pos.getY();
        int thisZ = this.pos.getZ();

        int inX = info.pos.getX();
        int inY = info.pos.getY();
        int inZ = info.pos.getZ();

        if((thisX == inX) && (thisY == inY) && (thisZ == inZ))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public GateInfo(CompoundNBT nbt) {
        this(new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z")), nbt.getString("ID"));
        addSecondaryValues(nbt.getList("WHITELIST",8),nbt.getList("BLACKLIST", 8),nbt.getBoolean("WHITELIST_ACTIVE"), nbt.getString("DESTINATION_ID"));
    }

    public void addSecondaryValues(ListNBT white, ListNBT black, boolean whiteList, String DestinationId)
    {
        //This is really long because I have to manually convert ListNBT to ArrayList, why? i don't know.
        List<String> tempWhiteList = new ArrayList<>();
        List<String> tempBlackList = new ArrayList<>();


        ListIterator iterator = white.listIterator();
        for(int i = 0; i < white.size(); i++) {
            String tempString = iterator.next().toString();
            tempString = tempString.substring(1,(tempString.length()-1));

            tempWhiteList.add(tempString);
        }

        iterator = black.listIterator();
        for(int i = 0; i < black.size(); i++)
        {
            String tempString = iterator.next().toString();
            tempString = tempString.substring(1,(tempString.length()-1));

            tempBlackList.add(tempString);
        }

        this.ARRIVAL_WHITELIST = tempWhiteList;
        this.ARRIVAL_BLACKLIST = tempBlackList;
        this.WHITELIST_ACTIVE = whiteList;
        this.DESTINATION_GATE_ID = DestinationId;
    }

    public CompoundNBT writeNBT(CompoundNBT nbt)
    {
        nbt.putString("ID", this.GATE_ID);

        nbt.putInt("x", this.pos.getX());
        nbt.putInt("y", this.pos.getY());
        nbt.putInt("z", this.pos.getZ());

        nbt.putBoolean("WHITELIST_ACTIVE", this.WHITELIST_ACTIVE);

        //Write the lists
        ListNBT NBTWhiteList = new ListNBT();
        ListNBT NBTBlackList = new ListNBT();

        if(ARRIVAL_WHITELIST.size() > 0)
        {
            Iterator <String>iterator  = this.ARRIVAL_WHITELIST.iterator();

            //This function inside the StringNBT class appears to convert a string to an NBT

            for(int i = 0; i < ARRIVAL_WHITELIST.size(); i++) {
                StringNBT tempNBTString = StringNBT.valueOf(iterator.next());
                NBTWhiteList.add(tempNBTString);
                LOGGER.debug("Added ID to WhiteList: " + tempNBTString.toString());
            }
        }

        nbt.put("WHITELIST", NBTWhiteList);

        if(ARRIVAL_BLACKLIST.size() > 0) {
            Iterator <String>iterator = this.ARRIVAL_BLACKLIST.iterator();

            for(int i= 0; i < ARRIVAL_BLACKLIST.size();i++) {
                StringNBT tempNBTString = StringNBT.valueOf(iterator.next());
                NBTBlackList.add(tempNBTString);
            }
        }

        nbt.put("BLACKLIST", NBTBlackList);

        nbt.putString("DESTINATION_ID", this.DESTINATION_GATE_ID);

        return nbt;

    }


    /*
     * used to remove the gateinfo from all instances where it's used in the game
     */
    public void removeGate()
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
