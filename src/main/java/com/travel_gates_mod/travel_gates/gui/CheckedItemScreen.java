package com.travel_gates_mod.travel_gates.gui;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public abstract class CheckedItemScreen extends Screen {

    protected CheckedItemScreen(ITextComponent titleIn) {
        super(titleIn);
    }

    protected abstract void addItemToList(String ID);

    protected abstract void removeItemFromList(String ID);

    protected abstract void nextPage();

    protected abstract void previousPage();

    protected abstract void accept();

    protected abstract void init();

    @Override
    public boolean isPauseScreen()
    {
        return false;
    }
}
