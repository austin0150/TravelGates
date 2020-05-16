package com.example.TravelGates.GUI;

import com.example.TravelGates.blocks.Gate;
import com.example.TravelGates.travelgates;
import com.example.TravelGates.util.GateInfo;
import com.example.TravelGates.util.GateInfoHandler;
import javafx.beans.property.IntegerProperty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.lang.annotation.Documented;
import java.util.Iterator;

public class GateIDEditScreen extends Screen {

    private GateScreen PARENTSCREEN;
    private TextFieldWidget GateIDField;
    private ResourceLocation GUI = new ResourceLocation(travelgates.MOD_ID, "textures/gui/gateidentry_gui.png");

    public static final int WIDTH = 250;
    public static final int HEIGHT = 75;


    public GateIDEditScreen(GateScreen parentScreen) {
        super(new StringTextComponent("Enter Gate ID"));
        PARENTSCREEN = parentScreen;
    }

    public void setGateID(String ID)
    {
        //Gate.GATE_IDS.add(ID);

        //Check that the ID does not exist
        Iterator iterator = GateInfoHandler.GATE_DIRECTORY.iterator();
        for(int i = 0; i < GateInfoHandler.GATE_DIRECTORY.size(); i++)
        {
            GateInfo info = (GateInfo)iterator.next();

            if(ID == info.GATE_ID)
            {
                if(GateScreen.CallingGateInfo.pos != info.pos)
                {
                    return;
                }
            }

        }

        GateScreen.CallingGateInfo.GATE_ID = ID;
        
        PARENTSCREEN.open();
    }



    @Override
    protected void init()
    {
        int x = (this.width - WIDTH)/2;
        int y = (this.height - HEIGHT)/2;

        this.GateIDField = new TextFieldWidget(this.font, x+30, y+20, 200, 20, I18n.format("selectWorld.enterName")) {
            //protected String getNarrationMessage() {
                //return super.getNarrationMessage() + ". " + I18n.format("selectWorld.resultFolder") + " " + CreateWorldScreen.this.saveDirName;
            //}
        };

        //Set the initial text in the box

        this.GateIDField.setText((GateScreen.CallingGateInfo.GATE_ID));


        addButton(new Button(x + 40, y + (50),160, 20, "Accept", button -> setGateID(this.GateIDField.getText().trim())));

        //Init text box stuff
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.func_212928_a(this.GateIDField);
        this.GateIDField.changeFocus(true);
    }


    public void tick()
    {
        this.GateIDField.tick();
    }


    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH)/2;
        int relY = (this.height - HEIGHT)/2;
        this.blit(relX,relY,0,0,WIDTH,HEIGHT);
        super.render(mouseX,mouseY,partialTicks);

        this.drawString(this.font, I18n.format("selectWorld.enterName"), relX, 47, -6250336);
        this.GateIDField.render(mouseX, mouseY, partialTicks);

        this.GateIDField.active = true;

    }


    public static void open(GateScreen parentScreen)
    {
        Minecraft.getInstance().displayGuiScreen(new GateIDEditScreen(parentScreen));

    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
