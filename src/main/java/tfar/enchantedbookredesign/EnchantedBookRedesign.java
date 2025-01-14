package tfar.enchantedbookredesign;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.tuple.Pair;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.network.NetworkConstants;
import net.minecraftforge.registries.ForgeRegistries;

@Mod(EnchantedBookRedesign.MODID)
public class EnchantedBookRedesign {

	public static final String MODID = "enchantedbookredesign";

	public EnchantedBookRedesign() {
		ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, CLIENT_SPEC);
		ModLoadingContext.get().registerExtensionPoint(IExtensionPoint.DisplayTest.class, () -> {Pair.of(() -> NetworkConstants.IGNORESERVERONLY, (a, b) -> true)});
		if (FMLEnvironment.dist.isClient()) {
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setupClient);
			FMLJavaModLoadingContext.get().getModEventBus().addListener(this::configLoad);
		}
	}

	private void setupClient(final FMLClientSetupEvent event) {
		
		ItemProperties.register(Items.ENCHANTED_BOOK, new ResourceLocation(MODID, "level"), 
					(stack, world, entity, unknown_int) -> {
						
							Map<Enchantment, Integer> enchs = EnchantmentHelper.getEnchantments(stack);
							
							if (enchs.isEmpty())
								return 1;

							int level = 1;
							
							for (Map.Entry<Enchantment, Integer> entry : enchs.entrySet()) {
								
								if (entry.getKey().isCurse())
									return 0;

								level = Math.max(level, entry.getValue());
								
							}
							
							return level;
							
						});

		Minecraft mc = Minecraft.getInstance();
		ItemColors itemColors = mc.getItemColors();
		itemColors.register((stack, tintIndex) -> tintIndex != 1 ? -1 : Hooks.getColor(stack), Items.ENCHANTED_BOOK);
		
	}

	public static final ClientConfig CLIENT;
	public static final ForgeConfigSpec CLIENT_SPEC;

	static {
		
		final Pair<ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(ClientConfig::new);
		CLIENT_SPEC = specPair.getRight();
		CLIENT = specPair.getLeft();
		
	}

	public static Set<Item> cache = new HashSet<>();

	public static class ClientConfig {

		public static ForgeConfigSpec.ConfigValue<List<? extends String>> items;

		public ClientConfig(ForgeConfigSpec.Builder builder) {
			
			builder.push("client");
			items = builder
					.comment("Whether to display the preview of the item in the dank, disable if you have optifine")
					.defineList("items", Lists.newArrayList(Items.ENCHANTED_BOOK.getRegistryName().toString()),String.class::isInstance);
			builder.pop();
			
		}
		
	}


	private void configLoad(ModConfigEvent e) {
		
		if (e.getConfig().getModId().equals(MODID)) {
			
			cache.clear();
			for (String s : ClientConfig.items.get()) {
				
				Item item = Registry.ITEM.getOrDefault(new ResourceLocation(s));
				if (item == Items.AIR) {
					
					System.out.println(s+" not found");
					
				} else {
					
					cache.add(item);
					
				}
				
			}
			
		}
		
	}
	
}
