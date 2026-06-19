package dev.anvilcraft.kubejs.recipe.anvil;

import dev.anvilcraft.kubejs.recipe.AnvilCraftKubeRecipe;
import dev.anvilcraft.kubejs.recipe.IDRecipeConstructor;
import dev.anvilcraft.kubejs.recipe.components.BlockStatePredicateComponent;
import dev.anvilcraft.kubejs.recipe.components.ChanceBlockStateComponent;
import dev.anvilcraft.kubejs.recipe.components.ChanceItemStackComponent;
import dev.anvilcraft.kubejs.recipe.components.ItemIngredientPredicateComponent;
import dev.anvilcraft.kubejs.wrapper.TagKeyWrapper;
import dev.anvilcraft.lib.v2.util.predicate.BlockStatePredicate;
import dev.anvilcraft.lib.v2.util.predicate.ChanceBlockState;
import dev.anvilcraft.lib.v2.util.predicate.ChanceItemStack;
import dev.anvilcraft.lib.v2.util.predicate.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.component.ComponentRole;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.ArrayList;
import java.util.List;

public interface ItemInjectRecipeSchema {
    @SuppressWarnings({"unused"})
    class ItemInjectKubeRecipe extends AnvilCraftKubeRecipe {
        public ItemInjectKubeRecipe requiresTag(String ingredient, int count) {
            this.computeIfAbsent(INGREDIENTS, ArrayList::new)
                .add(ItemIngredientPredicate.Builder.item().of(TagKeyWrapper.fromString(ingredient, Registries.ITEM)).withCount(count).build());
            this.save();
            return this;
        }

        public ItemInjectKubeRecipe requiresTag(String ingredient) {
            return this.requiresTag(ingredient, 1);
        }

        public ItemInjectKubeRecipe requires(ItemStack ingredient) {
            this.computeIfAbsent(INGREDIENTS, ArrayList::new)
                .add(ItemIngredientPredicate.Builder.item().of(ingredient).build());
            this.save();
            return this;
        }

        public ItemInjectKubeRecipe result(ItemStack result, NumberProvider count) {
            this.computeIfAbsent(RESULTS, ArrayList::new)
                .add(ChanceItemStack.of(result, count));
            this.save();
            return this;
        }

        public ItemInjectKubeRecipe result(ItemStack result, float chance) {
            return this.result(result, BinomialDistributionGenerator.binomial(result.getCount(), chance));
        }

        public ItemInjectKubeRecipe result(ItemStack result) {
            return this.result(result, ConstantValue.exactly(result.getCount()));
        }

        public ItemInjectKubeRecipe inputBlock(Block... block) {
            this.setValue(BLOCK_INGREDIENT, BlockStatePredicate.builder().of(block).build());
            this.save();
            return this;
        }

        public final ItemInjectKubeRecipe inputBlockTag(String tag) {
            this.setValue(BLOCK_INGREDIENT, BlockStatePredicate.builder().of(TagKeyWrapper.fromString(tag, Registries.BLOCK)).build());
            this.save();
            return this;
        }

        public ItemInjectKubeRecipe resultBlock(Block block) {
            this.setValue(BLOCK_RESULT, new ChanceBlockState(block.defaultBlockState(), 1.0f));
            this.save();
            return this;
        }

        @Override
        protected void validate() {
        }
    }

    RecipeKey<List<ItemIngredientPredicate>> INGREDIENTS = ItemIngredientPredicateComponent.INSTANCE
        .asList()
        .key("ingredients", ComponentRole.INPUT)
        .defaultOptional();
    RecipeKey<List<ChanceItemStack>> RESULTS = ChanceItemStackComponent.INSTANCE
        .asList()
        .key("results", ComponentRole.OUTPUT)
        .defaultOptional();
    RecipeKey<BlockStatePredicate> BLOCK_INGREDIENT = BlockStatePredicateComponent.INSTANCE
        .inputKey("block_ingredient")
        .defaultOptional();
    RecipeKey<ChanceBlockState> BLOCK_RESULT = ChanceBlockStateComponent.INSTANCE
        .outputKey("block_result")
        .defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(INGREDIENTS, RESULTS, BLOCK_INGREDIENT, BLOCK_RESULT)
        .factory(new KubeRecipeFactory(AnvilCraft.of("item_inject"), ItemInjectKubeRecipe.class, ItemInjectKubeRecipe::new))
        .constructor(INGREDIENTS, RESULTS, BLOCK_INGREDIENT, BLOCK_RESULT)
        .constructor(INGREDIENTS, BLOCK_INGREDIENT, BLOCK_RESULT)
        .constructor(new IDRecipeConstructor())
        .constructor();
}
