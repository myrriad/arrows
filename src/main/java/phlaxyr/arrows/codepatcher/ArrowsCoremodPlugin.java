package phlaxyr.arrows.codepatcher;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

// @TransformerExclusions({"phlaxyr.arrows.codepatcher.PatchPlugin"})
@TransformerExclusions(
		{
			/*"phlaxyr.arrows.codepatcher.ArrowsCoremodPlugin",
			"phlaxyr.arrows.codepatcher.PatchTransformer",
			"phlaxyr.arrows.codepatcher.ClassFixer",
			"phlaxyr.arrows.codepatcher.MethodFixer",
			"phlaxyr.arrows.codepatcher.ObfUtil",*/
			"phlaxyr.arrows.codepatcher"
		}
	)
@MCVersion("1.12.2")
public class ArrowsCoremodPlugin implements IFMLLoadingPlugin, IFMLCallHook{

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
		return "phlaxyr.arrows.codepatcher.ArrowsCoremodPlugin";
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
