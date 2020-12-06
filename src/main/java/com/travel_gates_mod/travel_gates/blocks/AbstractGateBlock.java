package com.travel_gates_mod.travel_gates.blocks;

import com.travel_gates_mod.travel_gates.TravelGates;
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
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
import java.util.function.ToIntFunction;

public abstract class AbstractGateBlock extends Block {

    private static final Logger LOGGER = LogManager.getLogger();

    public AbstractGateBlock() {
        super(Properties.create(
                Material.IRON)
                .sound(SoundType.METAL)
                .setLightLevel((light) -> 10)
                .harvestLevel(1)
                .hardnessAndResistance(.95f)
                .harvestTool(ToolType.PICKAXE));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity player, ItemStack stack) {
        if (worldIn.isRemote) {
            return;
        }

        GateInfo info = null;
        boolean validName = false;
        int index = GateInfoHandler.GATE_DIRECTORY.size();
        while (!validName) {
            boolean foundName = false;
            ListIterator<GateInfo> iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
            for (int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++) {
                GateInfo iterInfo = iterator.next();

                if (("gate" + index).equals(iterInfo.GATE_ID)) {
                    if (GateScreen.CallingGateInfo.pos != iterInfo.pos) {
                        foundName = true;
                    }
                }

            }

            if (foundName) {
                index++;
            } else {
                info = new GateInfo(pos, "gate" + index);
                validName = true;
            }
        }

        GateInfoHandler.GATE_DIRECTORY.add(info);
        LOGGER.info("Added Gate with ID:" + info.GATE_ID + " to the directory");

        ServerUtil.sendGateScreenToClient((PlayerEntity)player, pos);
    }


    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        if(worldIn.isRemote()) {
            return;
        }
        removeGateOnBreak(pos);
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        if(worldIn.isRemote()) {
            return;
        }
        removeGateOnBreak(pos);
    }

    private void removeGateOnBreak(BlockPos pos) {
        ListIterator<GateInfo> iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        while(iterator.hasNext()) {
            GateInfo info = iterator.next();
            if(info.pos.equals(pos)) {
                info.removeGate();
                break;
            }
        }
    }

    //On block activated
    @Override
    public abstract ActionResultType onBlockActivated(BlockState blockState, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult rayTraceResult);

    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {

        //Return if it's the client calling, should only happen server side
        if(worldIn.isRemote) {
            return;
        } else {
            if((worldIn.getGameTime() - GateInfoHandler.TeleportDelayTimer) < 30) {
                return;
            }
        }

        String destinationBlockId = "";
        String thisGateId = "";
        GateInfo destBlock = null;


        GateInfoHandler.TeleportDelayTimer = worldIn.getGameTime();


        ListIterator<GateInfo> iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        while(iterator.hasNext()){
            GateInfo info = iterator.next();
            if(info.pos.equals(pos)) {
                destinationBlockId = info.DESTINATION_GATE_ID;
                thisGateId = info.GATE_ID;
                break;
            }
        }

        if(thisGateId.isEmpty()) {
            LOGGER.error("Unable to find gate in directory matching pos: " + pos.toString());
            return;
        }

        iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        while(iterator.hasNext()) {
            GateInfo info = iterator.next();
            if(info.GATE_ID.equals(destinationBlockId)) {
                destBlock = info;
                break;
            }
        }
        if(destBlock == null) {
            LOGGER.error("Unable to find gate in directory with ID of: " + destinationBlockId);
            return;
        }

        //Make sure block is allowed to access destination
        if(destBlock.WHITELIST_ACTIVE) {
            if(!(destBlock.ARRIVAL_WHITELIST.contains(thisGateId))) {
                entityIn.sendMessage(new StringTextComponent("This gate is not present on the destination gate whitelist"), null);
                return;
            }
        }else{
            if(destBlock.ARRIVAL_BLACKLIST.contains(thisGateId)) {
                entityIn.sendMessage(new StringTextComponent("This gate is present on the destination gate blacklist"), null);
                return;
            }
        }

        //Load chunck we are teleporting to
        entityIn.getEntityWorld().getChunk((int) Math.floor(destBlock.pos.getX() / 16D), (int) Math.floor(destBlock.pos.getZ() / 16D));
        //Teleport and update
        entityIn.setLocationAndAngles(destBlock.pos.getX()+.5, destBlock.pos.getY()+1, destBlock.pos.getZ()+.5,entityIn.rotationYaw, entityIn.rotationPitch);
        entityIn.setPositionAndUpdate(destBlock.pos.getX()+.5, destBlock.pos.getY()+1, destBlock.pos.getZ()+.5);
    }
    public BlockItem createGateItem() {
        return new BlockItem(this, new Item.Properties().group(TravelGates.TravelGatesItemGroup.instance));
    }
}
