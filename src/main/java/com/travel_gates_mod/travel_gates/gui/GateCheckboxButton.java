package com.travel_gates_mod.travel_gates.gui;

import net.minecraft.client.gui.widget.button.CheckboxButton;

public class GateCheckboxButton extends CheckboxButton {

    public boolean checkStatus;
    public String ID;
    public CheckedItemScreen parentScreen;

    public GateCheckboxButton(int p_i51140_1_, int p_i51140_2_, int p_i51140_3_, int p_i51140_4_, String p_i51140_5_, boolean p_i51140_6_,CheckedItemScreen parentScreen) {
        super(p_i51140_1_, p_i51140_2_, p_i51140_3_, p_i51140_4_, p_i51140_5_, p_i51140_6_);

        this.parentScreen = parentScreen;
        ID = p_i51140_5_;
        checkStatus = p_i51140_6_;
    }

    @Override
    public void onPress()
    {
        super.onPress();
        this.checkStatus = !this.checkStatus;

        if(checkStatus) {
            parentScreen.addItemToList(this.ID);
        } else {
            parentScreen.removeItemFromList(this.ID);
        }
    }

    @Override
    public boolean func_212942_a() {
        return this.checkStatus;
    }
}
