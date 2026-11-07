package net.codexstudio.postify.client;

import net.codexstudio.postify.PostifyMain;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

@EventBusSubscriber(modid = PostifyMain.MOD_ID, value = Dist.CLIENT)
public class KeyInputHander {
    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) return;

        while (ClientKeyBinds.openPosters != null && ClientKeyBinds.openPosters.consumeClick()) {
            minecraft.setScreen(new net.codexstudio.postify.screens.Posters());
        }
    }
}
