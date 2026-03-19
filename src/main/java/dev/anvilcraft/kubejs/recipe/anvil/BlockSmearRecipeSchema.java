package dev.anvilcraft.kubejs.recipe.anvil;

import dev.anvilcraft.kubejs.recipe.AnvilCraftKubeRecipe;
import dev.anvilcraft.kubejs.recipe.IDRecipeConstructor;
import dev.anvilcraft.kubejs.recipe.components.BlockStatePredicateComponent;
import dev.anvilcraft.kubejs.recipe.components.ChanceBlockStateComponent;
import dev.anvilcraft.kubejs.wrapper.TagKeyWrapper;
import dev.anvilcraft.lib.recipe.component.BlockStatePredicate;
import dev.anvilcraft.lib.recipe.component.ChanceBlockState;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public interface BlockSmearRecipeSchema {
    @SuppressWarnings("unused")
    class BlockSmearKubeRecipe extends AnvilCraftKubeRecipe {
        public BlockSmearKubeRecipe input(Block... block) {
            this.computeIfAbsent(INPUTS, ArrayList::new)
                .add(BlockStatePredicate.builder().of(block).build());
            this.save();
            return this;
        }

        public final BlockSmearKubeRecipe inputTag(String... block) {
            this.computeIfAbsent(INPUTS, ArrayList::new)
                .addAll(Arrays.stream(block).map(tag -> BlockStatePredicate.builder().of(
                    TagKeyWrapper.fromString(tag, Registries.BLOCK)
                ).build()).toList());
            this.save();
            return this;
        }

        public BlockSmearKubeRecipe result(Block block) {
            this.setValue(RESULT, new ChanceBlockState(block.defaultBlockState(), 1.0f));
            this.save();
            return this;
        }

        @Override
        protected void validate() {
        }
    }

    RecipeKey<List<BlockStatePredicate>> INPUTS = BlockStatePredicateComponent.INSTANCE
        .asList()
        .key("inputs", ComponentRole.INPUT)
        .defaultOptional();
    RecipeKey<ChanceBlockState> RESULT = ChanceBlockStateComponent.INSTANCE
        .key("result", ComponentRole.OUTPUT)
        .defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(INPUTS, RESULT)
        .factory(new KubeRecipeFactory(AnvilCraft.of("block_smear"), BlockSmearKubeRecipe.class, BlockSmearKubeRecipe::new))
        .constructor(INPUTS, RESULT)
        .constructor(new IDRecipeConstructor())
        .constructor();
}
