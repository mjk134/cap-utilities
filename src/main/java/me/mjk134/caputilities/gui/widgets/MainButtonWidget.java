package me.mjk134.caputilities.gui.widgets;

import me.mjk134.caputilities.gui.utils.ExtendedRenderer2d;
import me.x150.renderer.event.events.RenderEvent;
import me.x150.renderer.renderer.ClipStack;
import me.x150.renderer.renderer.MSAAFramebuffer;
import me.x150.renderer.renderer.Rectangle;
import me.x150.renderer.renderer.Renderer2d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.awt.*;

import static me.mjk134.caputilities.CapUtilities.MOD_ID;

public class MainButtonWidget extends ButtonWidget {

    private final TextRenderer textRenderer;
    private final MutableText message;

    public MainButtonWidget(int x, int y, int width, int height, MutableText message, PressAction onPress) {
        super(x, y, width, height, message, onPress);
        this.textRenderer = MinecraftClient.getInstance().textRenderer;
        this.message = message;
        message.setStyle(Style.EMPTY.withFont(new Identifier(MOD_ID, "default")));
    }

    @Override
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        RenderEvent renderEvent = new RenderEvent(matrices);
        MSAAFramebuffer.use(MSAAFramebuffer.MAX_SAMPLES, () -> {
            ClipStack.globalInstance.addWindow(renderEvent.getStack(), new Rectangle(this.x, this.y, this.x + this.width, this.y + this.height));
            Renderer2d.renderRoundedQuad(renderEvent.getStack(), new Color(0,0,0,150), this.x, this.y,this.x + this.width, this.y + this.height,5,20);
            ExtendedRenderer2d.renderRoundedOutlineRectangle(renderEvent.getStack(), new Color(255,255,255,255), this.x, this.y,this.x + this.width, this.y + this.height,5,2, 20);
            ClipStack.globalInstance.popWindow();
        });
        textRenderer.draw(matrices, this.message, this.x + this.width / 2 - this.textRenderer.getWidth(this.message) / 2, this.y + this.height / 2 - this.textRenderer.fontHeight / 2, 16777215);
        // super.renderButton(matrices, mouseX, mouseY, delta);
    }
}
