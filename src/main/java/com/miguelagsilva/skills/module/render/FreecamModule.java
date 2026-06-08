package com.miguelagsilva.skills.module.render;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.PacketEvent;
import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import java.util.UUID;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.*;
import net.minecraft.util.math.Vec3d;

public class FreecamModule extends AbstractModule {
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private OtherClientPlayerEntity dummy;

    private static final int DUMMY_ENTITY_ID = -67;

    public FreecamModule() {
        super("Freecam", "Move your camera freely", ModuleCategory.RENDER);
    }

    @Override
    public void onEnable() {
        if (client.player == null) return;
        Vec3d pos = client.player.getEntityPos();
        x = pos.x;
        y = pos.y;
        z = pos.z;
        yaw = client.player.getYaw();
        pitch = client.player.getPitch();

        if (client.world == null) return;
        this.dummy = new OtherClientPlayerEntity(client.world, client.player.getGameProfile());
        this.dummy.copyPositionAndRotation(client.player);
        this.dummy.setHeadYaw(client.player.headYaw);
        this.dummy.setUuid(UUID.randomUUID());
        this.dummy.getInventory().clone(client.player.getInventory());
        this.dummy.setId(DUMMY_ENTITY_ID);
        this.dummy.noClip = true;

        client.world.addEntity(this.dummy);
    }

    @Override
    public void onDisable() {
        if (client.player == null) return;
        client.player.setPosition(x, y, z);
        client.player.setYaw(yaw);
        client.player.setPitch(pitch);

        if (client.world == null || client.player.getEntity() == null || dummy == null) return;
        client.world.removeEntity(this.dummy.getId(), Entity.RemovalReason.CHANGED_DIMENSION);
        dummy = null;
    }

    @Subscribe
    public void outPacket(PacketEvent.Send event) {
        if (event.getPacket() instanceof PlayerMoveC2SPacket
                || event.getPacket() instanceof PlayerActionC2SPacket
                || event.getPacket() instanceof HandSwingC2SPacket
                || event.getPacket() instanceof PlayerInteractEntityC2SPacket) {
            event.setCancelled(true);
        }
    }
}
