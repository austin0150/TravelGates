package com.TravelGatesMod.TravelGates.GUI;

import com.TravelGatesMod.TravelGates.travelgates;
import com.TravelGatesMod.TravelGates.util.Network.Client.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ListIterator;

@OnlyIn(Dist.CLIENT)
public class GateBlackListScreen extends CheckedItemScreen {

    public static final int WIDTH = 179;
    public static final int HEIGHT = 151;
    public static int PageNum = 0;
    private GateScreen PARENTSCREEN;
    boolean dumbBool;

    private static final Logger LOGGER = LogManager.getLogger();

    private ResourceLocation GUI = new ResourceLocation(travelgates.MOD_ID, "textures/gui/destination_select_gui.png");


    protected GateBlackListScreen(GateScreen screen) {
        super(new StringTextComponent("Select BlackList"));

        PARENTSCREEN = screen;
    }

    protected void init()
    {
        int x = (this.width - WIDTH)/2;
        int y = (this.height - HEIGHT)/2;


        int numToDisplay = 4;

        if((PARENTSCREEN.DirIDs.size() - ((PageNum)*4)) < 4)
        {
            numToDisplay = (PARENTSCREEN.DirIDs.size() - ((PageNum)*4));
        }

        ListIterator <String> iterator = PARENTSCREEN.DirIDs.listIterator((PageNum*4));

        for(int i = 0; i < numToDisplay; i++)
        {
            String infoId = iterator.next();
            String ID = infoId;
            if(this.PARENTSCREEN.CallingGateInfo.ARRIVAL_BLACKLIST.contains(infoId))
            {
                LOGGER.debug("BlackList comparison true");
                dumbBool = true;
            }
            addButton(new GateCheckboxButton(x + 10, (y + (10)+ (i*27)),160, 20, ID, dumbBool,this));
            dumbBool = false;
        }



        if((((PageNum+1)*4) < PARENTSCREEN.DirIDs.size())) {
            addButton(new Button(x + 140, (y + 125), 30, 20, "Next", button -> nextPage()));
        }

        if(PageNum > 0)
        {
            addButton(new Button(x+10 , (y + 125),30, 20, "Back", button -> previousPage()));
        }

        addButton(new Button(x+65 , (y + 125),50, 20, "Accept", button -> accept()));
    }

    @Override
    public void addItemToList(String ID)
    {
        if(!this.PARENTSCREEN.CallingGateInfo.ARRIVAL_BLACKLIST.contains(ID))
        {
            this.PARENTSCREEN.CallingGateInfo.ARRIVAL_BLACKLIST.add(ID);
        }
        else
        {
            LOGGER.warn("ID was found in BlackList when it should not have been there");
        }

    }

    @Override
    public void removeItemFromList(String ID)
    {
        if(this.PARENTSCREEN.CallingGateInfo.ARRIVAL_BLACKLIST.contains(ID))
        {
            this.PARENTSCREEN.CallingGateInfo.ARRIVAL_BLACKLIST.remove(ID);

        }
        else
        {
            LOGGER.warn("ID was not found in BlackList when it should have been");
        }
    }

    @Override
    public void nextPage()
    {
        PageNum++;
        this.open(PARENTSCREEN);
    }

    @Override
    public void previousPage()
    {
        PageNum--;
        this.open(PARENTSCREEN);
    }

    @Override
    public void accept()
    {
        ClientUtil.SendUpdateToServer(PARENTSCREEN.CallingGateInfo);
        PARENTSCREEN.open();

    }


    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH)/2;
        int relY = (this.height - HEIGHT)/2;

        this.blit(relX,relY,0,0,WIDTH,HEIGHT);
        super.render(mouseX,mouseY,partialTicks);


    }

    public static void open(GateScreen parentScreen)
    {
        Minecraft.getInstance().displayGuiScreen(new GateBlackListScreen(parentScreen));

    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
