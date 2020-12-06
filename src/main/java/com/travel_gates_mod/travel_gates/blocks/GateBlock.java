package com.travel_gates_mod.travel_gates.blocks;

import com.travel_gates_mod.travel_gates.util.network.server.ServerUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

public class GateBlock extends AbstractGateBlock {

    //On block activated
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand Hand, BlockRayTraceResult ray)  {
        if(world.isRemote) {
            return ActionResultType.SUCCESS;
        }

        ServerUtil.sendGateScreenToClient(player, pos);
        return ActionResultType.SUCCESS;
    }
}
