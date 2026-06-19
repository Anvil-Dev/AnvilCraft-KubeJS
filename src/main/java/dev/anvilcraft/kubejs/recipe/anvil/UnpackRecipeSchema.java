package dev.anvilcraft.kubejs.recipe.anvil;

import dev.anvilcraft.kubejs.recipe.AnvilCraftKubeRecipe;
import dev.anvilcraft.kubejs.recipe.IDRecipeConstructor;
import dev.anvilcraft.kubejs.recipe.components.ChanceItemStackComponent;
import dev.anvilcraft.kubejs.recipe.components.ItemIngredientPredicateComponent;
import dev.anvilcraft.kubejs.wrapper.TagKeyWrapper;
import dev.anvilcraft.lib.v2.util.predicate.ChanceItemStack;
import dev.anvilcraft.lib.v2.util.predicate.ItemIngredientPredicate;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.latvian.mods.kubejs.recipe.RecipeKey;
import dev.latvian.mods.kubejs.recipe.schema.KubeRecipeFactory;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchema;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.providers.number.BinomialDistributionGenerator;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;

import java.util.ArrayList;
import java.util.List;

public interface UnpackRecipeSchema {
    @SuppressWarnings({"unused"})
    class UnpackKubeRecipe extends AnvilCraftKubeRecipe {
        public UnpackKubeRecipe requiresTag(String ingredient, int count) {
            this.computeIfAbsent(INGREDIENTS, ArrayList::new)
                .add(ItemIngredientPredicate.Builder.item().of(TagKeyWrapper.fromString(ingredient, Registries.ITEM)).withCount(count).build());
            this.save();
            return this;
        }

        public UnpackKubeRecipe requiresTag(String ingredient) {
            return this.requiresTag(ingredient, 1);
        }

        public UnpackKubeRecipe requires(ItemStack ingredient) {
            this.computeIfAbsent(INGREDIENTS, ArrayList::new)
                .add(ItemIngredientPredicate.Builder.item().of(ingredient).build());
            this.save();
            return this;
        }

        public UnpackKubeRecipe result(ItemStack result, NumberProvider count) {
            this.computeIfAbsent(RESULTS, ArrayList::new)
                .add(ChanceItemStack.of(result, count));
            this.save();
            return this;
        }

        public UnpackKubeRecipe result(ItemStack result, float chance) {
            return this.result(result, BinomialDistributionGenerator.binomial(result.getCount(), chance));
        }

        public UnpackKubeRecipe result(ItemStack result) {
            return this.result(result, ConstantValue.exactly(result.getCount()));
        }

        @Override
        protected void validate() {
        }
    }

    RecipeKey<List<ItemIngredientPredicate>> INGREDIENTS = ItemIngredientPredicateComponent.INSTANCE
        .asList()
        .inputKey("ingredients")
        .defaultOptional();
    RecipeKey<List<ChanceItemStack>> RESULTS = ChanceItemStackComponent.INSTANCE
        .asList()
        .inputKey("results")
        .defaultOptional();

    RecipeSchema SCHEMA = new RecipeSchema(INGREDIENTS, RESULTS)
        .factory(new KubeRecipeFactory(AnvilCraft.of("unpack"), UnpackKubeRecipe.class, UnpackKubeRecipe::new))
        .constructor(INGREDIENTS, RESULTS)
        .constructor(new IDRecipeConstructor())
        .constructor();
}
