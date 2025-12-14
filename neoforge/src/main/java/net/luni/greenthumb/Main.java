package net.luni.greenthumb;

import net.luni.greenthumb.config.ConfigScreen;
import net.luni.greenthumb.config.ModConfig;
import net.luni.greenthumb.events.ModEvents;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(Constants.MOD_ID)
public class Main {
    public Main() {
        ModConfig.load();

        NeoForge.EVENT_BUS.register(new ModEvents());
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                () -> (minecraft, parent) -> ConfigScreen.createConfigScreen(parent)
        );
    }
}
