package mcp.mobius.waila.addons.vanillamc;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.world.World;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.impl.ModuleRegistrar;
import mcp.mobius.waila.overlay.tooltiprenderers.TTRenderBar;
import mcp.mobius.waila.overlay.tooltiprenderers.TTRenderProgressBar;
import mcp.mobius.waila.overlay.tooltiprenderers.TTRenderStack;

public class HUDHandlerFurnace implements IWailaDataProvider {

    static Block furanace = Blocks.furnace;

    @Override
    public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
        return null;
    }

    @Override
    public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        return currenttip;
    }

    @Override
    public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor,
            IWailaConfigHandler config) {
        NBTTagCompound tag = accessor.getNBTData();
        NBTTagList items = accessor.getNBTData().getTagList("Items", 10);
        String itemsText = "";
        ItemStack inputStack = null;
        ItemStack fuelStack = null;
        ItemStack outputStack = null;

        int smeltTime = tag.getInteger("Smelt");
        int fuelTime = tag.getInteger("Fuel");
        int maxFuelTime = tag.getInteger("MaxFuel");

        double smeltProgress = (double) smeltTime / 200;
        double fuelProgress = (double) fuelTime / maxFuelTime;

        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound itemTag = items.getCompoundTagAt(i);
            byte slot = itemTag.getByte("Slot");
            ItemStack stack = ItemStack.loadItemStackFromNBT(itemTag);

            if (slot == 0) {
                inputStack = stack;
            } else if (slot == 1) {
                fuelStack = stack;
            } else if (slot == 2) {
                outputStack = stack;
            }
        }

        if (fuelProgress > 0) {
            String fuelBar = TTRenderBar.create(
                    String.format("Fuel %ds / %ds", (maxFuelTime - fuelTime) / 20, maxFuelTime / 20),
                    0xFFFF0000,
                    0xFFCC0000,
                    1.0 - fuelProgress);
            currenttip.add(fuelBar);
        }

        if (smeltProgress > 0) {
            String cookBar = TTRenderBar.create(
                    String.format("Smelt %ds / %ds", smeltTime / 20, 200 / 20),
                    0xFFFF8800,
                    0xFFFFCC00,
                    smeltProgress);
            currenttip.add(cookBar);
        }

        if (inputStack != null) {
            itemsText += TTRenderStack.create(inputStack, inputStack.stackSize);
        }

        if (fuelStack != null) {
            itemsText += TTRenderStack.create(fuelStack, fuelStack.stackSize);
        }

        if (outputStack != null) {
            if (inputStack != null || fuelStack != null) {
                itemsText += TTRenderProgressBar.create(0, 1);
            }
            itemsText += TTRenderStack.create(outputStack, outputStack.stackSize);
        }

        currenttip.add(itemsText);

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
        if (te instanceof TileEntityFurnace furnace) {
            tag.setInteger("Smelt", furnace.furnaceCookTime);
            tag.setInteger("Fuel", furnace.furnaceBurnTime);
            tag.setInteger("MaxFuel", furnace.currentItemBurnTime);
            te.writeToNBT(tag);
        }
        return tag;
    }

    public static void register() {
        IWailaDataProvider provider = new HUDHandlerFurnace();

        ModuleRegistrar.instance().registerBodyProvider(provider, furanace.getClass());
        ModuleRegistrar.instance().registerNBTProvider(provider, furanace.getClass());
    }
}
