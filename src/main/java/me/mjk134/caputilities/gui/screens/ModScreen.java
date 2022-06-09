package me.mjk134.caputilities.gui.screens;

import me.mjk134.caputilities.CapUtilitiesClient;
import me.mjk134.caputilities.gui.widgets.MainButtonWidget;
import me.x150.renderer.event.events.ScreenRenderEvent;
import me.x150.renderer.renderer.ClipStack;
import me.x150.renderer.renderer.MSAAFramebuffer;
import me.x150.renderer.renderer.Rectangle;
import me.x150.renderer.renderer.Renderer2d;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

import static me.mjk134.caputilities.CapUtilities.MOD_ID;

@Environment(EnvType.CLIENT)
public class ModScreen extends Screen {

    private final MutableText title;

    public ModScreen(MutableText title) {
        super(title);
        this.title = Text.literal("Cap Utilities");
        this.title.setStyle(Style.EMPTY.withFont(new Identifier(MOD_ID, "bold")));
    }


    protected void init() {

        this.addDrawableChild(new MainButtonWidget(this.width / 2 - 100, this.height / 2 - 50, 200, 75, Text.literal("Tweaks"), (button) -> {
            //this.client.openScreen(new SettingsScreen(this));
        }));
        super.init();
    }

    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        CapUtilitiesClient.getInstance().blur.render(delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, this.height / 2 - this.textRenderer.fontHeight - 20, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}
