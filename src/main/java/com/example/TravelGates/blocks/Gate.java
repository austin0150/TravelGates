package com.example.TravelGates.blocks;

import com.example.TravelGates.GUI.GateScreen;
import com.example.TravelGates.util.GateInfo;
import com.example.TravelGates.util.GateInfoHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IProperty;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.state.properties.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Gate extends Block {


    private static final Logger LOGGER = LogManager.getLogger();
    //public static List<GateInfo> GATE_DIRECTORY;

    public Gate() {
        super(Block.Properties.create(
                Material.IRON)
                .sound(SoundType.METAL)
                .lightValue(10));

        GateInfoHandler.GATE_DIRECTORY = new ArrayList<GateInfo>();
    }

    //@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(!worldIn.isRemote)
        {
            LOGGER.info("Block about to be placed, Dir at size: " + GateInfoHandler.GATE_DIRECTORY.size());
            GateInfo info = new GateInfo(pos,"gate"+GateInfoHandler.GATE_DIRECTORY.size());
            GateInfoHandler.GATE_DIRECTORY.add(info);

            LOGGER.debug("Block added to Dir. Dir now at length:" + GateInfoHandler.GATE_DIRECTORY.size());

            GateScreen screen = new GateScreen();
            screen.CallingGateInfo = info;
            screen.open();
        }


    }

    //This is fucking onBlockActivated
    @Override
    public ActionResultType func_225533_a_(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_)
    {
        if (p_225533_2_.isRemote)
        {
            LOGGER.debug("World found to be remote, return success");
            return ActionResultType.SUCCESS;
        }
        else
        {
            LOGGER.debug("entered logic on block activated");
            ListIterator<GateInfo> iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
            GateInfo info = iterator.next();
            for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
            {

                LOGGER.debug("iter pos: " + info.pos.toString() + ". block Clicked pos: " + p_225533_3_.toString());
                if(info.pos.equals(p_225533_3_))
                {
                    LOGGER.debug("Found matching block position in directory");
                    GateScreen screen = new GateScreen();
                    screen.CallingGateInfo = info;
                    screen.open();
                    return ActionResultType.SUCCESS;
                }
                info = (GateInfo)iterator.next();
            }

        }

        return ActionResultType.FAIL;
    }


    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        entityIn.setPosition(pos.getX(),pos.getY() + 10, pos.getZ());
    }

}
