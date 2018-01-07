package phlaxyr.forcearrows.codepatcher;

import java.util.Map;



import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;

// @TransformerExclusions({"phlaxyr.forcearrows.codepatcher.PatchPlugin"})
@TransformerExclusions(
		{
			/*"phlaxyr.forcearrows.codepatcher.ForceArrowsCoremodPlugin",
			"phlaxyr.forcearrows.codepatcher.PatchTransformer",
			"phlaxyr.forcearrows.codepatcher.ClassFixer",
			"phlaxyr.forcearrows.codepatcher.MethodFixer",
			"phlaxyr.forcearrows.codepatcher.ObfUtil",*/
			"phlaxyr.forcearrows.codepatcher"
		}
	)
@MCVersion("1.12.2")
public class ForceArrowsCoremodPlugin implements IFMLLoadingPlugin, IFMLCallHook{

	
	public static String MODID = "forcearrows";
	@Override
	public String[] getASMTransformerClass()
	{
		System.out.println("CODE PATCHER ACTIVATED!: GOTTEN TRANSFORMER");
		return new String[] {
			PatchTransformer.class.getName()	
		};
	}

	@Override
	public String getModContainerClass()
	{
		return null;
	}

	@Override
	public String getSetupClass()
	{
		System.out.println("CODE PATCHER ACTIVATED!: GOTTEN THIS");
		return "phlaxyr."+MODID+".codepatcher.ForceArrowsCoremodPlugin";
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}

	@Override
	public Void call() throws Exception
	{
		return null;
	}

}
