package me.mjk134.caputilities.mixin;

import me.mjk134.caputilities.CapUtilitiesClient;
import me.mjk134.caputilities.gui.screens.ModScreen;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public class MixinMinecraftClient {

    @Inject(method = "setScreen",
            at = @At(value = "FIELD",
                    target = "Lnet/minecraft/client/MinecraftClient;currentScreen:Lnet/minecraft/client/gui/screen/Screen;",
                    opcode = Opcodes.PUTFIELD))
    private void onScreenOpen(Screen newScreen, CallbackInfo info) {
        CapUtilitiesClient.isBlurEnabled = newScreen instanceof ModScreen;
    }

}
