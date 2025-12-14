package net.luni.greenthumb.events;

import net.luni.greenthumb.config.ModConfig;
import net.luni.greenthumb.farming.CropReplanter;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent;

public class ModEvents {
    private final CropReplanter replanter = new CropReplanter();
    private final ModConfig config = ModConfig.get();

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        System.out.println("BlockBreak event fired!"); // DEBUG

        if (!config.AutoReplant.enabled) {
            System.out.println("AutoReplant disabled"); // DEBUG
            return;
        }

        Level world = (Level) event.getLevel();
        Player player = (Player) event.getPlayer();
        BlockPos pos = event.getPos();
        BlockState state = event.getState();

        System.out.println("Breaking block: " + state.getBlock()); // DEBUG
        System.out.println("Player crouching: " + player.isCrouching()); // DEBUG
        System.out.println("Require sneak: " + config.AutoReplant.requireSneak); // DEBUG

        boolean shouldReplant = (config.AutoReplant.requireSneak && !player.isCrouching())
                || (!config.AutoReplant.requireSneak && player.isCrouching());

        System.out.println("Should replant: " + shouldReplant); // DEBUG

        if (shouldReplant) {
            if (state.getBlock() instanceof CropBlock cropBlock && cropBlock.isMaxAge(state)) {
                // Cancel the normal break event
                event.setCanceled(true);

                // Drop the loot manually
                Block.dropResources(state, world, pos, null, player, player.getMainHandItem());

                // Now replant
                replanter.replant(world, pos, state, player, !player.isCreative());
            }
        }
    }
}
