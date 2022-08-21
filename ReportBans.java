package me.es359.Broadcast.Report;

import org.bukkit.entity.*;
import java.sql.*;
import org.bukkit.*;

public class ReportBans
{
    private SQL sql;
    private Report report;
    private String user_name;
    private String user;
    private boolean banned;
    public CreateSQLTables table;
    private String reason;
    private boolean status;
    
    public ReportBans() {
        this.table = new CreateSQLTables();
    }
    
    public String getUser() {
        return this.user_name;
    }
    
    public String getUserUUID() {
        return this.user;
    }
    
    public boolean getBanned() {
        return this.banned;
    }
    
    public void connection() {
        this.report.connectionExists();
        this.sql = this.report.getAccess();
        this.table.createTable(this.sql, "create table report_bans (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, Name varchar(25), UUID varchar(45), ban_status boolean, ban_reason varchar(350)); ");
    }
    
    public void checkBanned(final SQL sql, final Player p) {
        final String uuid = "" + p.getUniqueId();
        try {
            final Statement s = sql.getConnection().createStatement();
            final String query = "SELECT * FROM report_bans WHERE UUID='" + uuid + "'";
            final ResultSet set = s.executeQuery(query);
            while (set.next()) {
                final String result = set.getString(3);
                if (result.equals(uuid)) {
                    this.setStatus(true);
                }
                else {
                    this.setStatus(false);
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void getReason(final SQL sql, final Player p) {
        final String uuid = "" + p.getUniqueId();
        try {
            final Statement s = sql.getConnection().createStatement();
            final String query = "SELECT ban_reason FROM report_bans WHERE UUID='" + uuid + "'";
            final ResultSet set = s.executeQuery(query);
            while (set.next()) {
                final String result = ChatColor.RED + set.getString(1);
                p.sendMessage(result);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String returnReason() {
        return this.reason;
    }
    
    public boolean getStatus() {
        return this.status;
    }
    
    public void setStatus(final boolean b) {
        this.status = b;
    }
    
    @Deprecated
    public void submitBan(final SQL sql, final String uuid) {
    }
}
