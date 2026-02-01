package mcp.mobius.waila.addons.vanillamc;

import java.util.List;

import mcp.mobius.waila.api.impl.ModuleRegistrar;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.world.World;

import mcp.mobius.waila.addons.AddonBase;
import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.SpecialChars;
import mcp.mobius.waila.cbcore.LangUtil;

public class VanillaAddon extends AddonBase implements IWailaDataProvider {

    @Override
    public void init() {
        addConfigRemote(VANILLA_ADDON, "vanilla.spawntype");
        addConfigRemote(VANILLA_ADDON, "vanilla.leverstate");
        addConfigRemote(VANILLA_ADDON, "vanilla.repeater");
        addConfigRemote(VANILLA_ADDON, "vanilla.comparator");
        addConfigRemote(VANILLA_ADDON, "vanilla.redstone");
        addConfigRemote(VANILLA_ADDON, "vanilla.silverfish");
        addConfigRemote(VANILLA_ADDON, "vanilla.showents");
        addConfigRemote(VANILLA_ADDON, "vanilla.showhp");
        addConfigRemote(VANILLA_ADDON, "vanilla.showcrop");
        addConfigRemote(VANILLA_ADDON, "vanilla.jukebox");
        addConfigRemote(VANILLA_ADDON, "vanilla.show_invisible_players");

        registerStack(this, silverfish.getClass());
        registerStack(this, redstone.getClass());
        registerStack(this, doubleplant.getClass());
        registerStack(this, redstoneOre.getClass());
        registerStack(this, crops.getClass());
        registerStack(this, leave.getClass());
        registerStack(this, leave2.getClass());
        registerStack(this, log.getClass());
        registerStack(this, log2.getClass());
        registerStack(this, quartz.getClass());
        registerStack(this, anvil.getClass());
        registerStack(this, sapling.getClass());
        registerStack(this, stoneSlab.getClass());
        registerStack(this, woodSlab.getClass());

        registerHead(this, mobSpawner.getClass());
        registerHead(this, melonStem.getClass());
        registerHead(this, pumpkinStem.getClass());
        registerHead(this, redstone.getClass());

        registerBody(this, crops.getClass());
        registerBody(this, melonStem.getClass());
        registerBody(this, pumpkinStem.getClass());
        registerBody(this, lever.getClass());
        registerBody(this, repeaterIdle.getClass());
        registerBody(this, repeaterActv.getClass());
        registerBody(this, comparatorIdl.getClass());
        registerBody(this, comparatorAct.getClass());
        registerBody(this, redstone.getClass());
        registerBody(this, jukebox.getClass());
        registerBody(this, cocoa.getClass());
        registerBody(this, netherwart.getClass());

        registerNBT(this, mobSpawner.getClass());
        registerNBT(this, crops.getClass());
        registerNBT(this, melonStem.getClass());
        registerNBT(this, pumpkinStem.getClass());
        registerNBT(this, carrot.getClass());
        registerNBT(this, potato.getClass());
        registerNBT(this, lever.getClass());
        registerNBT(this, repeaterIdle.getClass());
        registerNBT(this, repeaterActv.getClass());
        registerNBT(this, comparatorIdl.getClass());
        registerNBT(this, comparatorAct.getClass());
        registerNBT(this, redstone.getClass());
        registerNBT(this, jukebox.getClass());
        registerNBT(this, cocoa.getClass());
        registerNBT(this, netherwart.getClass());
        registerNBT(this, silverfish.getClass());
    }

    static Block mobSpawner = Blocks.mob_spawner;
    static Block crops = Blocks.wheat;
    static Block melonStem = Blocks.melon_stem;
    static Block pumpkinStem = Blocks.pumpkin_stem;
    static Block carrot = Blocks.carrots;
    static Block potato = Blocks.potatoes;
    static Block lever = Blocks.lever;
    static Block repeaterIdle = Blocks.unpowered_repeater;
    static Block repeaterActv = Blocks.powered_repeater;
    static Block comparatorIdl = Blocks.unpowered_comparator;
    static Block comparatorAct = Blocks.powered_comparator;
    static Block redstone = Blocks.redstone_wire;
    static Block jukebox = Blocks.jukebox;
    static Block cocoa = Blocks.cocoa;
    static Block netherwart = Blocks.nether_wart;
    static Block silverfish = Blocks.monster_egg;
    static Block doubleplant = Blocks.double_plant;
    static Block leave = Blocks.leaves;
    static Block leave2 = Blocks.leaves2;
    static Block log = Blocks.log;
    static Block log2 = Blocks.log2;
    static Block quartz = Blocks.quartz_block;
    static Block anvil = Blocks.anvil;
    static Block sapling = Blocks.sapling;
    static Block redstoneOre = Blocks.redstone_ore;
    static Block stoneSlab = Blocks.stone_slab;
    static Block woodSlab = Blocks.wooden_slab;
    static String VANILLA_ADDON = "VanillaMC";

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        int meta = accessor.getMetadata();

        if (block == silverfish && config.getConfig("vanilla.silverfish")) {
            return switch (meta) {
                case 0 -> new ItemStack(Blocks.stone);
                case 1 -> new ItemStack(Blocks.cobblestone);
                case 2 -> new ItemStack(Blocks.brick_block);
                default -> null;
            };
        }

