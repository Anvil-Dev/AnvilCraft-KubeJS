package dev.anvilcraft.kubejs.wrapper;

import dev.latvian.mods.kubejs.error.KubeRuntimeException;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public interface BlockWrapper {
    static Block fromString(Object o) {
        if (o instanceof Block block) {
            return block;
        } else if (o == null) {
            return null;
        }

        String s = String.valueOf(o);

        try {
            return BuiltInRegistries.BLOCK.get(ResourceLocation.parse(s));
        } catch (ResourceLocationException e) {
            throw new KubeRuntimeException(e);
        }
    }
}
