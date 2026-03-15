package cc.aabss.eventutils.mixin;

import cc.aabss.eventutils.EventUtils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
//? if >=1.21.11 {
import net.minecraft.client.render.entity.EntityRenderManager;
//?} else {
/*import net.minecraft.client.render.entity.EntityRenderDispatcher;
*///?}
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


//? if >=1.21.11 {
@Mixin(EntityRenderManager.class)
//?} else {
/*@Mixin(EntityRenderDispatcher.class)
*///?}
public class EntityRenderDispatcherMixin {
    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    //? if <=1.21.1 {
    /*private <E extends Entity> void render(Entity entity, double x, double y, double z, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci) {
    *///?} else {
    private <E extends Entity> void render(E entity, double x, double y, double z, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci) {
    //?}
        if (!EventUtils.isInHidePlayersMode()) return;

        if (entity instanceof PlayerEntity player) {
            // Players
            if (player.isMainPlayer()) return;
            final String name = player.getName().getString().toLowerCase();
            if (EventUtils.isPlayerVisible(name)) return;
        } else {
            // Non-players (mob)
            if (!EventUtils.MOD.config.hiddenEntityTypes.contains(entity.getType())) return;
        }

        // Any radius
        if (EventUtils.MOD.config.hidePlayersRadius == 0) {
            ci.cancel();
            return;
        }

        // Specific radius
        final ClientPlayerEntity mainPlayer = MinecraftClient.getInstance().player;
        //? if >=1.21.11 {
        if (mainPlayer != null && mainPlayer.getSyncedPos().distanceTo(entity.getSyncedPos()) <= EventUtils.MOD.config.hidePlayersRadius) ci.cancel();
        //?} else {
        /*if (mainPlayer != null && mainPlayer.getPos().distanceTo(entity.getPos()) <= EventUtils.MOD.config.hidePlayersRadius) ci.cancel();
        *///?}
    }
}
