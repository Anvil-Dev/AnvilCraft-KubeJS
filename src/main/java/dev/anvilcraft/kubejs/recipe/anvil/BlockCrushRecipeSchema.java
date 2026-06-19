package dev.anvilcraft.kubejs.recipe.anvil;

import dev.anvilcraft.kubejs.recipe.AnvilCraftKubeRecipe;
import dev.anvilcraft.kubejs.recipe.IDRecipeConstructor;
import dev.anvilcraft.kubejs.recipe.components.BlockStatePredicateComponent;
import dev.anvilcraft.kubejs.recipe.components.ChanceBlockStateComponent;
import dev.anvilcraft.kubejs.wrapper.TagKeyWrapper;
import dev.anvilcraft.lib.v2.util.predicate.BlockStatePredicate;
import dev.anvilcraft.lib.v2.util.predicate.ChanceBlockState;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

public interface BlockCrushRecipeSchema {
    @SuppressWarnings("unused")
    class BlockCrushKubeRecipe extends AnvilCraftKubeRecipe {
        public BlockCrushKubeRecipe input(Block... block) {
            this.setValue(INPUT, BlockStatePredicate.builder().of(block).build());
            this.save();
            return this;
        }

        public final BlockCrushKubeRecipe inputTag(String tag) {
            this.setValue(INPUT, BlockStatePredicate.builder().of(TagKeyWrapper.fromString(tag, Registries.BLOCK)).build());
            this.save();
            return this;
        }

        public BlockCrushKubeRecipe result(Block block) {
            this.setValue(RESULT, new ChanceBlockState(block.defaultBlockState(), 1.0f));
            this.save();
            return this;
        }

        @Override
        protected void validate() {
        }
    }

    RecipeKey<BlockStatePredicate> INPUT = BlockStatePredicateComponent.INSTANCE
        .key("input", ComponentRole.INPUT)
        .defaultOptional();
    RecipeKey<ChanceBlockState> RESULT = ChanceBlockStateComponent.INSTANCE
        .key("result", ComponentRole.OUTPUT)
        .defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(INPUT, RESULT)
        .factory(new KubeRecipeFactory(AnvilCraft.of("block_crush"), BlockCrushKubeRecipe.class, BlockCrushKubeRecipe::new))
        .constructor(INPUT, RESULT)
        .constructor(new IDRecipeConstructor())
        .constructor();
}
