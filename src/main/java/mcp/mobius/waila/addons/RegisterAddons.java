package mcp.mobius.waila.addons;

import java.util.ArrayList;
import java.util.List;

import mcp.mobius.waila.addons.vanillamc.VanillaAddon;

public class RegisterAddons {

    private static final List<AddonBase> REGISTERED_ADDONS = new ArrayList<>();

    private static void registerAddon(AddonBase addon) {
        REGISTERED_ADDONS.add(addon);
    }

    public static void registerAllAddons() {
        registerAddon(new VanillaAddon());
    }

    public static void initAllAddons() {
        for (AddonBase addon : REGISTERED_ADDONS) {
            addon.init();
        }
    }
}
