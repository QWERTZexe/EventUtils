package cc.aabss.eventutils.commands;

import cc.aabss.eventutils.EventUtils;
import cc.aabss.eventutils.config.PlayerGroup;

import com.mojang.brigadier.context.CommandContext;

import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;

import net.minecraft.text.Text;

import org.jetbrains.annotations.NotNull;

import static net.minecraft.text.Text.translatable;


public class GroupMsgCmd {
    public static int groupMsg(@NotNull CommandContext<FabricClientCommandSource> context, @NotNull String groupNameArg, @NotNull String message) {
        final var groups = EventUtils.MOD.config.groups;
        final String search = groupNameArg.toLowerCase();
        PlayerGroup group = null;
        for (final PlayerGroup g : groups) {
            if (g.getName().toLowerCase().equals(search)) {
                group = g;
                break;
            }
        }
        if (group == null) {
            context.getSource().sendError(translatable("eventutils.command.groupmsg.no_group", EventUtils.ERROR_MESSAGE_PREFIX, groupNameArg));
            return 0;
        }
        final String toSend = "[" + group.getName() + "] " + message;
        if (context.getSource().getPlayer() != null && context.getSource().getPlayer().networkHandler != null) {
            context.getSource().getPlayer().networkHandler.sendChatMessage(toSend);
        }
        return 1;
    }
}
