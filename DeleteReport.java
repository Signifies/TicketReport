package me.es359.Broadcast.Report;

import org.bukkit.entity.*;
import org.bukkit.*;
import java.sql.*;
import org.bukkit.command.*;

public class DeleteReport implements CommandExecutor
{
    private SQL sql;
    Report report;
    
    public DeleteReport() {
        this.report = new Report();
    }
    
    private void deleteReport(SQL con, Player p, String var) {
        try {
            PreparedStatement statement = con.getConnection().prepareStatement("DELETE FROM user_reports WHERE id='" + var + "';");
            statement.execute();
            statement.close();
            p.sendMessage(ChatColor.RED + "Deleted Report ID, " + var + ".");
        }
        catch (SQLException e) {
            e.printStackTrace();
            p.sendMessage(ChatColor.RED + "ERROR - Deletion didn't work..");
        }
    }
    
    private void connection() {
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
        if (cmd.getName().equalsIgnoreCase("br-deletereport")) {
            String uuid = "" + p.getUniqueId();
            if (!uuid.equals(this.report.getAuthors())) {
                p.sendMessage("Unknown command. Type \"/help\" for help.");
            }
            else if (args.length < 1) {
                p.sendMessage(ChatColor.RED + "/delete <id> [Use /check to check reports.]");
            }
            else if (args.length == 1) {
                p.sendMessage(this.report.getMessage());
                String id = args[0];
                deleteReport(this.sql, p, id);
            }
        }
        return false;
    }
}
