package com.miguelagsilva.skills.module.render;

import com.miguelagsilva.skills.SkillsClient;
import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

public class XRayModule extends AbstractModule {
    private final List<Block> blocks = new ArrayList<>(List.of(
            // Overworld Ores
            Blocks.COAL_ORE,
            Blocks.IRON_ORE,
            Blocks.GOLD_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.LAPIS_ORE,
            Blocks.COPPER_ORE,

            // Deepslate Ores (1.17+)
            Blocks.DEEPSLATE_COAL_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.DEEPSLATE_REDSTONE_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,

            // Nether Ores
            Blocks.NETHER_GOLD_ORE,
            Blocks.NETHER_QUARTZ_ORE,
            Blocks.ANCIENT_DEBRIS,

            // Ore/Raw Blocks
            Blocks.COAL_BLOCK,
            Blocks.IRON_BLOCK,
            Blocks.GOLD_BLOCK,
            Blocks.DIAMOND_BLOCK,
            Blocks.EMERALD_BLOCK,
            Blocks.REDSTONE_BLOCK,
            Blocks.LAPIS_BLOCK,
            Blocks.NETHERITE_BLOCK,
            Blocks.COPPER_BLOCK,
            Blocks.RAW_IRON_BLOCK,
            Blocks.RAW_COPPER_BLOCK,
            Blocks.RAW_GOLD_BLOCK,

            // Storage/Interactive Blocks
            Blocks.CHEST,
            Blocks.TRAPPED_CHEST,
            Blocks.ENDER_CHEST,
            Blocks.DISPENSER,
            Blocks.DROPPER,
            Blocks.BEACON,

            // Portal Blocks
            Blocks.END_PORTAL_FRAME,
            Blocks.END_PORTAL,
            Blocks.NETHER_PORTAL,

            // Valuable/Special Materials
            Blocks.OBSIDIAN,
            Blocks.CRYING_OBSIDIAN,
            Blocks.BLUE_ICE,
            Blocks.CLAY,
            Blocks.CONDUIT,

            // Decorative
            Blocks.BOOKSHELF,
            Blocks.CHISELED_BOOKSHELF,
            Blocks.SPONGE,
            Blocks.WET_SPONGE,

            // Hazardous
            Blocks.TNT,
            Blocks.SPAWNER,
            Blocks.LAVA,
            Blocks.WATER,

            // Dragon/Rare
            Blocks.DRAGON_EGG,
            Blocks.DRAGON_HEAD,
            Blocks.DRAGON_WALL_HEAD,

            // Nether Plants
            Blocks.NETHER_WART,

            // Infested Blocks (Silverfish)
            Blocks.INFESTED_STONE,
            Blocks.INFESTED_COBBLESTONE,
            Blocks.INFESTED_STONE_BRICKS,
            Blocks.INFESTED_CRACKED_STONE_BRICKS,
            Blocks.INFESTED_CHISELED_STONE_BRICKS,
            Blocks.INFESTED_MOSSY_STONE_BRICKS
    ));
    public XRayModule() {
        super("XRay", "See through blocks", ModuleCategory.RENDER);
    }

    public void shouldDrawSide(BlockState state, BlockState otherState, Direction side, CallbackInfoReturnable<Boolean> cir) {
        boolean shouldRender = blocks.contains(state.getBlock());
        cir.setReturnValue(shouldRender);
    }

    @Override
    public void onEnable() {
        if (client.worldRenderer == null) return;
        if (!SkillsClient.moduleManager.getModule("Fullbright").isEnabled()) {
            SkillsClient.moduleManager.getModule("Fullbright").toggle();
        }
        client.execute(() -> client.worldRenderer.reload());
    }
    @Override
    public void onDisable() {
        if (client.worldRenderer == null) return;
        if (SkillsClient.moduleManager.getModule("Fullbright").isEnabled()) {
            SkillsClient.moduleManager.getModule("Fullbright").toggle();
        }
        client.execute(() -> client.worldRenderer.reload());
    }
}
