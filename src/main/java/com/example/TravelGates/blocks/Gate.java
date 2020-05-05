package com.example.TravelGates.blocks;

import com.example.TravelGates.GUI.GateScreen;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class Gate extends Block {
    public static String [] GATE_IDS = {"gate1", "gate2"};
    public int DestinationGateID = 0;

    public String GATE_ID ="";
    public Gate() {
        super(Block.Properties.create(
                Material.IRON)
                .sound(SoundType.METAL)
                .lightValue(10));
    }

    //@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        GateScreen screen = new GateScreen();
        screen.CallingGate = this;
        screen.open();

    }

    //This is fucking onBlockActivated
    @Override
    public ActionResultType func_225533_a_(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_) {
        if (p_225533_2_.isRemote) {
            return ActionResultType.SUCCESS;
        } else {
            GateScreen screen = new GateScreen();
            screen.CallingGate = this;
            screen.open();
            return ActionResultType.SUCCESS;
        }
    }


    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {
        entityIn.setPosition(pos.getX(),pos.getY() + 55, pos.getZ());
    }

}
