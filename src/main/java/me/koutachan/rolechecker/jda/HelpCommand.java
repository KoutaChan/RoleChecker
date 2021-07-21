package me.koutachan.rolechecker.jda;

import me.koutachan.rolechecker.RoleChecker;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

public class HelpCommand extends ListenerAdapter {

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT)) {
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            if (args[0].equalsIgnoreCase(RoleChecker.prefix + "help")) {
                event.getTextChannel().sendMessage("コマンド:"
                            + "\n`現在のprefix: " + RoleChecker.prefix + "{コマンド}`"
                            + "\n`" + RoleChecker.prefix + "help` - このBOTのヘルプを表示します"
                            + "\n`" + RoleChecker.prefix + "join {MCID}` - データベースに登録します"
                            + "\n`" + RoleChecker.prefix + "remove {MCID}` - データベースから登録を解除します"
                            + "\n`" + RoleChecker.prefix + "check {MCID}` - プレイヤーが登録されているか、参加可能か確認します"
                            + "\n`" + RoleChecker.prefix + "forcejoin {MCID} {DISCORD-ID}` - [AdminOnly] 強制的にデータベースから登録させます"
                            + "\n`" + RoleChecker.prefix + "forceremove {MCID}` - [AdminOnly] 強制的にデータベースから登録を解除します"
                ).queue();

            }
        }
    }
}
