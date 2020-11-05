package com.travel_gates_mod.travel_gates.gui;

import com.travel_gates_mod.travel_gates.TravelGates;
import com.travel_gates_mod.travel_gates.util.network.client.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ListIterator;

@OnlyIn(Dist.CLIENT)
public class DestinationSelectionScreen extends Screen {


    public static final int WIDTH = 179;
    public static final int HEIGHT = 151;
    public static int PageNum = 0;
    private GateScreen parentScreen;

    private static final Logger LOGGER = LogManager.getLogger();

    private ResourceLocation GUI = new ResourceLocation(TravelGates.MOD_ID, "textures/gui/destination_select_gui.png");

    protected DestinationSelectionScreen(GateScreen screen) {
        super(new TranslationTextComponent("gui.destination.title.select"));

        parentScreen = screen;
    }

    protected void init() {
        int x = (this.width - WIDTH)/2;
        int y = (this.height - HEIGHT)/2;


        int numToDisplay = 4;

        if((parentScreen.DirIDs.size() - ((PageNum)*4)) < 4)
        {
            numToDisplay = (parentScreen.DirIDs.size() - ((PageNum)*4));
        }

        ListIterator<String> iterator = parentScreen.DirIDs.listIterator((PageNum*4));
        String infoId;

        for(int i = 0; i < numToDisplay; i++)
        {
            infoId = iterator.next();
            String finalInfoId = infoId;

            addButton(new Button(x + 10, (y + (10)+ (i*27)),160, 20, infoId, button -> setDestination(finalInfoId)));
        }



        if((((PageNum+1)*4) < parentScreen.DirIDs.size())) {
            addButton(new Button(x + 140, (y + 125), 30, 20, new TranslationTextComponent("gui.generic.button.next").getFormattedText(), button -> nextPage()));
        }

        if(PageNum > 0)
        {
            addButton(new Button(x+10 , (y + 125),30, 20, new TranslationTextComponent("gui.generic.button.back").getFormattedText(), button -> previousPage()));
        }

        addButton(new Button(x+65 , (y + 125),50, 20, new TranslationTextComponent("gui.generic.button.cancel").getFormattedText(), button -> cancel()));
    }

    public void setDestination(String ID) {
        parentScreen.CallingGateInfo.DESTINATION_GATE_ID = ID;
        ClientUtil.SendUpdateToServer(parentScreen.CallingGateInfo);
        Minecraft.getInstance().displayGuiScreen(parentScreen);
    }

    public void nextPage() {
        PageNum++;
        Minecraft.getInstance().displayGuiScreen(new DestinationSelectionScreen(parentScreen));
    }

    public void previousPage()
    {
        PageNum--;
        Minecraft.getInstance().displayGuiScreen(new DestinationSelectionScreen(parentScreen));
    }

    public void cancel() {
        parentScreen.open();
    }

    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH)/2;
        int relY = (this.height - HEIGHT)/2;

        this.blit(relX,relY,0,0,WIDTH,HEIGHT);
        super.render(mouseX,mouseY,partialTicks);
    }

    public static void open(GateScreen parentScreen) {
        Minecraft.getInstance().displayGuiScreen(new DestinationSelectionScreen(parentScreen));
    }

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }

}
