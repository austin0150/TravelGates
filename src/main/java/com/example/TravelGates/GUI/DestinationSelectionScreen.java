package com.example.TravelGates.GUI;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.WorldSelectionList;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.storage.WorldSavedData;

public class DestinationSelectionScreen extends Screen {

    ExtendedList<String> gateList;
    TextFieldWidget field_212352_g;

    protected DestinationSelectionScreen() {
        super(new StringTextComponent("Select Gate Destination"));
    }

    protected void init()
    {
        this.field_212352_g = new TextFieldWidget(this.font, this.width / 2 - 100, 22, 200, 20, this.field_212352_g, I18n.format("selectWorld.search"));
        this.selectionList = new WorldSelectionList(this, this.minecraft, this.width, this.height, 48, this.height - 64, 36, () -> {
            return this.field_212352_g.getText();
        }, this.selectionList);
    }

}
