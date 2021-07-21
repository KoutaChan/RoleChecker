package me.koutachan.rolechecker;

import me.koutachan.rolechecker.commands.JoinModeCommand;
import me.koutachan.rolechecker.util.Check;
import me.koutachan.rolechecker.util.SQLUtil;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;

public class Event implements Listener {

    @EventHandler
    public void AsyncPlayerPreLoginEvent(AsyncPlayerPreLoginEvent e) {
        if(JoinModeCommand.mode)return;
        String[] SQL = new SQLUtil().request(e.getUniqueId().toString(), null);
        if (SQL == null) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "あなたには参加権限がありません" + ChatColor.RED + "[エラー | 登録されていません]");
            return;
        }
        if(!new Check().Checker(e.getUniqueId().toString(),null)){
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, "あなたには参加権限がありません" + ChatColor.RED + "[エラー | 登録ロールがついていません]");
        }
    }
}
