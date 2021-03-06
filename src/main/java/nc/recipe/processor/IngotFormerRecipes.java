package nc.recipe.processor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import nc.config.NCConfig;
import nc.recipe.BaseRecipeHandler;
import nc.util.FluidHelper;
import nc.util.StringHelper;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

public class IngotFormerRecipes extends BaseRecipeHandler {
	
	public IngotFormerRecipes() {
		super("ingot_former", 0, 1, 1, 0, false);
	}

	@Override
	public void addRecipes() {
		addIngotFormingRecipes();
		
		addIsotopeFormingRecipes("Thorium", 230, 232);
		addIsotopeFormingRecipes("Uranium", 233, 235, 238);
		addIsotopeFormingRecipes("Neptunium", 236, 237);
		addIsotopeFormingRecipes("Plutonium", 238, 239, 241, 242);
		addIsotopeFormingRecipes("Americium", 241, 242, 243);
		addIsotopeFormingRecipes("Curium", 243, 245, 246, 247);
		addIsotopeFormingRecipes("Berkelium", 247, 248);
		addIsotopeFormingRecipes("Californium", 249, 250, 251, 252);
		
		addRecipe(fluidStack("thorium_tbu", FluidHelper.INGOT_BLOCK_VOLUME), "fuelTBU", NCConfig.processor_time[10]*9);
		
		addFissionFuelFormingRecipes("uranium", "eu", 233, 235);
		addFissionFuelFormingRecipes("neptunium", "en", 236);
		addFissionFuelFormingRecipes("plutonium", "ep", 239, 241);
		addFissionFuelFormingRecipes("americium", "ea", 242);
		addFissionFuelFormingRecipes("curium", "ec", "m", 243, 245, 247);
		addFissionFuelFormingRecipes("berkelium", "eb", 248);
		addFissionFuelFormingRecipes("californium", "ec", "f", 249, 251);
		
		// Tinkers' Construct		
		addIngotFormingRecipe("Manyullyn");
		addIngotFormingRecipe("Alubrass");
		addIngotFormingRecipe("Pigiron");
		addIngotFormingRecipe("Brass");
		addIngotFormingRecipe("Bronze");
		addIngotFormingRecipe("Electrum");
		addIngotFormingRecipe("Steel");
	}
	
	public void addIngotFormingRecipe(String fluid, String metal) {
		addRecipe(fluidStack(fluid.toLowerCase(), FluidHelper.INGOT_VOLUME), "ingot" + metal, NCConfig.processor_time[10]);
	}
	
	public void addIngotFormingRecipe(String metal) {
		addIngotFormingRecipe(metal, metal);
	}
	
	public void addIsotopeFormingRecipes(String element, int... types) {
		for (int type : types) addIngotFormingRecipe(element.toLowerCase() + "_" + type, element + type + "Base");
	}
	
	public void addFissionFuelFormingRecipes(String element, String suffix, String suffixExtra, int... types) {
		for (int type : types) {
			addRecipe(fluidStack("fuel_l" + suffix + suffixExtra + "_" + type, FluidHelper.INGOT_BLOCK_VOLUME), "fuelL" + suffix.toUpperCase() + suffixExtra + type, NCConfig.processor_time[10]*9);
			addRecipe(fluidStack("fuel_h" + suffix + suffixExtra + "_" + type, FluidHelper.INGOT_BLOCK_VOLUME), "fuelH" + suffix.toUpperCase() + suffixExtra + type, NCConfig.processor_time[10]*9);
			addRecipe(fluidStack("depleted_fuel_l" + suffix + suffixExtra + "_" + type, FluidHelper.NUGGET_VOLUME*64), "depletedFuelL" + suffix.toUpperCase() + suffixExtra + type, NCConfig.processor_time[10]*64/9);
			addRecipe(fluidStack("depleted_fuel_h" + suffix + suffixExtra + "_" + type, FluidHelper.NUGGET_VOLUME*64), "depletedFuelH" + suffix.toUpperCase() + suffixExtra + type, NCConfig.processor_time[10]*64/9);
		}
	}
	
	public void addFissionFuelFormingRecipes(String element, String suffix, int... types) {
		addFissionFuelFormingRecipes(element, suffix, "", types);
	}
	
	public void addIngotFormingRecipes() {
		List<String> oreList = Arrays.asList(OreDictionary.getOreNames());
		ArrayList<String> fluidList = new ArrayList(FluidRegistry.getRegisteredFluids().keySet());
		for (String fluidName : fluidList) {
			String ingot = "ingot" + StringHelper.capitalize(fluidName);
			if (oreList.contains(ingot)) addRecipe(fluidStack(fluidName, FluidHelper.INGOT_VOLUME), ingot, NCConfig.processor_time[10]);
		}
	}
}
