package com.TravelGatesMod.TravelGates.blocks;

import com.TravelGatesMod.TravelGates.GUI.GateScreen;
import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.ListIterator;

public class QuickGate extends Block {


    private static final Logger LOGGER = LogManager.getLogger();
    public long TickRead = 0;

    public QuickGate() {
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

            LOGGER.info("TravelGates: Added QuickGate with ID:" + info.GATE_ID + " to the directory");
            GateScreen screen = new GateScreen();
            screen.CallingGateInfo = info;
            screen.open();
        }


    }

    @Override
    public void onPlayerDestroy(IWorld worldIn, BlockPos pos, BlockState state) {
        ListIterator<GateInfo> iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
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



    @Override
    public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn)
    {


        if((worldIn.getGameTime() - TickRead) < 20)
        {
            return;
        }

        String destinationBlockId = "";
        String thisGateId = "";
        GateInfo destBlock = null;

        TickRead = worldIn.getGameTime();

        ListIterator<GateInfo> iterator = GateInfoHandler.GATE_DIRECTORY.listIterator();
        for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
        {
            GateInfo info = iterator.next();
            LOGGER.debug("iter pos = " + info.pos.toString());
            if(info.pos.equals(pos))
            {
                destinationBlockId = info.DESTINATION_GATE_ID;
                thisGateId = info.GATE_ID;
                break;

            }

        }

        if(thisGateId == "")
        {
            LOGGER.error("Unable to find gate in directory matching pos:" + pos.toString());
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
            LOGGER.error("Unable to find gate in directory with ID of:"+destinationBlockId);
            return;
        }

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


        entityIn.setPosition(destBlock.pos.getX()+.5, destBlock.pos.getY()+1, destBlock.pos.getZ()+.5);
        entityIn.setMotion(0,0,0);


    }

}
