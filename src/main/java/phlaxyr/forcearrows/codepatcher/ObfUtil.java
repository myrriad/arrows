package phlaxyr.forcearrows.codepatcher;

public class ObfUtil {
	public static String obf(String str) {
		String stri = str.replace('/', '.');
		switch(stri) {
		case "net.minecraft.inventory.ContainerWorkbench":
			return "afx";
				
		case "net.minecraft.block.BlockWorkbench":
			return "app";
		case "net.minecraft.block.BlockWorkbench.InterfaceCraftingTable":
			return "app$a";
		case "net.minecraft.client.gui.inventory.GuiCrafting":
			return "bml";
		case "net.minecraft.inventory.Container":
			return "afp";
		case "net.minecraft.world.World":
			return "ams";
		case "net.minecraft.entity.player.EntityPlayer":
			return "aeb";
		case "net.minecraft.inventory.InventoryCrafting":
			return "afw";
		case "net.minecraft.inventory.InventoryCraftResult":
			return "agl";
		
		case "net.minecraft.item.crafting.IRecipe":
			return "akr";
		case "net.minecraft.item.ItemStack":
			return "ain";
		case "net.minecraft.inventory.Slot":
			return "agp";
		case "net.minecraft.util.math.BlockPos":
			return "et";
		case "net.minecraft.item.crafting.CraftingManager":
			return "aks";
		}
		return str;
	}
}
