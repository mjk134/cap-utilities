package me.mjk134.caputilities.gui.utils;

import com.mojang.blaze3d.systems.RenderSystem;
import me.x150.renderer.renderer.RendererUtils;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Matrix4f;

import java.awt.*;

public class ExtendedRenderer2d {

    public static void renderRoundedOutlineRectangle(MatrixStack matrices, Color c, double fromX, double fromY, double toX, double toY, double rad, double borderSize, double samples) {
        double height = toY - fromY;
        double width = toX - fromX;
        if (height <= 0) {
            throw new IllegalArgumentException("Height should be > 0, got " + height);
        }
        if (width <= 0) {
            throw new IllegalArgumentException("Width should be > 0, got " + width);
        }
        double smallestC = Math.min(height, width) / 2d;
        rad = Math.min(rad, smallestC);
        int color = c.getRGB();
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        float f = (float) (color >> 24 & 255) / 255.0F;
        float g = (float) (color >> 16 & 255) / 255.0F;
        float h = (float) (color >> 8 & 255) / 255.0F;
        float k = (float) (color & 255) / 255.0F;

        RendererUtils.setupRender();
        RenderSystem.setShader(GameRenderer::getPositionColorShader);

        renderRoundedOutlineRectangleInternal(matrix, g, h, k, f, fromX, fromY, toX, toY, rad, borderSize, samples);
        RendererUtils.endRender();
    }

    private static void renderRoundedOutlineRectangleInternal(Matrix4f matrix, float cr, float cg, float cb, float ca, double fromX, double fromY, double toX, double toY, double rad, double borderSize, double samples) {
        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);

        double toX1 = toX - rad;
        double toY1 = toY - rad;
        double fromX1 = fromX + rad;
        double fromY1 = fromY + rad;
        double[][] map = new double[][] {
                new double[] { toX1, toY1 },
                new double[] { toX1, fromY1 },
                new double[] { fromX1, fromY1 },
                new double[] { fromX1, toY1 }
        };
        for (int i = 0; i < 4; i++) {
            double[] current = map[i];
            double max = (360 / 4d + i * 90d);
            for (double r = i * 90d; r < max; r += (90 / samples)) {
                float rad1 = (float) Math.toRadians(r);
                float sin = (float) (Math.sin(rad1) * rad);
                float cos = (float) (Math.cos(rad1) * rad);
                bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F)
                        .color(cr, cg, cb, ca)
                        .next();
            }
            // make sure we render the corner properly by adding one final vertex at the end
            float rad1 = (float) Math.toRadians(max);
            float sin = (float) (Math.sin(rad1) * rad);
            float cos = (float) (Math.cos(rad1) * rad);
            bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F)
                    .color(cr, cg, cb, ca)
                    .next();
        }

        double[][] secondmap = new double[][] { new double[] { toX1 + borderSize, toY1 + borderSize }, new double[] { toX1 + borderSize, fromY1 - borderSize },
                new double[] { fromX1 - borderSize, fromY1 - borderSize }, new double[] { fromX1 - borderSize, toY1 + borderSize } };
        for (int i = 0; i < 4; i++) {
            double[] current = secondmap[i];
            double max = (360 / 4d + i * 90d);
            for (double r = i * 90d; r < max; r += (90 / samples)) {
                float rad1 = (float) Math.toRadians(r);
                float sin = (float) (Math.sin(rad1) * rad);
                float cos = (float) (Math.cos(rad1) * rad);
                bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F)
                        .color(cr, cg, cb, ca)
                        .next();
            }
            // make sure we render the corner properly by adding one final vertex at the end
            float rad1 = (float) Math.toRadians(max);
            float sin = (float) (Math.sin(rad1) * rad);
            float cos = (float) (Math.cos(rad1) * rad);
            bufferBuilder.vertex(matrix, (float) current[0] + sin, (float) current[1] + cos, 0.0F)
                    .color(cr, cg, cb, ca)
                    .next();
        }
        BufferRenderer.drawWithShader(bufferBuilder.end());
    }

}
