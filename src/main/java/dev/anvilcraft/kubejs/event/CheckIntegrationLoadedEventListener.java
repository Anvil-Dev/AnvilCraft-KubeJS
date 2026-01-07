package dev.anvilcraft.kubejs.event;

import dev.dubhe.anvilcraft.api.event.CheckIntegrationLoadedEvent;
import dev.latvian.mods.kubejs.KubeJS;
import net.neoforged.bus.api.SubscribeEvent;

public class CheckIntegrationLoadedEventListener {
    @SubscribeEvent
    public static void onHasGuide(CheckIntegrationLoadedEvent event) {
        if (event.getId().equals(KubeJS.MOD_ID)) {
            event.setLoaded();
        }
    }
}
