package me.es359.Broadcast.Report;

import org.bukkit.entity.*;
import org.bukkit.*;
import java.sql.*;
import org.bukkit.command.*;

public class CheckReports implements CommandExecutor
{
    private Report report;
    private SQL sql;
    
    public CheckReports() {
        this.report = new Report();
    }
    
    public void authorCheck(final SQL con, final Player p) {
        try {
            final Statement s = con.getConnection().createStatement();
            final String query = "SELECT * FROM user_reports ;";
            final ResultSet set = s.executeQuery(query);
            final String header = ChatColor.GRAY + "Name           report    Status:\n--------------------------";
            p.sendMessage(header);
            while (set.next()) {
                final String result = ChatColor.AQUA + set.getString(1) + ChatColor.RESET + "  " + set.getString(2) + "  :" + ChatColor.GOLD + "  " + set.getString(3);
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
    
    public void authorCheck(final SQL con, final Player p, final String var) {
        try {
            final Statement s = con.c.createStatement();
            final String query = "SELECT * FROM user_reports WHERE id='" + var + "'";
            final ResultSet set = s.executeQuery(query);
            final String header = ChatColor.GRAY + "Name           report    Status:\n--------------------------";
            p.sendMessage(header);
            while (set.next()) {
                final String result = ChatColor.GOLD + "" + set.getString(1) + "  " + ChatColor.AQUA + set.getString(3) + ChatColor.RESET + "  " + set.getString(5) + "  :" + ChatColor.GOLD + "  " + set.getString(7);
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
    
    public void connection() {
        this.report.connectionExists();
        this.sql = this.report.getAccess();
    }
    
    public boolean onCommand(final CommandSender sender, final Command cmd, final String commandLabel, final String[] args) {
        this.connection();
        if (!(sender instanceof Player)) {
            sender.sendMessage("Unknown command. Type \"/help\" for help.");
            return true;
        }
        final Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("br-check")) {
            final String uuid = "" + p.getUniqueId();
            if (!uuid.equals(this.report.getAuthors())) {
                p.sendMessage("Unknown command. Type \"/help\" for help.");
            }
            else if (args.length < 1) {
                p.sendMessage(this.report.getMessage());
                this.authorCheck(this.sql, p);
                p.sendMessage(ChatColor.GREEN + "Use /check <id> for more information about the report.");
            }
            else if (args.length > 0) {
                p.sendMessage(this.report.getMessage());
                final String id = args[0];
                this.authorCheck(this.sql, p, id);
            }
        }
        return false;
    }
}
