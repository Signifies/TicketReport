package me.es359.Broadcast.Report;

import org.bukkit.entity.*;
import org.bukkit.*;
import java.sql.*;
import org.bukkit.event.player.*;
import org.bukkit.event.*;
import org.bukkit.command.*;

public class GetReports implements CommandExecutor, Listener
{
    public SQL sql;
    public Report report;
    
    public GetReports() {
        this.report = new Report();
    }
    
    public void checkReports(final SQL con, final Player p) {
        try {
            final Statement s = con.c.createStatement();
            final String query = "SELECT * FROM user_reports WHERE UUID='" + p.getUniqueId() + "'";
            final ResultSet set = s.executeQuery(query);
            final String header = ChatColor.GRAY + "Name           report    Status:\n--------------------------";
            p.sendMessage(this.report.getMessage());
            p.sendMessage(header);
            while (set.next()) {
                p.sendMessage(this.report.getMessage());
                final String result = ChatColor.AQUA + set.getString(3) + ChatColor.RESET + "  " + set.getString(5) + "  :" + ChatColor.GOLD + "  " + set.getString(7);
                p.sendMessage(result);
            }
            p.sendMessage("\n");
            p.sendMessage(ChatColor.GRAY + "Data displayed.");
        }
        catch (SQLException e) {
            e.printStackTrace();
            p.sendMessage(ChatColor.RED + "ERROR - No data found.");
        }
    }
    
    private void connection() {
        this.report.connectionExists();
        this.sql = this.report.getAccess();
    }
    
    public void checkForReports(final SQL sql, final Player p) {
        try {
            final Statement s = sql.getConnection().createStatement();
            final String query = "SELECT * FROM user_reports WHERE UUID='" + p.getUniqueId() + "'";
            final ResultSet set = s.executeQuery(query);
            while (set.next()) {
                final String result = set.getString(7);
                if (!result.isEmpty()) {
                    p.sendMessage(ChatColor.YELLOW + p.getName() + ChatColor.GREEN + ", You have answered reports waiting for you! do /br-reports to check!");
                }
                else {
                    if (result.isEmpty()) {
                        return;
                    }
                    continue;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        this.connection();
        final Player p = event.getPlayer();
        final String user = "" + p.getUniqueId();
        this.report.checkAuthor(p);
        this.checkForReports(this.sql, p);
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This cannot be sent from the console.");
            return true;
        }
        this.connection();
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("br-reports") && args.length < 1) {
            this.checkReports(this.sql, p);
        }
        return true;
    }
}
