package net.luni.greenthumb.farming;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockState;

public class CropReplanter {

    public boolean replant(Level world, BlockPos pos, BlockState cropState, Player player, boolean consumeSeed) {
        if (world.isClientSide) return false;

        if (!(cropState.getBlock() instanceof CropBlock cropBlock)) return false;
        if (!cropBlock.isMaxAge(cropState)) return false;

        if (consumeSeed) {
            ItemStack seedStack = getSeedFromPlayer(player, cropState);
            if (seedStack == null || seedStack.isEmpty()) return false;

            seedStack.shrink(1);
        }

        BlockState newState = cropBlock.defaultBlockState().setValue(CropBlock.AGE, 0);
        world.setBlock(pos, newState, 3);
        return true;
    }

    private ItemStack getSeedFromPlayer(Player player, BlockState cropState) {
        Block cropBlock = cropState.getBlock();

        for (ItemStack stack : player.getInventory().items) {
            if (stack.isEmpty()) continue;

            Item item = stack.getItem();

            if (item instanceof BlockItem blockItem) {
                Block block = blockItem.getBlock();
                if (block == cropBlock) {
                    return stack;
                }
            }

            if (cropBlock instanceof CropBlock cropBlockInstance) {
                ItemStack seedFromCrop = cropBlockInstance.getCloneItemStack(player.level(), player.blockPosition(), cropState);
                if (!seedFromCrop.isEmpty() && seedFromCrop.getItem() == item) {
                    return stack;
                }
            }

            if (wouldPlaceCrop(item, cropBlock)) {
                return stack;
            }
        }

        return null;
    }

    private boolean wouldPlaceCrop(Item item, Block cropBlock) {
        if (item instanceof BlockItem blockItem) {
            return blockItem.getBlock() == cropBlock;
        }

        return false;
    }
}