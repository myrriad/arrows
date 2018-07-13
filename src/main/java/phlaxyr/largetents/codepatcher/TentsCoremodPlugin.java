package phlaxyr.largetents.codepatcher;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

// @TransformerExclusions({"phlaxyr.largetents.codepatcher.PatchPlugin"})
@TransformerExclusions(
		{
			/*"phlaxyr.largetents.codepatcher.largetentsCoremodPlugin",
			"phlaxyr.largetents.codepatcher.PatchTransformer",
			"phlaxyr.largetents.codepatcher.ClassFixer",
			"phlaxyr.largetents.codepatcher.MethodFixer",
			"phlaxyr.largetents.codepatcher.ObfUtil",*/
			"phlaxyr.largetents.codepatcher"
		}
	)
@MCVersion("1.12.2")
public class TentsCoremodPlugin implements IFMLLoadingPlugin, IFMLCallHook{

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
		return "phlaxyr.largetents.codepatcher.largetentsCoremodPlugin";
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
