package com.example.TravelGates.util;

import net.minecraft.dispenser.Position;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import javax.swing.plaf.basic.ComboPopup;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GateInfo {
    public String GATE_ID;
    public BlockPos pos;
    public List<String> ARRIVAL_WHITELIST;
    public List<String> ARRIVAL_BLACKLIST;
    public boolean WHITELIST_ACTIVE;

    public GateInfo(BlockPos pos, String id)
    {
        this.GATE_ID = id;
        this.pos = pos;

        ARRIVAL_BLACKLIST = new ArrayList<String>();
        ARRIVAL_WHITELIST = new ArrayList<String>();
        WHITELIST_ACTIVE = false;

    }

    public GateInfo(CompoundNBT nbt)
    {
        this(new BlockPos(nbt.getInt("x"), nbt.getInt("y"), nbt.getInt("z")), nbt.getString("ID"));
        AddSecondaryValues(nbt.getList("WHITELIST", Constants.NBT.TAG_COMPOUND),nbt.getList("BLACKLIST", Constants.NBT.TAG_COMPOUND),nbt.getBoolean("WHITELIST_ACTIVE") );
    }

    public void AddSecondaryValues(ListNBT white, ListNBT black, boolean whiteList)
    {
        //This is really long because I have to manually convert ListNBT to ArrayList, why? i don't know.
        List<String> tempWhiteList = new ArrayList<String>();
        List<String> tempBlackList = new ArrayList<String>();

        Iterator iterator = white.iterator();
        String tempString = iterator.toString();
        for(int i = 0; i < white.size(); i++)
        {
            tempWhiteList.add(tempString);
            tempString = iterator.next().toString();
        }

        iterator = black.iterator();
        tempString = iterator.toString();
        for(int i = 0; i < black.size(); i++)
        {
            tempBlackList.add(tempString);
            tempString = iterator.next().toString();
        }

        this.ARRIVAL_WHITELIST = tempWhiteList;
        this. ARRIVAL_BLACKLIST = tempBlackList;
        this.WHITELIST_ACTIVE = whiteList;
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

        if(ARRIVAL_WHITELIST.size() > 0)
        {
            Iterator iterator = this.ARRIVAL_WHITELIST.iterator();

            //This function inside the StringNBT class appears to convert a string to an NBT
            StringNBT tempNBTString = StringNBT.func_229705_a_(iterator.toString());
            for(int i = 0; i < ARRIVAL_WHITELIST.size(); i++)
            {

                NBTWhiteList.add(tempNBTString);
                if(i < ARRIVAL_WHITELIST.size() - 1)
                {
                    tempNBTString = StringNBT.func_229705_a_(iterator.next().toString());
                }

            }


        }

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

        return nbt;

    }


}
