package com.miguelagsilva.skills.module.render;

import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldExtractionContext;
import net.fabricmc.fabric.api.client.rendering.v1.world.WorldRenderEvents;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector4f;

public class EntityESP2DModule extends AbstractModule {

    private volatile Matrix4f cachedViewProj = null;
    private volatile Vec3d cachedCamPos = null;

    public EntityESP2DModule() {
        super("EntityESP2D", "Shows entities through walls, drawn on 2D", ModuleCategory.RENDER);
        WorldRenderEvents.END_EXTRACTION.register(this::onExtract);
        HudRenderCallback.EVENT.register(this::onHudRender);
    }

    private void onExtract(WorldExtractionContext context) {
        if (!isEnabled()) return;
        cachedViewProj = new Matrix4f(context.cullProjectionMatrix()).mul(context.viewMatrix());
        cachedCamPos = context.camera().getCameraPos();
    }

    private void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
        if (!isEnabled() || cachedViewProj == null) return;
        if (client.player == null || client.world == null) return;

        float tickDelta = tickCounter.getTickProgress(false);
        int screenW = client.getWindow().getScaledWidth();
        int screenH = client.getWindow().getScaledHeight();

        for (Entity entity : client.world.getEntities()) {
            if (entity == client.player || !entity.isAlive()) continue;

            Vec3d pos = entity.getLerpedPos(tickDelta).subtract(cachedCamPos);
            float height = entity.getHeight();

            float[] feet =
                    worldToScreen(
                            cachedViewProj, (float) pos.x, (float) pos.y, (float) pos.z, screenW,
                            screenH);
            float[] head =
                    worldToScreen(
                            cachedViewProj,
                            (float) pos.x,
                            (float) (pos.y + height),
                            (float) pos.z,
                            screenW,
                            screenH);

            if (feet == null || head == null) continue;

            float boxH = Math.abs(feet[1] - head[1]);
            if (boxH < 2f) continue;
            float boxW = boxH * 0.5f;
            int bx = (int) (feet[0] - boxW / 2f);
            int by = (int) head[1];
            int bw = (int) boxW;
            int bh = (int) boxH;
            int color = 0xFF00FF00;

            context.drawHorizontalLine(bx, bx + bw, by, color);
            context.drawHorizontalLine(bx, bx + bw, by + bh, color);
            context.drawVerticalLine(bx, by, by + bh, color);
            context.drawVerticalLine(bx + bw, by, by + bh, color);
        }
    }

    private float[] worldToScreen(
            Matrix4f viewProj, float x, float y, float z, int screenW, int screenH) {
        Vector4f pos = new Vector4f(x, y, z, 1.0f);
        viewProj.transform(pos);
        if (pos.w <= 0.001f) return null;
        float ndcX = pos.x / pos.w;
        float ndcY = pos.y / pos.w;
        float screenX = (ndcX + 1.0f) / 2.0f * screenW;
        float screenY = (1.0f - ndcY) / 2.0f * screenH;
        return new float[] {screenX, screenY};
    }
}
