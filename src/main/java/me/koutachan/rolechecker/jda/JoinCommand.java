package me.koutachan.rolechecker.jda;

import me.koutachan.rolechecker.RoleChecker;
import me.koutachan.rolechecker.util.SQLUtil;
import me.koutachan.rolechecker.util.UUIDUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.UUID;


public class JoinCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT)) {
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            if (args[0].equalsIgnoreCase(RoleChecker.prefix + "join")) {
                if (args.length != 2) {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("エラー！")
                            .setDescription("ユーザー名を入力してください")
                            .addField("使い方:", RoleChecker.prefix + "join ${マインクラフトID}", false)
                            .setTimestamp(event.getMessage().getTimeCreated());
                    event.getMessage().reply(embedBuilder.build()).queue();
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
                        event.getMessage().reply(embedBuilder.build()).queue();
                        return;
                    }
                    String[] result = new SQLUtil().request(uuid.toString(), null);
                    if (result != null) {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("エラーが発生したようです")
                                .setDescription("問題があると思う場合は管理者に報告してください")
                                .addField("エラー概要:", "すでにこのユーザー名は登録されているようです", false)
                                .addField("UUID:", uuid.toString(), false)
                                .addField("登録者ID:", result[1], false)
                                .setTimestamp(event.getMessage().getTimeCreated());
                        event.getMessage().reply(embedBuilder.build()).queue();
                    } else {
                        new SQLUtil().insert(uuid.toString(), event.getAuthor().getId(), false);
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.GREEN)
                                .setTitle("登録完了しました(" + args[1] + ")")
                                .addField("UUID:", uuid.toString(), false)
                                .addField("Discord ID:", event.getAuthor().getId(), false)
                                .setThumbnail("https://crafatar.com/avatars/" + uuid)
                                .setTimestamp(event.getMessage().getTimeCreated());
                        event.getMessage().reply(embedBuilder.build()).queue();
                    }
                }
            }
        }
    }
}
