package com.travel_gates_mod.travel_gates.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.travel_gates_mod.travel_gates.TravelGates;
import com.travel_gates_mod.travel_gates.util.network.client.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ListIterator;

@OnlyIn(Dist.CLIENT)
public class GateIDEditScreen extends Screen {

    private GateScreen parentScreen;
    private TextFieldWidget gateIDField;
    private ResourceLocation GUI = new ResourceLocation(TravelGates.MOD_ID, "textures/gui/gateidentry_gui.png");
    private static final Logger LOGGER = LogManager.getLogger();

    public static final int WIDTH = 250;
    public static final int HEIGHT = 75;

    public GateIDEditScreen(GateScreen parentScreen) {
        super(new StringTextComponent(""));
        this.parentScreen = parentScreen;
    }

    public void setGateID(String ID) {
        ListIterator <String>iterator = GateScreen.DirIDs.listIterator();
        for(int i = 0; i < GateScreen.DirIDs.size(); i++) {
            String str = iterator.next();

            if(ID.equals(str)) {
                LOGGER.debug("ID already exists");
                Minecraft.getInstance().displayGuiScreen(parentScreen);
                return;
            }
        }

        ClientUtil.SendIDUpdateToServer(parentScreen.CallingGateInfo, ID);
        this.onClose();
    }

    @Override
    protected void init() {
        int x = (this.width - WIDTH)/2;
        int y = (this.height - HEIGHT)/2;

        this.gateIDField = new TextFieldWidget(this.font, x+30, y+20, 200, 20, new TranslationTextComponent("selectWorld.enterName"));


        this.gateIDField.setText((GateScreen.CallingGateInfo.GATE_ID));

        this.children.add(gateIDField);

        addButton(new Button(x + 40, y + (50),160, 20,  new TranslationTextComponent("gui.generic.button.accept"), button -> setGateID(this.gateIDField.getText().trim())));

        //Init text box stuff
        this.minecraft.keyboardListener.enableRepeatEvents(true);
        this.setFocusedDefault(this.gateIDField);
    }

    public void tick() {
        this.gateIDField.tick();
    }

    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        this.minecraft.getTextureManager().bindTexture(GUI);
        int relX = (this.width - WIDTH)/2;
        int relY = (this.height - HEIGHT)/2;
        this.blit(stack, relX,relY,0,0,WIDTH,HEIGHT);
        super.render(stack, mouseX,mouseY,partialTicks);

        this.gateIDField.render(stack, mouseX, mouseY, partialTicks);

        this.gateIDField.active = true;
    }

    public static void open(GateScreen parentScreen) {
        Minecraft.getInstance().displayGuiScreen(new GateIDEditScreen(parentScreen));
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
