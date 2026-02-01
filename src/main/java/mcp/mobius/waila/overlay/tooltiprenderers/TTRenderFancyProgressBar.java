package mcp.mobius.waila.overlay.tooltiprenderers;

import java.awt.Dimension;

import mcp.mobius.waila.api.IWailaVariableWidthTooltipRenderer;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.overlay.DisplayUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

import org.lwjgl.opengl.GL11;

import mcp.mobius.waila.api.IWailaCommonAccessor;

public class TTRenderFancyProgressBar implements IWailaVariableWidthTooltipRenderer {

    int maxStringW;

    @Override
    public Dimension getSize(String[] params, IWailaCommonAccessor accessor) {
        return new Dimension(DisplayUtil.getDisplayWidth(params.length > 4 ? params[4] : ""), Integer.parseInt(params[0]));
    }

    @Override
    public void setMaxLineWidth(int width) {
        maxStringW = width + 2;
    }

    @Override
    public int getMaxLineWidth() {
        return maxStringW;
    }

    @Override
    public void draw(String[] params, IWailaCommonAccessor accessor) {
        int height = Integer.parseInt(params[0]);
        int current = Integer.parseInt(params[1]);
        int max = Integer.parseInt(params[2]);
        int color = params.length > 3 ? Integer.parseInt(params[3], 16) : 0xFFD700;
        String text = params.length > 4 ? params[4] : "";

        float progress = max > 0 ? (float) current / (float) max : 0.0f;

        drawProgressBar(0, 0, maxStringW, height, progress, color, text);
    }

    private void drawProgressBar(int x, int y, int width, int height, float progress, int fillColor, String text) {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        drawRect(x, y, x + width, y + height, 0xAA000000);

        drawRect(x, y, x + width, y + 1, 0xFF555555);
        drawRect(x, y + height - 1, x + width, y + height, 0xFF555555);
        drawRect(x, y, x + 1, y + height, 0xFF555555);
        drawRect(x + width - 1, y, x + width, y + height, 0xFF555555);

        int innerWidth = width - 2;
        int filled = (int) (innerWidth * clamp(progress));

        if (filled > 0) {
            drawGradientRect(
                    x + 1,
                    y + 1,
                    x + 1 + filled,
                    y + height - 1,
                    fillColor | 0xFF000000,
                    darken(fillColor, 0.6f) | 0xFF000000);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        float scale = 0.75F;

        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, 1.0f);

        int textWidth = fontRenderer.getStringWidth(text);
        int textHeight = fontRenderer.FONT_HEIGHT;

        float centerX = x + width / 2.0f;
        float centerY = y + height / 2.0f;

        int drawX = (int) ((centerX - (textWidth * scale) / 2) / scale);
        int drawY = (int) ((centerY - (textHeight * scale) / 2) / scale) + 1;

        fontRenderer.drawStringWithShadow(text, drawX, drawY, 0xFFFFFF);

        GL11.glPopMatrix();
    }

    private float clamp(float value) {
        return value < 0 ? 0 : (value > 1 ? 1 : value);
    }

    private int darken(int color, float factor) {
        int r = (int) (((color >> 16) & 0xFF) * factor);
        int g = (int) (((color >> 8) & 0xFF) * factor);
        int b = (int) ((color & 0xFF) * factor);
        return (r << 16) | (g << 8) | b;
    }

    private void drawRect(int left, int top, int right, int bottom, int color) {
        float a = (color >> 24 & 255) / 255.0F;
        float r = (color >> 16 & 255) / 255.0F;
        float g = (color >> 8 & 255) / 255.0F;
        float b = (color & 255) / 255.0F;

        GL11.glColor4f(r, g, b, a);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(left, bottom);
        GL11.glVertex2f(right, bottom);
        GL11.glVertex2f(right, top);
        GL11.glVertex2f(left, top);
        GL11.glEnd();
    }

    private void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
        float sa = (startColor >> 24 & 255) / 255.0F;
        float sr = (startColor >> 16 & 255) / 255.0F;
        float sg = (startColor >> 8 & 255) / 255.0F;
        float sb = (startColor & 255) / 255.0F;

        float ea = (endColor >> 24 & 255) / 255.0F;
        float er = (endColor >> 16 & 255) / 255.0F;
        float eg = (endColor >> 8 & 255) / 255.0F;
        float eb = (endColor & 255) / 255.0F;

        GL11.glShadeModel(GL11.GL_SMOOTH);
        GL11.glBegin(GL11.GL_QUADS);

        GL11.glColor4f(sr, sg, sb, sa);
        GL11.glVertex2f(left, bottom);
        GL11.glVertex2f(right, bottom);

        GL11.glColor4f(er, eg, eb, ea);
        GL11.glVertex2f(right, top);
        GL11.glVertex2f(left, top);

        GL11.glEnd();
        GL11.glShadeModel(GL11.GL_FLAT);
    }

    public static String create(int height, int currentValue, int maxValue, int color, String text) {
        return SpecialChars.getRenderString(
                "waila.fancy_progress_bar",
                String.valueOf(height),
                String.valueOf(currentValue),
                String.valueOf(maxValue),
                String.valueOf(color),
                text);
    }

    public static String create(int currentValue, int maxValue, int color, String text) {
        return create(10, currentValue, maxValue, color, text);
    }
}