package com.TravelGatesMod.TravelGates.GUI;

import com.TravelGatesMod.TravelGates.travelgates;
import com.TravelGatesMod.TravelGates.util.GateInfo;
import com.TravelGatesMod.TravelGates.util.Network.Client.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.system.CallbackI;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GateScreen extends Screen {

    public static final int WIDTH = 179;
    public static final int HEIGHT = 170;

    private ResourceLocation GUI = new ResourceLocation(travelgates.MOD_ID, "textures/gui/gate_gui.png");

    public static GateInfo CallingGateInfo;
    public static List<String> DirIDs;
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
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        this.minecraft.displayGuiScreen((Screen)null);
    }

    private void SetID()
    {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        Minecraft.getInstance().displayGuiScreen(new GateIDEditScreen(this));
        //GateIDEditScreen.open(this);

        //this.onClose();
    }

    private void SetDestination()
    {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        DestinationSelectionScreen.PageNum = 0;
        Minecraft.getInstance().displayGuiScreen(new DestinationSelectionScreen(this));
        //DestinationSelectionScreen.open(this);
    }

    private void EditWhiteList()
    {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        GateWhiteListScreen.PageNum = 0;
        Minecraft.getInstance().displayGuiScreen(new GateWhiteListScreen(this));
        //GateWhiteListScreen.open(this);
    }

    private void EditBlackList()
    {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        GateBlackListScreen.PageNum = 0;
        Minecraft.getInstance().displayGuiScreen(new GateBlackListScreen(this));
        //GateBlackListScreen.open(this);
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
