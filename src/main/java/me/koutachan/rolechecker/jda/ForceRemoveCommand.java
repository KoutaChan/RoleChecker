package me.koutachan.rolechecker.jda;

import me.koutachan.rolechecker.RoleChecker;
import me.koutachan.rolechecker.api.event.EventListener;
import me.koutachan.rolechecker.util.Check;
import me.koutachan.rolechecker.util.SQLUtil;
import me.koutachan.rolechecker.util.UUIDUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.UUID;

public class ForceRemoveCommand extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT) && new Check().AllowedChecker(event.getTextChannel().getId())) {
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            if (args[0].equalsIgnoreCase(RoleChecker.prefix + "forceremove")) {
                if (event.getMember().hasPermission(Permission.ADMINISTRATOR)) {
                    if (args.length != 2) {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setTitle("エラー！")
                                .setDescription("ユーザー名を入力してください")
                                .addField("使い方:", RoleChecker.prefix + "forceremove ${マインクラフトID}", true)
                                .setColor(Color.RED)
                                .setTimestamp(event.getMessage().getTimeCreated());
                        EventListener.Event eventListener = new EventListener().request(null, event.getAuthor().getId(), embedBuilder, false, EventListener.reasonEnum.FORCEREMOVE);

                        event.getMessage().reply(eventListener.getEmbedBuilder().build()).queue();
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
                            EventListener.Event eventListener = new EventListener().request(null, event.getAuthor().getId(), embedBuilder, false, EventListener.reasonEnum.FORCEREMOVE);

                            event.getMessage().reply(eventListener.getEmbedBuilder().build()).queue();
                            return;
                        }
                        String[] result = new SQLUtil().request(uuid.toString(), null);
                        if (result != null) {
                            new SQLUtil().delete(result[0], result[1]);
                            EmbedBuilder embedBuilder = new EmbedBuilder()
                                    .setColor(Color.GREEN)
                                    .setTitle("削除完了")
                                    .addField("デバッグ情報(UUID):", result[0], false)
                                    .addField("所持していたID(DiscordID):", result[1], false)
                                    .setTimestamp(event.getMessage().getTimeCreated());
                            EventListener.Event eventListener = new EventListener().request(uuid.toString(), event.getAuthor().getId(), embedBuilder, true, EventListener.reasonEnum.FORCEREMOVE);

                            event.getMessage().reply(eventListener.getEmbedBuilder().build()).queue();
                        } else {
                            EmbedBuilder embedBuilder = new EmbedBuilder()
                                    .setColor(Color.RED)
                                    .setTitle("削除失敗")
                                    .addField("エラー概要:", "このユーザー名は登録されていません", false)
                                    .setTimestamp(event.getMessage().getTimeCreated());
                            EventListener.Event eventListener = new EventListener().request(uuid.toString(), event.getAuthor().getId(), embedBuilder, false, EventListener.reasonEnum.FORCEREMOVE);

                            event.getMessage().reply(eventListener.getEmbedBuilder().build()).queue();
                        }
                    }
                } else {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("権限がないようです！")
                            .addField("エラー概要:", "あなたは`ADMINISTRATOR`権限がありません", false)
                            .setTimestamp(event.getMessage().getTimeCreated());
                    EventListener.Event eventListener = new EventListener().request(null, event.getAuthor().getId(), embedBuilder, false, EventListener.reasonEnum.FORCEREMOVE);

                    event.getMessage().reply(eventListener.getEmbedBuilder().build()).queue();
                }
            }
        }
    }
}