        if (block == redstone) {
            return new ItemStack(Items.redstone);
        }

        if (block == doubleplant && (meta & 8) != 0) {
            int x = accessor.getPosition().blockX;
            int y = accessor.getPosition().blockY - 1;
            int z = accessor.getPosition().blockZ;
            int newMeta = accessor.getWorld().getBlockMetadata(x, y, z);

            return new ItemStack(doubleplant, 0, newMeta);
        }

        if (block == redstoneOre) {
            return new ItemStack(Blocks.redstone_ore);
        }

        if (block == crops) {
            return new ItemStack(Items.wheat);
        }

        if ((block == leave || block == leave2) && (meta > 3)) {
            return new ItemStack(block, 1, meta - 4);
        }

        if (block == log || block == log2) {
            return new ItemStack(block, 1, meta % 4);
        }

        if ((block == quartz) && (meta > 2)) {
            return new ItemStack(block, 1, 2);
        }

        if (block == anvil) {
            return new ItemStack(block, 1, block.damageDropped(meta));
        }

        if (block == sapling) {
            return new ItemStack(block, 1, block.damageDropped(meta));
        }

        if (block == stoneSlab || block == woodSlab) {
            return new ItemStack(block, 1, block.damageDropped(meta));
        }

        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        TileEntity te = accessor.getTileEntity();

        if (config.getConfig("vanilla.spawntype")) {
            if (block == mobSpawner && te instanceof TileEntityMobSpawner teSpawner) {
                String name = currenttip.get(0);
                String mobName = teSpawner.func_145881_a().getEntityNameToSpawn();
                currenttip.set(0, String.format("%s (%s)", name, mobName));
            }
        }

        if (block == redstone) {
            String name = currenttip.get(0).replaceFirst(String.format(" %s", accessor.getMetadata()), "");
            currenttip.set(0, name);
        }

        if (block == melonStem) {
            currenttip.set(0, SpecialChars.WHITE + "Melon stem");
        }

        if (block == pumpkinStem) {
            currenttip.set(0, SpecialChars.WHITE + "Pumpkin stem");
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        Block block = accessor.getBlock();
        boolean isCrop = crops.getClass().isInstance(block);
        int meta = accessor.getMetadata();

        if (config.getConfig("vanilla.showcrop")) {
            if (isCrop || block == melonStem || block == pumpkinStem || block == carrot || block == potato) {
                float growthValue = (meta / 7.0F) * 100.0F;
                if (growthValue < 100.0)
                    currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
                else currenttip.add(
                        String.format(
                                "%s : %s",
                                LangUtil.translateG("hud.msg.growth"),
                                LangUtil.translateG("hud.msg.mature")));
                return currenttip;
            }

            if (block == cocoa) {
                float growthValue = ((accessor.getMetadata() >> 2) / 2.0F) * 100.0F;
                if (growthValue < 100.0)
                    currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
                else currenttip.add(
                        String.format(
                                "%s : %s",
                                LangUtil.translateG("hud.msg.growth"),
                                LangUtil.translateG("hud.msg.mature")));
                return currenttip;
            }

            if (block == netherwart) {
                float growthValue = (meta / 3.0F) * 100.0F;
                if (growthValue < 100.0)
                    currenttip.add(String.format("%s : %.0f %%", LangUtil.translateG("hud.msg.growth"), growthValue));
                else currenttip.add(
                        String.format(
                                "%s : %s",
                                LangUtil.translateG("hud.msg.growth"),
                                LangUtil.translateG("hud.msg.mature")));
                return currenttip;
            }
        }

        if (config.getConfig("vanilla.leverstate")) {
            if (block == lever) {
                String redstoneOn = (meta & 8) == 0 ? LangUtil.translateG("hud.msg.off")
                        : LangUtil.translateG("hud.msg.on");
                currenttip.add(String.format("%s : %s", LangUtil.translateG("hud.msg.state"), redstoneOn));
                return currenttip;
            }
        }

        if (config.getConfig("vanilla.repeater")) {
            if ((block == repeaterIdle) || (block == repeaterActv)) {
                int tick = (meta >> 2) + 1;
                if (tick == 1)
                    currenttip.add(String.format("%s : %s tick", LangUtil.translateG("hud.msg.delay"), tick));
                else currenttip.add(String.format("%s : %s ticks", LangUtil.translateG("hud.msg.delay"), tick));
                return currenttip;
            }
        }

        if (config.getConfig("vanilla.comparator")) {
            if ((block == comparatorIdl) || (block == comparatorAct)) {
                String mode = ((meta >> 2) & 1) == 0 ? LangUtil.translateG("hud.msg.comparator")
                        : LangUtil.translateG("hud.msg.substractor");
                currenttip.add("Mode : " + mode);
                return currenttip;
            }
        }

        if (config.getConfig("vanilla.redstone")) {
            if (block == redstone) {
                currenttip.add(String.format("%s : %s", LangUtil.translateG("hud.msg.power"), meta));
                return currenttip;
            }
        }

        return currenttip;
    }

    @Override
    public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, int x,
            int y, int z) {
        if (te != null) te.writeToNBT(tag);
        return tag;
    }
}
