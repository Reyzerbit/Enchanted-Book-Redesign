package tfar.enchantedbookredesign.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms.TransformType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.world.item.ItemStack;
import tfar.enchantedbookredesign.EnchantedBookRedesign;
import tfar.enchantedbookredesign.Hooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import tfar.enchantedbookredesign.ModRenderType;
import tfar.enchantedbookredesign.TintedVertexConsumer;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
	
	// IMPORTANT: Inject methods must be updated and verified with EVERY new version release!

	// Capture the ItemStack
	@Inject(method = "render("
			+ "Lnet/minecraft/world/item/ItemStack;"
			+ "Lnet/minecraft/client/renderer/block/model/ItemTransforms$TransformType;"
			+ "Z" // Boolean
			+ "Lcom/mojang/blaze3d/vertex/PoseStack;"
			+ "Lnet/minecraft/client/renderer/IRenderTypeBuffer;"
			+ "I" // Integer
			+ "I" // Integer
			+ "Lnet/minecraft/client/resources/model/BakedModel;"
			+ ")V", // Returns void
			at = @At("HEAD"))
	private void capturestack(ItemStack itemStackIn, TransformType transformTypeIn, boolean leftHand, PoseStack matrixStackIn, MultiBufferSource bufferIn, int combinedLightIn, int combinedOverlayIn, BakedModel modelIn) {
		
		Hooks.stack = itemStackIn;
		
	}

	// Stop the vanilla glint from drawing at all if our conditions are met
	@Inject(method = "getEntityGlintVertexBuilder", at = @At("HEAD"), cancellable = true)
	private static void tintedglint(IRenderTypeBuffer bufferIn, RenderType renderTypeIn, boolean isItem, boolean glint, CallbackInfoReturnable<IVertexBuilder> cir) {
			if (glint && EnchantedBookRedesign.cache.contains(Hooks.stack.getItem())) {
				VertexConsumer builder2 = VertexBuilderUtils.newDelegate(
								TintedVertexConsumer.withTint(
												bufferIn.getBuffer(isItem ? ModRenderType.TINTED_GLINT_DIRECT : ModRenderType.TINTED_ENTITY_GLINT_DIRECT)
												, Hooks.getColor(Hooks.stack)),
								bufferIn.getBuffer(renderTypeIn));
				cir.setReturnValue(builder2);
			}
	}
}