package net.luni.greenthumb.events;

import net.fabricmc.fabric.api.event.player.PlayerBlockBreakEvents;
import net.luni.greenthumb.config.ModConfig;
import net.luni.greenthumb.farming.CropReplanter;

public class ModEvents {
    ModConfig config = ModConfig.get();

    private final CropReplanter replanter = new CropReplanter();

    public void register() {
        PlayerBlockBreakEvents.AFTER.register((world, player, pos, state, entity) -> {
            if (!config.AutoReplant.enabled) return;

            boolean shouldReplant = (config.AutoReplant.requireSneak && !player.isCrouching()) ||
                    (!config.AutoReplant.requireSneak && player.isCrouching());

            if (shouldReplant) {
                replanter.replant(world, pos, state, player, !player.isCreative());
            }
        });
    }
}
