package phlaxyr.arrows.codepatcher;

import static phlaxyr.arrows.codepatcher.ObfUtil.obf;

// import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.launchwrapper.IClassTransformer;

public class PatchTransformer implements IClassTransformer {
	// public static boolean obfuscated;
	public static final boolean DO_CRAFTING_ARROW = false;
	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass)
	{
		// String target = "net.minecraft.client.gui.inventory.GuiCrafting";
		// String container = "net.minecraft.inventory.Container";
//		if(name.equals(container)) {
//			obfuscated = false;
//			return patchContainer(name, basicClass, false);
//		}
//		if(name.equals(obfuscate(container))) {
//			obfuscated = true;
//			return patchContainer(name, basicClass, true);
//		}
		byte[] bytes = basicClass;
		if(DO_CRAFTING_ARROW) {
			ClassFixer container = new ClassFixer("net.minecraft.inventory.Container",
					new Container_slotChangedCraftingGrid());
			ClassFixer containerworkbench = new ClassFixer("net.minecraft.inventory.ContainerWorkbench", 
					new ContainerWorkbench_transferStackInSlot());
	
			bytes = containerworkbench.fixClass(bytes, name);
			bytes = container.fixClass(bytes, name);
		}

				

		return bytes;
	}
	static class ClassNameUtil {
		private boolean obfuscate;
		public ClassNameUtil(boolean obfuscate) {
			this.obfuscate = obfuscate;
		}
		
		public String get(String str) {
			return PatchTransformer.get(str, obfuscate);
		}
	}
	public static String get(String str, boolean obf) {
		if(!obf) return str;
			return obf(str);
	}
	
	public static String obfdesc(String desc, boolean obfuscated) {
		if(!(desc.charAt(0) == '(')) return desc;
		int i = 1;
		String retn = "(";
		while(i < desc.length()) {
			char c = desc.charAt(i);
			if(c == 'L') {
				retn = retn + 'L';
				String methodname = "";
				i++; // i is the first index of methodname
				c = desc.charAt(i); // c is the first character of methodname
				while(c != ';' && i < desc.length() - 1) { // check before appending
					methodname = methodname + c; // append c to methodname
					i++;
					c = desc.charAt(i);
					// System.out.println(i);
				}
				// c = ';'
				// i at the ';'
				retn = retn + get(methodname, obfuscated) + ";";
				i++;
				continue;
			}
			
			retn = retn + c;
			i++;

			continue;

		}
		return retn;
		// "(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)V"
		//  (Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)
	}
	
	public static
	void main(String[] args) {
		System.out.println(get("net/minecraft/world/World", true));
		System.out.println(obfdesc("(Lnet/minecraft/world/World;Lnet/minecraft/entity/player/EntityPlayer;Lnet/minecraft/inventory/InventoryCrafting;Lnet/minecraft/inventory/InventoryCraftResult;)V", true));
	}
	// mv.visitVarInsn(ALOAD, 4);
	// mv.visitMethodInsn(INVOKESTATIC, "phlaxyr/arrows/craftingarrow/ArrowManager", "onCraftArrow", "(Lnet/minecraft/inventory/Slot;)Z", false);

}
