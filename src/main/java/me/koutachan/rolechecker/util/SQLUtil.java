package me.koutachan.rolechecker.util;

import me.koutachan.rolechecker.RoleChecker;

import java.sql.*;

public class SQLUtil {
    public String[] request(String uuid, String discordID) {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + RoleChecker.plugin.getConfig().getString("sqllocate"));
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            PreparedStatement preparedStatement;
            if(discordID != null){
                preparedStatement = connection.prepareStatement("select * from roledata where uuid = ? and discordID = ?");
                preparedStatement.setString(2,discordID);
            }else {
                preparedStatement = connection.prepareStatement("select * from roledata where uuid = ?");
            }
            preparedStatement.setString(1,uuid);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) return new String[]{resultSet.getString(1),resultSet.getString(2)};
        }catch(SQLException e) {
            System.err.println(e.getMessage());
        }
        finally {
            try {
                if(connection != null)
                    connection.close();
            }
            catch(SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    return null;
    }

    public void insert(String uuid,String discordID,boolean delete){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + RoleChecker.plugin.getConfig().getString("sqllocate"));
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            if(request(uuid,discordID) != null){
                if(!delete) return;
                delete(uuid, discordID);
            }
            PreparedStatement insert = connection.prepareStatement("insert into roledata values (?,?)");
            insert.setString(1,uuid);
            insert.setString(2,discordID);
            insert.execute();
            insert.close();
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

    public void delete(String uuid,String discordID){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + RoleChecker.plugin.getConfig().getString("sqllocate"));
            Statement statement = connection.createStatement();
            statement.setQueryTimeout(30);
            PreparedStatement preparedStatement = connection.prepareStatement("delete from roledata where uuid = ? and discordID = ?");
            preparedStatement.setString(1,uuid);
            preparedStatement.setString(2,discordID);
            preparedStatement.execute();
            preparedStatement.close();
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
}
