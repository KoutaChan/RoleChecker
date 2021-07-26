package me.koutachan.rolechecker;

import me.koutachan.rolechecker.api.event.EventListener;
import me.koutachan.rolechecker.api.event.testAPI;
import me.koutachan.rolechecker.commands.JoinModeCommand;
import me.koutachan.rolechecker.commands.RemoveDataBase;
import me.koutachan.rolechecker.jda.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class RoleChecker extends JavaPlugin {
    public static List<String> list = new ArrayList<>();
    public static String prefix;
    public static Plugin plugin;
    public static JDA jda;

    @Override
    public void onEnable() {
        try {
            plugin = this;
            saveDefaultConfig();
            prefix = getConfig().getString("prefix");
            getCommand("joinmode").setExecutor(new JoinModeCommand());
            getCommand("removedatabase").setExecutor(new RemoveDataBase());
            getServer().getPluginManager().registerEvents(new Event(),this);
            list.addAll(getConfig().getStringList("role"));
            if(!getConfig().getBoolean("disablebotcommand")) {
                jda = JDABuilder.createDefault(getConfig().getString("token"))
                        .addEventListeners(new JoinCommand(),new RemoveCommand(),new HelpCommand(),new CheckCommand(),new ForceJoinCommand(),new ForceRemoveCommand())
                        .setStatus(OnlineStatus.ONLINE)
                        .setActivity(Activity.playing(getConfig().getString("activity")))
                        .build();
            }
        }catch (Exception ignored){
        }
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + getConfig().getString("sqllocate"));
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            statement.executeUpdate("create table if not exists roledata (uuid string, discordID string)");
            statement.close();
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
    }


    @Override
    public void onDisable() {
        jda.shutdown();
        // Plugin shutdown logic
    }
}
