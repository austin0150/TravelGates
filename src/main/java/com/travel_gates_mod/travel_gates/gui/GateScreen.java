package com.travel_gates_mod.travel_gates.gui;

import com.travel_gates_mod.travel_gates.TravelGates;
import com.travel_gates_mod.travel_gates.util.GateInfo;
import com.travel_gates_mod.travel_gates.util.network.client.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class GateScreen extends Screen {

    public static final int WIDTH = 179;
    public static final int HEIGHT = 170;

    private ResourceLocation GUI = new ResourceLocation(TravelGates.MOD_ID, "textures/gui/gate_gui.png");

    public static GateInfo CallingGateInfo;
    public static List<String> DirIDs;
    private CheckboxButton whiteListCheckBox;

    public GateScreen() {
        super(new StringTextComponent(""));
    }

    @Override
    protected void init() {

        int x = (this.width - WIDTH)/2;
        int y = ((this.height - HEIGHT)/2) + 20;

        whiteListCheckBox = new CheckboxButton((x + 10), (y + 118),160, 20, new TranslationTextComponent("gui.gate.button.whitelist").getFormattedText(), this.CallingGateInfo.WHITELIST_ACTIVE);


        addButton(new Button(x + 10, y + (10),160, 20, new TranslationTextComponent("gui.gate.button.setGate").getFormattedText(),button -> setID()));
        addButton(new Button(x + 10, y + (37),160, 20, new TranslationTextComponent("gui.gate.button.setDestination").getFormattedText(),button -> setDestination()));
        addButton(new Button(x + 10, y + (64),160, 20, new TranslationTextComponent("gui.gate.button.editWhiteList").getFormattedText(),button -> editWhiteList()));
        addButton(new Button(x + 10, y + (91),160, 20, new TranslationTextComponent("gui.gate.button.editBlackList").getFormattedText(),button -> editBlackList()));
        addButton(whiteListCheckBox);
    }

    @Override
    public void onClose() {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        this.minecraft.displayGuiScreen((Screen)null);
    }

    private void setID() {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        Minecraft.getInstance().displayGuiScreen(new GateIDEditScreen(this));

    }

    private void setDestination() {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        DestinationSelectionScreen.PageNum = 0;
        Minecraft.getInstance().displayGuiScreen(new DestinationSelectionScreen(this));
    }

    private void editWhiteList() {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        GateWhiteListScreen.PageNum = 0;
        Minecraft.getInstance().displayGuiScreen(new GateWhiteListScreen(this));
    }

    private void editBlackList() {
        this.CallingGateInfo.WHITELIST_ACTIVE = whiteListCheckBox.func_212942_a();
        ClientUtil.SendUpdateToServer(CallingGateInfo);
        GateBlackListScreen.PageNum = 0;
        Minecraft.getInstance().displayGuiScreen(new GateBlackListScreen(this));
    }


    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    public void render(int mouseX, int mouseY, float partialTicks) {
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH)/2;
        int relY = (this.height - HEIGHT)/2;
        this.blit(relX,relY,0,0,WIDTH,HEIGHT);
        this.drawCenteredString(this.font,translateAndFormat("gui.gate.text.id", CallingGateInfo.GATE_ID),this.width / 2, (relY + 8), 16777215);
        this.drawCenteredString(this.font,translateAndFormat("gui.gate.text.destination", CallingGateInfo.DESTINATION_GATE_ID),this.width / 2, (relY + 18), 16777215);
        super.render(mouseX,mouseY,partialTicks);
    }

    public static void open() {
        Minecraft.getInstance().displayGuiScreen(new GateScreen());
    }

    private static String translateAndFormat(String translationKey, String... toFill) {
        String translatedString = new TranslationTextComponent(translationKey).getFormattedText();
        for(int i = 0; i < toFill.length; i++) {
            translatedString = translatedString.replaceAll("\\{" + i + "\\}", toFill[i]);
        }
        return translatedString;
    }
}
