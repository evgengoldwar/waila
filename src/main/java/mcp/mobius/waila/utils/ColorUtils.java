package mcp.mobius.waila.utils;

import com.gtnewhorizon.gtnhlib.color.ColorResource;

public class ColorUtils {

    private static final ColorResource.Factory color = new ColorResource.Factory("Waila");

    public static final ColorResource fuelFurnaceBarTop = color.rgb("fuelFurnaceBarTop", "0xFFFF8800");
    public static final ColorResource fuelFurnaceBarBottom = color.rgb("fuelFurnaceBarBottom", "0xFFFFCC00");

    public static final ColorResource progressBarTop = color.rgb("progressBarTop", "0xFFFF0000");
    public static final ColorResource progressBarBottom = color.rgb("progressBarBottom", "0xFF8B0000");
}
