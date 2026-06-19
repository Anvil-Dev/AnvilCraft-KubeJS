package dev.anvilcraft.kubejs;

import dev.anvilcraft.kubejs.recipe.JewelCraftingRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.BlockCompressRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.BlockCrushRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.BlockSmearRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.BulgingRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.InWorldRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.ItemInjectRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.ItemProcessRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.SqueezingRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.SuperHeatingRecipeSchema;
import dev.anvilcraft.kubejs.recipe.anvil.TimeWarpRecipeSchema;
import dev.anvilcraft.kubejs.recipe.mineral.MineralFountainChanceRecipeSchema;
import dev.anvilcraft.kubejs.recipe.mineral.MineralFountainRecipeSchema;
import dev.anvilcraft.kubejs.recipe.multiblock.MultiblockRecipeSchema;
import dev.anvilcraft.kubejs.recipe.transform.MobTransformRecipeSchema;
import dev.anvilcraft.kubejs.wrapper.BlockWrapper;
import dev.anvilcraft.lib.v2.recipe.outcome.IRecipeOutcome;
import dev.anvilcraft.lib.v2.recipe.predicate.IRecipePredicate;
import dev.anvilcraft.lib.v2.recipe.trigger.IRecipeTrigger;
import dev.anvilcraft.lib.v2.util.predicate.ChanceBlockState;
import dev.anvilcraft.lib.v2.util.predicate.ChanceItemStack;
import dev.dubhe.anvilcraft.AnvilCraft;
import dev.dubhe.anvilcraft.recipe.multiblock.BlockPredicateWithState;
import dev.dubhe.anvilcraft.recipe.transform.NumericTagValuePredicate;
import dev.dubhe.anvilcraft.recipe.transform.TagModification;
import dev.dubhe.anvilcraft.recipe.transform.TransformOptions;
import dev.latvian.mods.kubejs.plugin.ClassFilter;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeSchemaRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import dev.latvian.mods.kubejs.script.TypeWrapperRegistry;
import net.minecraft.world.level.block.Block;

public class AnvilCraftKubeJS implements KubeJSPlugin {
    @Override
    public void registerClasses(ClassFilter filter) {
        filter.allow("dev.dubhe.anvilcraft");
        filter.allow("dev.anvilcraft.lib");
        filter.allow("dev.anvilcraft.kubejs");
    }

    @Override
    public void registerBindings(BindingRegistry bindings) {
        bindings.add("ChanceItemStack", ChanceItemStack.class);
        bindings.add("ChanceBlockState", ChanceBlockState.class);
        bindings.add("BlockPredicateWithState", BlockPredicateWithState.class);

        bindings.add("ValueFunction", NumericTagValuePredicate.ValueFunction.class);
        bindings.add("ModifyOperation", TagModification.ModifyOperation.class);
        bindings.add("TransformOptions", TransformOptions.class);

        // 绑定配方接口类型
        bindings.add("IRecipeTrigger", IRecipeTrigger.class);
        bindings.add("IRecipePredicate", IRecipePredicate.class);
        bindings.add("IRecipeOutcome", IRecipeOutcome.class);
    }

    @Override
    public void registerRecipeSchemas(RecipeSchemaRegistry registry) {
        registry.register(AnvilCraft.of("block_compress"), BlockCompressRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("block_crush"), BlockCrushRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("block_smear"), BlockSmearRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("item_crush"), ItemProcessRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("boiling"), ItemProcessRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("cooking"), ItemProcessRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("item_compress"), ItemProcessRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("unpack"), ItemProcessRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("stamping"), ItemProcessRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("mesh"), ItemProcessRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("super_heating"), SuperHeatingRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("item_inject"), ItemInjectRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("squeezing"), SqueezingRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("bulging"), BulgingRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("time_warp"), TimeWarpRecipeSchema.SCHEMA);

        // 注册InWorldRecipe
        registry.register(AnvilCraft.of("in_world"), InWorldRecipeSchema.SCHEMA);

        registry.register(AnvilCraft.of("multiblock"), MultiblockRecipeSchema.SCHEMA);

        registry.register(AnvilCraft.of("mob_transform"), MobTransformRecipeSchema.SCHEMA);

        registry.register(AnvilCraft.of("mineral_fountain"), MineralFountainRecipeSchema.SCHEMA);
        registry.register(AnvilCraft.of("mineral_fountain_chance"), MineralFountainChanceRecipeSchema.SCHEMA);

        registry.register(AnvilCraft.of("jewel_crafting"), JewelCraftingRecipeSchema.SCHEMA);
    }

    @Override
    public void registerTypeWrappers(TypeWrapperRegistry registry) {
        registry.register(Block.class, BlockWrapper::fromString);
    }
}
