/*
 * Copyright 2026 QWERTZexe ALL RIGHTS RESERVED
 */

package app.qwertz.mixin;

import app.qwertz.EventAlertsApi;
import app.qwertz.PlusTag;
import app.qwertz.PlusTagRenderer;

import cc.aabss.eventutils.EventUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardObjective;

import java.util.Set;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(PlayerListHud.class)
public abstract class PlayerListHudMixin {
    @Shadow @Final private MinecraftClient client;

    /** Draw + icon for every tab list row (local player = selected tag, others = best unlocked from Event Alerts). */
    @Inject(method = "renderLatencyIcon", at = @At("TAIL"))
    private void eventutils$drawPlusTagNextToName(DrawContext context, int width, int x, int y, PlayerListEntry entry, CallbackInfo ci) {
        if (client.player == null) return;

        //? if >=1.21.11 {
        String name = entry.getProfile().name();
        final boolean isLocal = entry.getProfile().id().equals(client.player.getUuid());
        //?} else {
        /*String name = entry.getProfile().getName();
        final boolean isLocal = entry.getProfile().getId().equals(client.player.getUuid());
        *///?}
        final PlusTag tag;

        if (isLocal) {
            // Fallback: if we never got a fetch (e.g. JOIN ran before player was ready), trigger it when tab list is drawn
            String localUuid = client.player.getUuid().toString();
            if (EventAlertsApi.getCached(localUuid) == null) {
                EventUtils.LOGGER.info("[EventUtils] Tab list: local player not cached, scheduling Event Alerts fetch uuid={}", localUuid);
                EventAlertsApi.scheduleFetchIfNeeded(localUuid);
            }
            tag = EventUtils.MOD.config.selectedPlusTag;
            EventUtils.LOGGER.debug("[TabList] entry={} isLocal=true tag=config.selectedPlusTag={}", name, tag);
        } else {
            //? if >=1.21.11 {
            String uuid = entry.getProfile().id().toString();
            //?} else {
            /*String uuid = entry.getProfile().getId().toString();
            *///?}
            Set<PlusTag> cached = EventAlertsApi.getCached(uuid);
            if (cached == null) {
                EventUtils.LOGGER.debug("[TabList] entry={} uuid={} cache MISS, scheduling fetch", name, uuid);
                EventAlertsApi.scheduleFetchIfNeeded(uuid);
                return;
            }
            tag = PlusTag.pickBestForDisplay(cached);
            EventUtils.LOGGER.debug("[TabList] entry={} uuid={} cache HIT unlocked={} pickBest={}", name, uuid, cached, tag);
        }

        if (tag == null || tag == PlusTag.WHITE) {
            EventUtils.LOGGER.debug("[TabList] entry={} skip draw: tag={} (null or WHITE)", name, tag);
            return;
        }

        int iconSize = 8;
        int iconX = x - 8;
        PlusTagRenderer.draw(context, tag, iconX, y, iconSize);
        EventUtils.LOGGER.debug("[TabList] entry={} DRAW tag={} at ({}, {}) size={}", name, tag, iconX, y, iconSize);
    }
}
