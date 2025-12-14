package net.luni.greenthumb.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class ConfigScreen {
    public static Screen createConfigScreen(Screen screen) {
        ModConfig config = ModConfig.get();
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(screen)
                .setTitle(Component.translatable("config.greenthumb.title"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        ConfigCategory AutoReplant = builder.getOrCreateCategory(Component.translatable("config.greenthumb.auto_replant"));

        AutoReplant.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("config.greenthumb.auto_replant.enabled"),
                        config.AutoReplant.enabled)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.greenthumb.auto_replant.enabled.tooltip"))
                .setSaveConsumer(value -> config.AutoReplant.enabled = value)
                .build());
        AutoReplant.addEntry(entryBuilder.startBooleanToggle(
                        Component.translatable("config.greenthumb.auto_replant.require_sneak"),
                        config.AutoReplant.requireSneak)
                .setDefaultValue(true)
                .setTooltip(Component.translatable("config.greenthumb.auto_replant.require_sneak.tooltip"))
                .setSaveConsumer(value -> config.AutoReplant.requireSneak = value)
                .build());

        return builder.build();
    }
}
