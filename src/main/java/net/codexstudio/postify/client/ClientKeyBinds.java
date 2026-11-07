package net.codexstudio.postify.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.codexstudio.postify.PostifyMain;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

@EventBusSubscriber(modid = PostifyMain.MOD_ID, value = Dist.CLIENT)
public class ClientKeyBinds {
    public static KeyMapping openPosters;

    @SubscribeEvent
    public static void registerKeyBindings(RegisterKeyMappingsEvent event) {
        openPosters = new KeyMapping(
                "key.postify.open_posters",
                InputConstants.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                "key.categories.postify"
        );
        event.register(openPosters);
    }
}
