package com.TravelGatesMod.TravelGates.blocks;

import com.TravelGatesMod.TravelGates.GUI.GateScreen;
import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.command.CommandSource;
import net.minecraft.command.impl.TeleportCommand;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.server.TicketType;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.ListIterator;

public class Gate extends Block {


    private static final Logger LOGGER = LogManager.getLogger();
    public long TickRead = 0;
    public long ServerTickRead = 0;

    public Gate() {
        super(Block.Properties.create(
                Material.IRON)
                .sound(SoundType.METAL)
                .lightValue(10)
                .harvestLevel(1)
                .hardnessAndResistance(.95f)
                .harvestTool(ToolType.PICKAXE));

        GateInfoHandler.GATE_DIRECTORY = new ArrayList<GateInfo>();
    }

    //@Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack)
    {
        if(!worldIn.isRemote)
        {
            GateInfo info = null;
            boolean validName = false;
            int index = GateInfoHandler.GATE_DIRECTORY.size();
            while(!validName)
            {
                boolean foundName = false;
                ListIterator <GateInfo>iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
                for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
                {
                    GateInfo iterInfo = iterator.next();

                    if(("gate" + index).equals(iterInfo.GATE_ID))
                    {
                        if(GateScreen.CallingGateInfo.pos != iterInfo.pos)
                        {
                            foundName = true;
                        }
                    }

                }

                if(foundName)
                {
                    index++;
                }
                else
                {
                    info= new GateInfo(pos,"gate" + index);
                    validName = true;
                }
            }

            GateInfoHandler.GATE_DIRECTORY.add(info);

            LOGGER.info("TravelGates: Added Gate with ID:" + info.GATE_ID + " to the directory");
            GateScreen screen = new GateScreen();
            screen.CallingGateInfo = info;
            screen.open();
        }


    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        ListIterator <GateInfo>iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
        {
            GateInfo info = iterator.next();
            if(info.pos.equals(pos))
            {
                info.RemoveGate();
                info = null;
                break;
            }
        }
    }

    @Override
    public void onExplosionDestroy(World worldIn, BlockPos pos, Explosion explosionIn) {
        ListIterator <GateInfo>iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
        {
            GateInfo info = iterator.next();
            if(info.pos.equals(pos))
            {
                info.RemoveGate();
                info = null;
                break;
            }
        }
    }


    //On block activated
    @Override
    public ActionResultType func_225533_a_(BlockState p_225533_1_, World p_225533_2_, BlockPos p_225533_3_, PlayerEntity p_225533_4_, Hand p_225533_5_, BlockRayTraceResult p_225533_6_)
    {
        if (p_225533_2_.isRemote)
        {
            return ActionResultType.SUCCESS;
        }
        else
        {
            ListIterator<GateInfo> iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();

            for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
            {
                GateInfo info = iterator.next();
                if(info.pos.equals(p_225533_3_))
                {
                    GateScreen screen = new GateScreen();
                    screen.CallingGateInfo = info;
                    screen.open();
                    return ActionResultType.SUCCESS;
                }
            }

        }

        return ActionResultType.FAIL;
    }


    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {

        //Return if it's the client calling, should only happen server side
        if(worldIn.isRemote)
        {
            return;
        }
        else
        {
            if((worldIn.getGameTime() - ServerTickRead) < 25)
            {
                return;
            }

        }


        String destinationBlockId = "";
        String thisGateId = "";
        GateInfo destBlock = null;


        ServerTickRead = worldIn.getGameTime();


        ListIterator<GateInfo> iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
        {
            GateInfo info = iterator.next();
            if(info.pos.equals(pos))
            {
                destinationBlockId = info.DESTINATION_GATE_ID;
                thisGateId = info.GATE_ID;
                break;

            }

        }

        if(thisGateId == "")
        {
            LOGGER.error("TravelGates:Unable to find gate in directory matching pos:" + pos.toString());
            return;
        }

        iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        for(int i = 0 ; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
        {
            GateInfo info = iterator.next();
            if(info.GATE_ID.equals(destinationBlockId))
            {
                destBlock = info;
                break;
            }
        }
        if(destBlock == null)
        {
            LOGGER.error("TravelGates:Unable to find gate in directory with ID of:"+destinationBlockId);
            return;
        }

        //Make sure block is allowed to access destination
        if(destBlock.WHITELIST_ACTIVE)
        {
            if(!(destBlock.ARRIVAL_WHITELIST.contains(thisGateId)))
            {
                entityIn.sendMessage(new StringTextComponent("This gate is not present on the destination gate whitelist"));
                return;
            }
        }
        else
        {
            if(destBlock.ARRIVAL_BLACKLIST.contains(thisGateId))
            {
                entityIn.sendMessage(new StringTextComponent("This gate is present on the destination gate blacklist"));
                return;
            }
        }



        //Load chunck we are teleporting to
        entityIn.getEntityWorld().getChunk((int) Math.floor(destBlock.pos.getX() / 16D), (int) Math.floor(destBlock.pos.getZ() / 16D));

        //Teleport and update
        entityIn.setLocationAndAngles(destBlock.pos.getX()+.5, destBlock.pos.getY()+1, destBlock.pos.getZ()+.5,entityIn.rotationYaw, entityIn.rotationPitch);
        entityIn.setPositionAndUpdate(destBlock.pos.getX()+.5, destBlock.pos.getY()+1, destBlock.pos.getZ()+.5);


    }

}
