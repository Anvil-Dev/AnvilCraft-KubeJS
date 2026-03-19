package dev.anvilcraft.kubejs.wrapper;

import dev.latvian.mods.kubejs.error.KubeRuntimeException;
import net.minecraft.ResourceLocationException;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;

public interface TagKeyWrapper {
    static String toString(@Nullable TagKey<Item> tag) {
        return tag == null ? "" : tag.toString();
    }

    static <T> TagKey<T> fromString(Object o, ResourceKey<Registry<T>> registry) {
        if (o instanceof TagKey<?> tag && tag.registry().equals(registry)) {
            return tag.cast(registry).orElseThrow();
        } else if (o == null) {
            return null;
        }

        String s = String.valueOf(o);

        if (!s.startsWith("#")) throw new KubeRuntimeException("Tag must starts with '#', got %s".formatted(s));
        try {
            return TagKey.create(registry, ResourceLocation.parse(s.substring(1)));
        } catch (ResourceLocationException e) {
            throw new KubeRuntimeException(e);
        }
    }
}
