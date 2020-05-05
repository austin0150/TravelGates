package com.example.TravelGates.GUI;

import com.example.TravelGates.blocks.Gate;
import com.example.TravelGates.travelgates;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.CreateWorldScreen;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resources.I18n;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

import java.lang.annotation.Documented;

public class GateIDEditScreen extends Screen {

    private TextFieldWidget GateIDField;
    private ResourceLocation GUI = new ResourceLocation(travelgates.MOD_ID, "textures/gui/gateidentry_gui.png");

    public static final int WIDTH = 250;
    public static final int HEIGHT = 75;


    public GateIDEditScreen() {
        super(new StringTextComponent("Enter Gate ID"));
    }

    public void setGateID(String ID)
    {
        GateScreen.CallingGate.GATE_ID = ID;
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
        if(GateScreen.CallingGate.GATE_ID != "")
        {
            this.GateIDField.setText((GateScreen.CallingGate.GATE_ID));
        }
        else
        {
            this.GateIDField.setText(("gate" + Gate.GATE_IDS.length));
        }

        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.func_212928_a(this.GateIDField);
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
        this.GateIDField.changeFocus(true);

    }

    public static void open()
    {
        Minecraft.getInstance().displayGuiScreen(new GateIDEditScreen());
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
