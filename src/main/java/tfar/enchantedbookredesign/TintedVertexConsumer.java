package tfar.enchantedbookredesign;

import com.mojang.blaze3d.vertex.VertexConsumer;

public class TintedVertexConsumer implements VertexConsumer {

	public static VertexConsumer withTint(VertexConsumer tint, int color) {
		
		return new TintedVertexConsumer(tint, color);
		
	}

	private final VertexConsumer vertexConsumer;
	private final int color;

	public TintedVertexConsumer(VertexConsumer vertexConsumer, int color) {

		this.vertexConsumer = vertexConsumer;
		this.color = color;
		
	}

	/*
	public void addVertex(float x, float y, float z, float red, float green, float blue, float alpha, float u, float v, int overlay, int light, float normalX, float normalY, float normalZ) {

		float r = (color >> 16 & 0xff) / 255f;
		float g = (color >> 8 & 0xff) / 255f;
		float b = (color & 0xff) / 255f;

		this.vertexConsumer. .addVertex(x, y, z, r, g, b, alpha, u, v, overlay, light, normalX, normalY, normalZ);
		
	}
	*/

	public void endVertex() {
		this.vertexConsumer.endVertex();
	}

	@Override
	public VertexConsumer vertex(double x, double y, double z) {
		this.vertexConsumer.vertex(x, y, z);
		return this;
	}

	@Override
	public VertexConsumer color(int red, int green, int blue, int alpha) {
		int r = color >> 16 & 0xff;
		int g = color >> 8 & 0xff;
		int b = color & 0xff;
		this.vertexConsumer.color(r, g, b, alpha);
		return this;
	}

	@Override
	public VertexConsumer uv(float u, float v) {
		this.vertexConsumer.uv(u, v);
		return this;
	}

	@Override
	public VertexConsumer overlayCoords(int u, int v) {
		this.vertexConsumer.overlayCoords(u, v);
		return this;
	}

	@Override
	public VertexConsumer uv2(int u, int v) {
		this.vertexConsumer.uv2(u, v);
		return this;
	}

	@Override
	public VertexConsumer normal(float x, float y, float z) {
		this.vertexConsumer.normal(x, y, z);
		return this;
	}
	
	// Default color and unsetDefaultColor are new, may need to check their purpose if bugs arise

	@Override
	public void defaultColor(int r, int g, int b, int alpha) {}

	@Override
	public void unsetDefaultColor() {}
	
}
