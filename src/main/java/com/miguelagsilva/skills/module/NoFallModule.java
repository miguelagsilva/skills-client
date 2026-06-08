package com.miguelagsilva.skills.module;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.accessor.PlayerMoveC2SAccessor;
import com.miguelagsilva.skills.event.PacketEvent;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;

public class NoFallModule extends AbstractModule {
    protected NoFallModule() {
        super("NoFall", "No more fall damage", ModuleCategory.MOVEMENT);
    }

    @Subscribe
    public void outPacket(PacketEvent.Send event) {
        if (client.player == null) return;
        if (event.getPacket() instanceof PlayerMoveC2SPacket playerMoveC2SPacket) {
            if (client.player.fallDistance > 3.0f && client.player.getVelocity().y < -0.5) {
                Vec3d velocity = client.player.getVelocity();

                if (velocity.y >= 0) return;

                Box currentBox = client.player.getBoundingBox();
                double futureY = velocity.y;

                Box predictionColumn =
                        new Box(
                                currentBox.minX,
                                currentBox.minY + futureY, // The bottom of our prediction
                                currentBox.minZ,
                                currentBox.maxX,
                                currentBox.minY, // Our current feet
                                currentBox.maxZ);

                Iterable<VoxelShape> collisions =
                        client.world.getBlockCollisions(client.player, predictionColumn);

                if (collisions.iterator().hasNext()) {
                    ((PlayerMoveC2SAccessor) playerMoveC2SPacket).setOnGround(true);
                    client.player.fallDistance = 0.0F;
                }
            }
        }
    }
}
