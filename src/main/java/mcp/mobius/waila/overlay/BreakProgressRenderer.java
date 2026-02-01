package mcp.mobius.waila.overlay;

import org.lwjgl.opengl.GL11;

public class BreakProgressRenderer {

    public static void renderBreakProgress(int x, int y, int width, int height, float progress) {
        if (progress <= 0.0f || progress >= 1.0f) {
            return;
        }

        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        int padding = 2;
        int innerX = x + padding;
        int innerWidth = width - 2 * padding;

        if (progress > 0) {
            int progressWidth = (int) (innerWidth * progress);

            if (progressWidth > 0) {
                GL11.glBegin(GL11.GL_QUADS);

                GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.9f);
                GL11.glVertex2i(innerX, y);
                GL11.glColor4f(0.0f, 1.0f, 0.0f, 0.9f);
                GL11.glVertex2i(innerX, y + height);

                float green = 1.0f - progress;
                GL11.glColor4f(progress, green, 0.0f, 0.9f);
                GL11.glVertex2i(innerX + progressWidth, y + height);
                GL11.glColor4f(progress, green, 0.0f, 0.9f);
                GL11.glVertex2i(innerX + progressWidth, y);

                GL11.glEnd();
            }
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }
}
