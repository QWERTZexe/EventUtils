/*
 * Copyright 2026 QWERTZexe ALL RIGHTS RESERVED
 */

package app.qwertz.commands;

import app.qwertz.EventAlertsApi;
import app.qwertz.PlusTagScreen;

import cc.aabss.eventutils.EventUtils;

import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import net.minecraft.client.MinecraftClient;

import org.jetbrains.annotations.NotNull;


public class TagCmd {
    public static int openTagScreen(@NotNull CommandContext<FabricClientCommandSource> context) {
        MinecraftClient client = context.getSource().getClient();
        client.send(() -> {
            var player = context.getSource().getPlayer();
            var unlocked = player != null
                    ? EventAlertsApi.getCached(player.getUuid().toString())
                    : null;
            EventUtils.LOGGER.debug("[TagCmd] openTagScreen: player={} uuid={} cachedUnlocked={}", player != null ? player.getName().getString() : null, player != null ? player.getUuid() : null, unlocked);
            client.setScreen(new PlusTagScreen(client.currentScreen, unlocked));
        });
        return 1;
    }
}
