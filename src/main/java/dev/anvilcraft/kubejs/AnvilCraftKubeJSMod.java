package dev.anvilcraft.kubejs;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(AnvilCraftKubeJSMod.MOD_ID)
public class AnvilCraftKubeJSMod {
    public static final String MOD_ID = "anvilcraft_kubejs";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static ResourceLocation of(String path) {
        return ResourceLocation.fromNamespaceAndPath(AnvilCraftKubeJSMod.MOD_ID, path);
    }
}
