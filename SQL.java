package me.es359.Broadcast.Report;

import java.sql.*;

public class SQL
{
    private String host;
    private String user;
    private String pass;
    private String database;
    public Connection c;
    
    public String getHost() {
        return this.host;
    }
    
    public String getUser() {
        return this.user;
    }
    
    public String getPassword() {
        return this.pass;
    }
    
    public String getDatabase() {
        return this.database;
    }
    
    public void setHost(String host) {
        this.host = host;
    }
    
    public void setUser(String user) {
        this.user = user;
    }
    
    public void setPassword(String pass) {
        this.pass = pass;
    }
    
    public void setDatabase(String database) {
        this.database = database;
    }
    
    public SQL() {
    }
    
    public SQL(String ip, String userName, String access, String db) {
        this.host = ip;
        this.user = userName;
        this.pass = access;
        this.database = db;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.c = DriverManager.getConnection("jdbc:mysql://" + ip + "/" + db + "?user=" + userName + "&password=" + access);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public Connection getConnection() {
        return this.c;
    }
}
