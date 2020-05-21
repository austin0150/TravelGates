package com.TravelGatesMod.TravelGates.GUI;

import com.TravelGatesMod.TravelGates.travelgates;
import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ListIterator;

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

        if((GateInfoHandler.GATE_DIRECTORY.size() - ((PageNum)*4)) < 4)
        {
            numToDisplay = (GateInfoHandler.GATE_DIRECTORY.size() - ((PageNum)*4));
        }

        ListIterator iterator = GateInfoHandler.GATE_DIRECTORY.listIterator((PageNum*4));
        GateInfo info;

        for(int i = 0; i < numToDisplay; i++)
        {
            info = (GateInfo)iterator.next();
            String ID = info.GATE_ID;
            if(this.PARENTSCREEN.CallingGateInfo.ARRIVAL_BLACKLIST.contains(info.GATE_ID))
            {
                LOGGER.debug("BlackList comparison true");
                dumbBool = true;
            }
            addButton(new GateCheckboxButton(x + 10, (y + (10)+ (i*27)),160, 20, info.GATE_ID, dumbBool,this));
            dumbBool = false;
        }



        if((((PageNum+1)*4) < GateInfoHandler.GATE_DIRECTORY.size())) {
            addButton(new Button(x + 140, (y + 125), 30, 20, "Next", button -> NextPage()));
        }

        if(PageNum > 0)
        {
            addButton(new Button(x+10 , (y + 125),30, 20, "Back", button -> PreviousPage()));
        }

        addButton(new Button(x+65 , (y + 125),50, 20, "Accept", button -> Accept()));
    }

    @Override
    public void AddItemToList(String ID)
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
    public void RemoveItemFromList(String ID)
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
    public void NextPage()
    {
        PageNum++;
        this.open(PARENTSCREEN);
    }

    @Override
    public void PreviousPage()
    {
        PageNum--;
        this.open(PARENTSCREEN);
    }

    @Override
    public void Accept()
    {
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
