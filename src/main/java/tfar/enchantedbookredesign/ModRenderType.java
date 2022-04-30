package tfar.enchantedbookredesign;

import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;

import net.minecraft.client.renderer.RenderStateShard;
import net.minecraft.client.renderer.RenderType;

public class ModRenderType extends RenderType {

	public ModRenderType(String p_i225973_1_, Runnable runnable1, Runnable runnable2) {

		super(p_i225973_1_, DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, true, runnable1, runnable2);

	}

	public static RenderType TINTED_GLINT  = create("tinted_glint", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, true,
			CompositeState.builder()
			.setTextureState(new RenderStateShard.TextureStateShard(Hooks.TINTED_GLINT_RL, true, false))
			.setWriteMaskState(COLOR_WRITE)
			.setCullState(NO_CULL)
			.setDepthTestState(EQUAL_DEPTH_TEST)
			.setTransparencyState(GLINT_TRANSPARENCY)
			.setTexturingState(GLINT_TEXTURING)
			.createCompositeState(false));

	public static RenderType TINTED_GLINT_DIRECT = create("tinted_glint_direct", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, true,
			CompositeState.builder()
			.setTextureState(new RenderStateShard.TextureStateShard(Hooks.TINTED_GLINT_RL, true, false))
			.setWriteMaskState(COLOR_WRITE)
			.setCullState(NO_CULL)
			.setDepthTestState(EQUAL_DEPTH_TEST)
			.setTransparencyState(GLINT_TRANSPARENCY)
			.setTexturingState(GLINT_TEXTURING)
			.createCompositeState(false));

	public static final RenderType TINTED_ENTITY_GLINT_DIRECT = create("tinted_entity_glint_direct", DefaultVertexFormat.POSITION_COLOR_TEX, VertexFormat.Mode.QUADS, 256, false, true,
			CompositeState.builder()
			.setTextureState(new RenderStateShard.TextureStateShard(Hooks.TINTED_GLINT_RL, true, false))
			.setWriteMaskState(COLOR_WRITE)
			.setCullState(NO_CULL)
			.setDepthTestState(EQUAL_DEPTH_TEST)
			.setTransparencyState(GLINT_TRANSPARENCY)
			.setTexturingState(ENTITY_GLINT_TEXTURING)
			.createCompositeState(false));


}
