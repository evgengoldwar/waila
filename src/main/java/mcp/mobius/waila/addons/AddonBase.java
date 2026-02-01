package mcp.mobius.waila.addons;

import mcp.mobius.waila.api.IWailaBlockDecorator;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaEntityProvider;
import mcp.mobius.waila.api.IWailaFMPDecorator;
import mcp.mobius.waila.api.IWailaFMPProvider;
import mcp.mobius.waila.api.IWailaTooltipRenderer;
import mcp.mobius.waila.api.impl.ModuleRegistrar;

public abstract class AddonBase {

    abstract public void init();

    // region Config
    protected void addConfig(String modName, String key) {
        ModuleRegistrar.instance().addConfig(modName, key);
    }

    protected void addConfig(String modName, String key, boolean defValue) {
        ModuleRegistrar.instance().addConfig(modName, key, defValue);
    }

    protected void addConfig(String modName, String key, String configName, boolean defValue) {
        ModuleRegistrar.instance().addConfig(modName, key, configName, defValue);
    }

    protected void addConfigRemote(String modName, String key) {
        ModuleRegistrar.instance().addConfigRemote(modName, key);
    }

    protected void addConfigRemote(String modName, String key, boolean defValue) {
        ModuleRegistrar.instance().addConfigRemote(modName, key, defValue);
    }

    protected void addConfigRemote(String modName, String key, String configName, boolean defValue) {
        ModuleRegistrar.instance().addConfigRemote(modName, key, configName, defValue);
    }

    // endregion

    // region Head
    protected void registerHead(IWailaDataProvider provider, Class<?> block) {
        ModuleRegistrar.instance().registerHeadProvider(provider, block);
    }

    protected void registerHead(IWailaEntityProvider provider, Class<?> block) {
        ModuleRegistrar.instance().registerHeadProvider(provider, block);
    }

    protected void registerHead(IWailaFMPProvider provider, String name) {
        ModuleRegistrar.instance().registerHeadProvider(provider, name);
    }
    // endregion

    // region Body
    protected void registerBody(IWailaDataProvider provider, Class<?> block) {
        ModuleRegistrar.instance().registerBodyProvider(provider, block);
    }

    protected void registerBody(IWailaEntityProvider provider, Class<?> block) {
        ModuleRegistrar.instance().registerBodyProvider(provider, block);
    }

    protected void registerBody(IWailaFMPProvider provider, String name) {
        ModuleRegistrar.instance().registerBodyProvider(provider, name);
    }
    // endregion

    // region Tail
    protected void registerTail(IWailaDataProvider provider, Class<?> block) {
        ModuleRegistrar.instance().registerTailProvider(provider, block);
    }

    protected void registerTail(IWailaEntityProvider provider, Class<?> block) {
        ModuleRegistrar.instance().registerTailProvider(provider, block);
    }

    protected void registerTail(IWailaFMPProvider provider, String name) {
        ModuleRegistrar.instance().registerTailProvider(provider, name);
    }
    // endregion

    // region Stack
    protected void registerStack(IWailaDataProvider provider, Class<?> block) {
        ModuleRegistrar.instance().registerStackProvider(provider, block);
    }
    // endregion

    // region NBT
    protected void registerNBT(IWailaDataProvider provider, Class<?> block) {
        ModuleRegistrar.instance().registerNBTProvider(provider, block);
    }

    protected void registerNBT(IWailaEntityProvider provider, Class<?> block) {
        ModuleRegistrar.instance().registerNBTProvider(provider, block);
    }
    // endregion

    // region Decorator
    protected void registerDecorator(IWailaBlockDecorator provider, Class<?> block) {
        ModuleRegistrar.instance().registerDecorator(provider, block);
    }

    protected void registerDecorator(IWailaFMPDecorator provider, String name) {
        ModuleRegistrar.instance().registerDecorator(provider, name);
    }
    // endregion

    // region Tooltip Renderer
    protected void registerTooltipRenderer(IWailaTooltipRenderer provider, String name) {
        ModuleRegistrar.instance().registerTooltipRenderer(name, provider);
    }
    // endregion
}
