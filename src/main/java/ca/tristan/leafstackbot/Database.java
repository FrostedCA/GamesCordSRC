package ca.tristan.leafstackbot;

import net.dv8tion.jda.api.entities.*;

import java.sql.*;

public class Database {

    private Connection conn;

    public Database(){
        try {
            conn = DriverManager.getConnection("-", "-", "-");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConn(){
        return conn;
    }

    public int getCoinsOfMember(Member member){
        try {
            PreparedStatement statement = getConn().prepareStatement("SELECT * FROM discord.members WHERE ID=?");
            statement.setString(1, member.getId());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(3);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public int getLevelOfMember(Member member){
        try {
            PreparedStatement statement = getConn().prepareStatement("SELECT * FROM discord.members WHERE ID=?");
            statement.setString(1, member.getId());
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(4);
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public void addCoinsToMember(Member member, int amount){
        try {
            PreparedStatement statement = getConn().prepareStatement("UPDATE discord.members SET coins=? WHERE ID=?");
            statement.setInt(1, getCoinsOfMember(member) + amount);
            statement.setString(2, member.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeCoinsFromMember(Member member, int amount){
        try {
            PreparedStatement statement = getConn().prepareStatement("UPDATE discord.members SET coins=? WHERE ID=?");
            if(getCoinsOfMember(member) >= amount){
                statement.setInt(1, getCoinsOfMember(member) - amount);
            }else{
                statement.setInt(1, 0);
            }
            statement.setString(2, member.getId());
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
