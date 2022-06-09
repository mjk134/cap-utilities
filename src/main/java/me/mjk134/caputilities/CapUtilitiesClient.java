package me.mjk134.caputilities;

import ladysnake.satin.api.event.ShaderEffectRenderCallback;
import ladysnake.satin.api.managed.ManagedShaderEffect;
import ladysnake.satin.api.managed.ShaderEffectManager;
import me.mjk134.caputilities.gui.screens.ModScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import static me.mjk134.caputilities.CapUtilities.MOD_ID;

public class CapUtilitiesClient implements ClientModInitializer {

    private static KeyBinding openModScreenKeyBinding;
    public static TextRenderer textRenderer;
    private static final CapUtilitiesClient instance = new CapUtilitiesClient();
    public final ManagedShaderEffect blur = ShaderEffectManager.getInstance().manage(new Identifier(MOD_ID, "shaders/post/fade_in_blur.json"));
    public static boolean isBlurEnabled = false;

    public static CapUtilitiesClient getInstance() {
        return instance;
    }

    @Override
    public void onInitializeClient() {
        openModScreenKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.cap-utilities.openhud", // The translation key of the keybinding's name
                InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
                GLFW.GLFW_KEY_RIGHT_SHIFT, // The keycode of the key
                "category.cap-utilities.keybinds" // The translation key of the keybinding's category.
        ));

        // textRenderer = new TextRenderer(identifier -> new FontStorage(MinecraftClient.getInstance().getTextureManager(), new Identifier(MOD_ID, "regular.ttf")).setFonts());
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openModScreenKeyBinding.wasPressed()) {
                assert client.player != null;
                client.player.sendMessage(Text.literal("Open mod screen!"));
                MinecraftClient.getInstance().setScreenAndRender(new ModScreen(Text.literal("Cap Utilities")));
            }
        });

        ShaderEffectRenderCallback.EVENT.register(tickDelta -> {
            if (isBlurEnabled) {
                blur.render(tickDelta);
            }
        });


    }

}
