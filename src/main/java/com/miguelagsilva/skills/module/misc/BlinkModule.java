package com.miguelagsilva.skills.module.misc;

import com.google.common.eventbus.Subscribe;
import com.miguelagsilva.skills.event.PacketEvent;
import com.miguelagsilva.skills.module.AbstractModule;
import com.miguelagsilva.skills.module.ModuleCategory;
import java.util.*;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;

public class BlinkModule extends AbstractModule {
    private long lastBlink;
    private final long BLINK_INTERVAL_MS = 500;
    private final Queue<PacketEvent.Send> queue = new LinkedList<>();
    private final Random random = new Random();

    public BlinkModule() {
        super("Blink", "Fakes laggy movement", ModuleCategory.MISC);
        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> onServerDisconnect());
    }

    @Override
    public void onEnable() {
        lastBlink = System.currentTimeMillis();
    }

    @Override
    public void onDisable() {
        flushQueue();
    }

    @Subscribe
    public void outPacket(PacketEvent.Send event) {
        if (client.world == null || client.player == null || client.getNetworkHandler() == null)
            return;
        if (!(event.getPacket() instanceof PlayerMoveC2SPacket move)) return;

        if (System.currentTimeMillis() - lastBlink >= BLINK_INTERVAL_MS) {
            // Releases the packets
            flushQueue();
            lastBlink = System.currentTimeMillis();
        } else {
            // Holds packets
            queue.add(event);
            event.setCancelled(true);
        }
    }

    private void flushQueue() {
        if (client == null || client.getNetworkHandler() == null || queue.isEmpty()) return;

        while (!queue.isEmpty()) {
            PacketEvent.Send event = queue.poll();
            client.getNetworkHandler().sendPacket(event.getPacket());
        }
    }

    private void onServerDisconnect() {
        if (!this.isEnabled()) return;
        queue.clear();
        this.toggle();
    }
}
