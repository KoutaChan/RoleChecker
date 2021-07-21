package me.koutachan.rolechecker.commands;

import me.koutachan.rolechecker.RoleChecker;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class RemoveDataBase implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + RoleChecker.plugin.getConfig().getString("sqllocate"));
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("drop table if exists roledata");
            sender.sendMessage("テーブル削除に成功しました！");
            statement.executeUpdate("create table roledata (uuid string, discordID string)");
            sender.sendMessage("テーブル再生成に成功しました！");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
        return false;
    }
}
