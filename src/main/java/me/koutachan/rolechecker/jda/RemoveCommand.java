package me.koutachan.rolechecker.jda;

import me.koutachan.rolechecker.RoleChecker;
import me.koutachan.rolechecker.api.event.EventListener;
import me.koutachan.rolechecker.util.SQLUtil;
import me.koutachan.rolechecker.util.UUIDUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.UUID;

public class RemoveCommand extends ListenerAdapter {

    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT)) {
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            if (args[0].equalsIgnoreCase(RoleChecker.prefix + "remove")) {
                if (args.length != 2) {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("エラー！")
                            .setDescription("ユーザー名を入力してください")
                            .addField("使い方:", RoleChecker.prefix + "remove ${マインクラフトID}", true)
                            .setTimestamp(event.getMessage().getTimeCreated());
                    EventListener.Event eventListener = new EventListener().request(null, event.getAuthor().getId(), embedBuilder, false, EventListener.reasonEnum.REMOVE);

                    event.getMessage().reply(eventListener.getEmbedBuilder().build()).queue();
                } else {
                    UUID uuid;
                    try {
                        uuid = UUIDUtil.getUUID(args[1]);
                    } catch (Exception e) {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("エラーが発生したようです")
                                .setDescription("問題があると思う場合は管理者に報告してください")
                                .addField("エラー概要:", "無効なユーザー名か他の重大なエラーが発生したようです", false)
                                .setTimestamp(event.getMessage().getTimeCreated());
                        EventListener.Event eventListener = new EventListener().request(null, event.getAuthor().getId(), embedBuilder, false, EventListener.reasonEnum.REMOVE);

                        event.getMessage().reply(eventListener.getEmbedBuilder().build()).queue();
                        return;
                    }

                    String[] result = new SQLUtil().request(uuid.toString(), null);

                    if (result != null) {
                        new SQLUtil().delete(result[0], result[1]);
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.GREEN)
                                .setTitle("削除完了")
                                .setDescription("問題があると思う場合は管理者に報告してください")
                                .setTimestamp(event.getMessage().getTimeCreated());
                        EventListener.Event eventListener = new EventListener().request(uuid.toString(), event.getAuthor().getId(), embedBuilder, true, EventListener.reasonEnum.REMOVE);

                        event.getMessage().reply(eventListener.getEmbedBuilder().build()).queue();
                    } else {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("削除失敗")
                                .setDescription("問題があると思う場合は管理者に報告してください")
                                .addField("エラー概要:", "本当にこのユーザー名の所持者ですか？", false)
                                .setTimestamp(event.getMessage().getTimeCreated());

                        EventListener.Event eventListener = new EventListener().request(uuid.toString(), event.getAuthor().getId(), embedBuilder, false, EventListener.reasonEnum.REMOVE);

                        event.getMessage().reply(eventListener.getEmbedBuilder().build()).queue();
                    }
                }
            }
        }
    }
}
