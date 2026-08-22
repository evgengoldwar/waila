package mcp.mobius.waila.overlay.tooltiprenderers;

import java.awt.Dimension;

import net.minecraft.client.gui.Gui;

import mcp.mobius.waila.api.IWailaCommonAccessor;
import mcp.mobius.waila.api.IWailaVariableWidthTooltipRenderer;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.overlay.DisplayUtil;

public class TTRenderBar implements IWailaVariableWidthTooltipRenderer {

    private int maxStringW;
    private static final int HEIGHT = 12;

    @Override
    public Dimension getSize(String[] params, IWailaCommonAccessor accessor) {
        String text = params[0];

        int displayWidth = DisplayUtil.getDisplayWidth(text);

        return new Dimension(displayWidth + 4, HEIGHT);
    }

    @Override
    public void draw(String[] params, IWailaCommonAccessor accessor) {
        String text = params[0];
        int topColor = 0xFFFFEE55;
        int bottomColor = 0xFFFFA500;
        int fillWidth = maxStringW - 2;

        if (params.length > 2) {
            try {
                topColor = Integer.parseInt(params[1]);
                bottomColor = Integer.parseInt(params[2]);

            } catch (NumberFormatException ignored) {}
        }

        if (params.length > 3) {
            try {
                double ratio = Double.parseDouble(params[3]);
                ratio = Math.max(0.0, Math.min(1.0, ratio));
                fillWidth = (int) ((maxStringW - 2) * ratio);
            } catch (NumberFormatException ignored) {}
        }

        drawVerticalGradient(maxStringW - 1, 0xFF2A2A2A, 0xFF111111);

        if (fillWidth > 0) {
            drawVerticalGradient(1 + fillWidth, topColor, bottomColor);
        }

        DisplayUtil.drawThickBeveledBox(0, 0, maxStringW, HEIGHT, 1, 0xFF505050, 0xFF505050, -1);
        DisplayUtil.drawString(text, 2, 2, 0xFFFFFFFF, true);
    }

    @Override
    public void setMaxLineWidth(int width) {
        maxStringW = width;
    }

    @Override
    public int getMaxLineWidth() {
        return maxStringW;
    }

    private void drawVerticalGradient(int x2, int topColor, int bottomColor) {
        int h = 11;
        if (x2 <= 1) {
            return;
        }

        for (int i = 0; i < h; i++) {
            float ratio = (float) i / (float) h;
            int color = blendColors(topColor, bottomColor, ratio);
            Gui.drawRect(1, i, x2, i + 1, color);
        }
    }

    private int blendColors(int c1, int c2, float ratio) {
        int a1 = (c1 >> 24 & 0xFF), r1 = (c1 >> 16 & 0xFF), g1 = (c1 >> 8 & 0xFF), b1 = (c1 & 0xFF);
        int a2 = (c2 >> 24 & 0xFF), r2 = (c2 >> 16 & 0xFF), g2 = (c2 >> 8 & 0xFF), b2 = (c2 & 0xFF);

        int a = (int) (a1 + (a2 - a1) * ratio);
        int r = (int) (r1 + (r2 - r1) * ratio);
        int g = (int) (g1 + (g2 - g1) * ratio);
        int b = (int) (b1 + (b2 - b1) * ratio);

        return (a << 24) | (r << 16) | (g << 8) | b;
    }

    public static String create(String text, int topColor, int bottomColor, double progress) {
        return SpecialChars.getRenderString(
                "waila.bar",
                text,
                String.valueOf(topColor),
                String.valueOf(bottomColor),
                String.valueOf(progress));
    }

    public static String create(String text, int color, double progress) {
        return create(text, color, color, progress);
    }

}
