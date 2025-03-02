package tfar.enchantedbookredesign.mixin;

import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeBuffers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import tfar.enchantedbookredesign.ModRenderType;

@Mixin(RenderTypeBuffers.class)
public abstract class RenderTypeBuffersMixin {

	@Shadow private static void put(Object2ObjectLinkedOpenHashMap<RenderType, BufferBuilder> mapBuildersIn, RenderType renderTypeIn) {
		throw new IllegalStateException("mixin broke");
	}

	@Inject(method = "*(Lit/unimi/dsi/fastutil/objects/Object2ObjectLinkedOpenHashMap;)V",
					at = @At(value = "INVOKE",target = "net/minecraft/client/renderer/RenderType.getEntityGlint ()Lnet/minecraft/client/renderer/RenderType;"))
	private void rendertype(Object2ObjectLinkedOpenHashMap map, CallbackInfo ci) {
		put(map, ModRenderType.TINTED_GLINT_DIRECT);
		put(map, ModRenderType.TINTED_ENTITY_GLINT_DIRECT);
	}
}