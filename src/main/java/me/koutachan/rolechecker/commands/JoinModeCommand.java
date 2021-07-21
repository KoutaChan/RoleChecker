package me.koutachan.rolechecker.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.Locale;

public class JoinModeCommand implements CommandExecutor {
    public static boolean mode = false;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length != 1) {
            sender.sendMessage(ChatColor.YELLOW + "現在参加" + (mode ? "可能です" : ChatColor.BOLD + "不可能" + ChatColor.YELLOW + "です"));
            sender.sendMessage("");
            sender.sendMessage(ChatColor.RED + "falseかtrueで変更可能です！");
            sender.sendMessage(ChatColor.RED + "true = 参加可能");
            sender.sendMessage(ChatColor.RED + "false = 参加不可能");
        }else if (args[0].equalsIgnoreCase("true") || args[0].equalsIgnoreCase("false")) {
            mode = Boolean.parseBoolean(args[0]);
            sender.sendMessage(ChatColor.YELLOW + args[0].toLowerCase() + "に変更しました！");
        }
    return true;
    }
}
