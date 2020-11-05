package com.travel_gates_mod.travel_gates.blocks;

import com.travel_gates_mod.travel_gates.gui.GateScreen;
import com.travel_gates_mod.travel_gates.util.GateInfo;
import com.travel_gates_mod.travel_gates.util.GateInfoHandler;
import com.travel_gates_mod.travel_gates.util.network.server.ServerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ListIterator;

public class QuickGateBlock extends AbstractGateBlock {

    //On Block Activated
    @Override
    public ActionResultType func_225533_a_(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand Hand, BlockRayTraceResult ray) {
        return ActionResultType.PASS;
    }

}
