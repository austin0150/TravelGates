package com.example.TravelGates.GUI;

import com.example.TravelGates.blocks.Gate;
import com.example.TravelGates.travelgates;
import com.example.TravelGates.util.GateInfo;
import com.mojang.blaze3d.platform.GlStateManager;
import jdk.nashorn.internal.codegen.CompilerConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.client.gui.widget.button.Button;

import java.awt.*;

public class GateScreen extends Screen {

    public static final int WIDTH = 179;
    public static final int HEIGHT = 151;

    private ResourceLocation GUI = new ResourceLocation(travelgates.MOD_ID, "textures/gui/gate_gui.png");

    public static GateInfo CallingGateInfo;

    public GateScreen() {
        super(new StringTextComponent("Select Gate Destination"));
    }

    @Override
    protected void init()
    {
        //int numIds = Gate.GATE_IDS.size();

        int x = (this.width - WIDTH)/2;
        int y = (this.height - HEIGHT)/2;

        addButton(new Button(x + 10, y + (10),160, 20, "Set Gate ID",button -> SetID()));
        addButton(new Button(x + 10, y + (37),160, 20, "Set Destination",button -> SetID()));
        addButton(new Button(x + 10, y + (37),160, 20, "Edit WhiteList",button -> SetID()));
        addButton(new Button(x + 10, y + (37),160, 20, "Edit BlackList",button -> SetID()));
        /*
        for(int i = 0; i < numIds; i++)
        {
            final int dumbCounter = i;
            addButton(new Button(x + 10, y + (10+(i * 27)),160, 20, Gate.GATE_IDS[i],button -> PickGate(dumbCounter)));
        }

         */
    }

    private void SetID()
    {
        GateIDEditScreen.open(this);

        //this.onClose();
    }

    private void SetDestination()
    {

    }

    private void EditWhiteList()
    {

    }

    private void EditBlackList()
    {

    }


    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

    /*
    private void PickGate(int id)
    {
        CallingGate.DestinationGateID = id;
    }

     */

    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        //GlStateManager.color4f(1.0F,1.0F,1.0F,1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH)/2;
        int relY = (this.height - HEIGHT)/2;
        this.blit(relX,relY,0,0,WIDTH,HEIGHT);
        super.render(mouseX,mouseY,partialTicks);
    }

    public static void open()
    {
        Minecraft.getInstance().displayGuiScreen(new GateScreen());
    }
}