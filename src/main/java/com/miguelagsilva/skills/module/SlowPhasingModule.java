package com.miguelagsilva.skills.module;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.TickEvent;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class SlowPhasingModule extends AbstractModule {
    private final int PACKET_TICK_INTERVAL = 2;
    private int tickCounter = 0;

    protected SlowPhasingModule() {
        super(
                "SlowPhasing",
                "Slowly moves in looking direction bypasses protection",
                ModuleCategory.MOVEMENT);
    }

    @Subscribe
    public void onTick(TickEvent event) {
        if (client.player == null) return;
        tickCounter++;
        if (tickCounter < PACKET_TICK_INTERVAL) return;

        tickCounter = 0;

        double yawRadians = Math.toRadians(client.player.getYaw());
        double nudgeAmount = 0.00001;
        double moveX = -Math.sin(yawRadians) * nudgeAmount;
        double moveZ = Math.cos(yawRadians) * nudgeAmount;

        client.player.setPosition(
                client.player.getX() + moveX, client.player.getY(), client.player.getZ() + moveZ);

        PlayerMoveC2SPacket.PositionAndOnGround movePacket =
                new PlayerMoveC2SPacket.PositionAndOnGround(
                        client.player.getX() + moveX,
                        client.player.getY(),
                        client.player.getZ() + moveZ,
                        client.player.isOnGround(),
                        false);
        client.getNetworkHandler().sendPacket(movePacket);

        // print all values
        System.out.printf(
                "Sent move packet: X=%.10f, Y=%.2f, Z=%.10f, Yaw=%.2f, Pitch=%.2f, OnGround=%b, moveX=%.10f, moveZ=%.10f\n",
                client.player.getX() + moveX,
                client.player.getY(),
                client.player.getZ() + moveZ,
                client.player.getYaw(),
                client.player.getPitch(),
                client.player.isOnGround(),
                moveX,
                moveZ);
    }
}
