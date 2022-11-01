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
    
    public void authorCheck(SQL con, Player p) {
        try {
             Statement s = con.getConnection().createStatement();
             String query = "SELECT * FROM user_reports ;";
             ResultSet set = s.executeQuery(query);
             String header = ChatColor.GRAY + "Name           report    Status:\n--------------------------";
            p.sendMessage(header);
            while (set.next()) {
                 String result = ChatColor.AQUA + set.getString(1) + ChatColor.RESET + "  " + set.getString(2) + "  :" + ChatColor.GOLD + "  " + set.getString(3);
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
    
    public void authorCheck(SQL con, Player p, String var) {
        try {
             Statement s = con.c.createStatement();
             String query = "SELECT * FROM user_reports WHERE id='" + var + "'";
             ResultSet set = s.executeQuery(query);
             String header = ChatColor.GRAY + "Name           report    Status:\n--------------------------";
            p.sendMessage(header);
            while (set.next()) {
                 String result = ChatColor.GOLD + "" + set.getString(1) + "  " + ChatColor.AQUA + set.getString(3) + ChatColor.RESET + "  " + set.getString(5) + "  :" + ChatColor.GOLD + "  " + set.getString(7);
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
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        this.connection();
        if (!(sender instanceof Player)) {
            sender.sendMessage("Unknown command. Type \"/help\" for help.");
            return true;
        }
         Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("br-check")) {
             String uuid = "" + p.getUniqueId();
            if (!uuid.equals(this.report.getAuthors())) {
                p.sendMessage("Unknown command. Type \"/help\" for help.");
            }
            else if (args.length < 1) {
                p.sendMessage(this.report.getMessage());
                authorCheck(this.sql, p);
                p.sendMessage(ChatColor.GREEN + "Use /check <id> for more information about the report.");
            }
            else if (args.length > 0) {
                p.sendMessage(this.report.getMessage());
                 String id = args[0];
                authorCheck(this.sql, p, id);
            }
        }
        return false;
    }
}
