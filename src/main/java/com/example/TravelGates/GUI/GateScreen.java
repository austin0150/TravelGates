package com.example.TravelGates.GUI;

import com.example.TravelGates.blocks.Gate;
import com.example.TravelGates.travelgates;
import com.example.TravelGates.util.GateInfo;
import com.mojang.blaze3d.platform.GlStateManager;
import javafx.util.Builder;
import jdk.nashorn.internal.codegen.CompilerConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.item.BookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.client.gui.widget.button.Button;

import javax.print.attribute.standard.Destination;
import java.awt.*;

public class GateScreen extends Screen {

    public static final int WIDTH = 179;
    public static final int HEIGHT = 170;

    private ResourceLocation GUI = new ResourceLocation(travelgates.MOD_ID, "textures/gui/gate_gui.png");

    public static GateInfo CallingGateInfo;
    private CheckboxButton whiteListCheckBox;

    public GateScreen() {
        super(new StringTextComponent(""));
    }

    @Override
    protected void init()
    {
        //int numIds = Gate.GATE_IDS.size();

        int x = (this.width - WIDTH)/2;
        int y = ((this.height - HEIGHT)/2) + 20;

        whiteListCheckBox = new CheckboxButton((x + 10), (y + 118),160, 20, "Use WhiteList", this.CallingGateInfo.WHITELIST_ACTIVE);


        addButton(new Button(x + 10, y + (10),160, 20, "Set Gate ID",button -> SetID()));
        addButton(new Button(x + 10, y + (37),160, 20, "Set Destination",button -> SetDestination()));
        addButton(new Button(x + 10, y + (64),160, 20, "Edit WhiteList",button -> EditWhiteList()));
        addButton(new Button(x + 10, y + (91),160, 20, "Edit BlackList",button -> EditBlackList()));
        addButton(whiteListCheckBox);
        /*
        for(int i = 0; i < numIds; i++)
        {
            final int dumbCounter = i;
            addButton(new Button(x + 10, y + (10+(i * 27)),160, 20, Gate.GATE_IDS[i],button -> PickGate(dumbCounter)));
        }

         */
    }

    @Override
    public void onClose()
    {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        this.minecraft.displayGuiScreen((Screen)null);
    }

    private void SetID()
    {
        GateIDEditScreen.open(this);

        //this.onClose();
    }

    private void SetDestination()
    {
        DestinationSelectionScreen.PageNum = 0;
        DestinationSelectionScreen.open(this);
    }

    private void EditWhiteList()
    {
        GateWhiteListScreen.PageNum = 0;
        GateWhiteListScreen.open(this);
    }

    private void EditBlackList()
    {
        GateBlackListScreen.PageNum = 0;
        GateBlackListScreen.open(this);
    }


    @Override
    public boolean isPauseScreen()
    {
        return false;
    }


    @Override
    public void render(int mouseX, int mouseY, float partialTicks)
    {
        //GlStateManager.color4f(1.0F,1.0F,1.0F,1.0F);
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH)/2;
        int relY = (this.height - HEIGHT)/2;
        this.blit(relX,relY,0,0,WIDTH,HEIGHT);
        this.drawCenteredString(this.font,("ID: " + CallingGateInfo.GATE_ID),this.width / 2, (relY + 8), 16777215);
        this.drawCenteredString(this.font,("Destination: " + CallingGateInfo.DESTINATION_GATE_ID),this.width / 2, (relY + 18), 16777215);
        super.render(mouseX,mouseY,partialTicks);
    }

    public static void open()
    {
        Minecraft.getInstance().displayGuiScreen(new GateScreen());
    }
}
