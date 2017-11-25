package phlaxyr.forcearrows.codepatcher;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLCallHook;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

// @TransformerExclusions({"phlaxyr.forcearrows.codepatcher.PatchPlugin"})
public class PatchPlugin implements IFMLLoadingPlugin, IFMLCallHook{

	
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
		return "phlaxyr."+MODID+".codepatcher.PatchPlugin";
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
