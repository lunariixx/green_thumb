package net.luni.greenthumb;

import net.fabricmc.api.ModInitializer;
import net.luni.greenthumb.config.ModConfig;
import net.luni.greenthumb.events.ModEvents;

public class Main implements ModInitializer {
    @Override
    public void onInitialize() {
        ModConfig.load();

        new ModEvents().register();
    }
}
