package com.TravelGatesMod.TravelGates.GUI;

import com.TravelGatesMod.TravelGates.travelgates;
import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.GateInfoHandler;
import com.TravelGatesMod.TravelGates.util.Network.Client.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ListIterator;

@OnlyIn(Dist.CLIENT)
public class GateIDEditScreen extends Screen {

    private GateScreen PARENTSCREEN;
    private TextFieldWidget GateIDField;
    private ResourceLocation GUI = new ResourceLocation(travelgates.MOD_ID, "textures/gui/gateidentry_gui.png");
    private static final Logger LOGGER = LogManager.getLogger();

    public static final int WIDTH = 250;
    public static final int HEIGHT = 75;


    public GateIDEditScreen(GateScreen parentScreen) {
        super(new StringTextComponent(""));
        PARENTSCREEN = parentScreen;
    }

    public void setGateID(String ID)
    {
        //Gate.GATE_IDS.add(ID);

        //Check that the ID does not exist
        ListIterator <String>iterator = PARENTSCREEN.DirIDs.listIterator();
        for(int i = 0; i < PARENTSCREEN.DirIDs.size(); i++)
        {
            String str = iterator.next();

            if(ID.equals(str))
            {
                LOGGER.debug("ID already exists");
                Minecraft.getInstance().displayGuiScreen(PARENTSCREEN);
                return;
            }

        }


        ClientUtil.SendIDUpdateToServer(PARENTSCREEN.CallingGateInfo, ID);
        this.onClose();
    }



    @Override
    protected void init()
    {
        int x = (this.width - WIDTH)/2;
        int y = (this.height - HEIGHT)/2;

        this.GateIDField = new TextFieldWidget(this.font, x+30, y+20, 200, 20, I18n.format("selectWorld.enterName"));

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

        //this.drawString(this.font, I18n.format("selectWorld.enterName"), relX, 47, -6250336);
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
