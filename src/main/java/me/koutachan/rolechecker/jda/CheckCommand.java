package me.koutachan.rolechecker.jda;

import me.koutachan.rolechecker.RoleChecker;
import me.koutachan.rolechecker.util.Check;
import me.koutachan.rolechecker.util.SQLUtil;
import me.koutachan.rolechecker.util.UUIDUtil;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.util.UUID;

public class CheckCommand extends ListenerAdapter {
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (!event.getAuthor().isBot() && event.isFromType(ChannelType.TEXT) && new Check().AllowedChecker(event.getTextChannel().getId())) {
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            if(args[0].equalsIgnoreCase(RoleChecker.prefix +  "check")){
                if (args.length != 2) {
                    EmbedBuilder embedBuilder = new EmbedBuilder()
                            .setColor(Color.RED)
                            .setTitle("エラー！")
                            .setDescription("ユーザー名を入力してください")
                            .addField("使い方:", "!join ${マインクラフトID}", false)
                            .setTimestamp(event.getMessage().getTimeCreated());
                    event.getMessage().reply(embedBuilder.build()).queue();
                } else {
                    UUID uuid;
                    try {
                        uuid = UUIDUtil.getUUID(args[1]);
                    }catch (Exception e){
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("エラーが発生したようです")
                                .setDescription("問題があると思う場合は管理者に報告してください")
                                .addField("エラー概要:", "無効なユーザー名か他の重大なエラーが発生したようです", false)
                                .setTimestamp(event.getMessage().getTimeCreated());
                        event.getMessage().reply(embedBuilder.build()).queue();
                        if (RoleChecker.plugin.getConfig().getBoolean("pasteUUIDError")) {
                            StringWriter sw = new StringWriter();
                            PrintWriter pw = new PrintWriter(sw);
                            e.printStackTrace(pw);
                            pw.flush();

                            File file = new File("./error.txt");

                            if (file.exists()) {
                                try {
                                    Files.delete(file.toPath());
                                    Files.createFile(file.toPath());
                                    FileWriter filewriter = new FileWriter(file);
                                    filewriter.write(sw.toString());
                                    filewriter.close();
                                    sw.close();
                                    pw.close();
                                    event.getTextChannel().sendFile(file).queue();
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                        }
                        return;
                    }
                    String[] result = new SQLUtil().request(uuid.toString(),null);
                    if(result == null){
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.RED)
                                .setTitle("エラーが発生したようです")
                                .setDescription("問題があると思う場合は管理者に報告してください")
                                .addField("エラー概要:", "このプレイヤー名は登録されていないようです", false)
                                .addField("UUID:", uuid.toString(), false)
                                .setTimestamp(event.getMessage().getTimeCreated());
                        event.getMessage().reply(embedBuilder.build()).queue();
                    }else {
                        EmbedBuilder embedBuilder = new EmbedBuilder()
                                .setColor(Color.GREEN)
                                .setTitle("このプレイヤーの情報(" + args[1] + ")")
                                .setDescription("問題があると思う場合は管理者に報告してください")
                                .addField("UUID:", result[0], false)
                                .addField("DiscordID:", result[1], false)
                                .addField("参加可能ですか？:", (new Check().Checker(result[0],result[1]) ? "はい" : "いいえ"),false)
                                .setTimestamp(event.getMessage().getTimeCreated());
                        event.getMessage().reply(embedBuilder.build()).queue();
                    }
                }
            }
        }
    }
}
