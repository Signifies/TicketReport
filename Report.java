package me.es359.Broadcast.Report;

import org.bukkit.*;
import org.bukkit.entity.*;
import java.util.*;
import org.bukkit.command.*;
import java.sql.*;

public class Report implements CommandExecutor
{
    public CreateSQLTables table;
    private String author;
    private String[] authList;
    boolean log;
    private SQL sql;
    private String message;
    
    public Report() {
        this.table = new CreateSQLTables();
        this.author = "9c5dd792-dcb3-443b-ac6c-605903231eb2";
        this.authList = new String[10];
        this.log = true;
        this.message = ChatColor.translateAlternateColorCodes('&', "&a&oGathering Data...");
    }
    
    public String[] getAuthors() {
        this.authList[0] = "9c5dd792-dcb3-443b-ac6c-605903231eb2";
        return this.authList;
    }
    
    public void displayUserInfo(Player p) {
        String msg = ChatColor.translateAlternateColorCodes('&', "   &6&o&lUser Display Info:\n   &3Server: &a&l%server%\n   &3User: &7%user%\n   &3UUID: &7%uuid%\n   &3World: &7%world%\n   &3Location: %location%\n   &3IP: &7%ip%");
        String server = "Broadcast";
        String user = p.getName();
        UUID uuid = p.getUniqueId();
        String world = p.getPlayer().getWorld().getName();
        String location = "X: " + p.getLocation().getBlockX() + " Y: " + p.getLocation().getY() + " Z: " + p.getLocation().getBlockZ();
        String ip = "" + p.getPlayer().getAddress();
        msg = msg.replaceAll("%user%", user);
        msg = msg.replaceAll("%uuid%", "" + uuid);
        msg = msg.replaceAll("%world%", world);
        msg = msg.replaceAll("%location%", location);
        msg = msg.replaceAll("%ip%", ip);
        msg = msg.replaceAll("%server%", server);
        msg = msg.replaceAll("\n", "\n");
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[]&8--------------------&c[]"));
        p.sendMessage(msg);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c[]&8--------------------&c[]"));
    }
    
    public void checkAuthor(Player p) {
        String uuid = "" + p.getUniqueId();
        if (uuid.equals(this.author)) {
            p.sendMessage(ChatColor.GRAY + "User Authentication complete. - ");
            displayUserInfo(p);
        }
    }
    
    public String getMessage() {
        return this.message;
    }
    
    public void runConnection() {
        this.sql = new SQL("107.170.21.151", "Logger", "REQUEST1", "Logs");
        try {
            table.createTable(sql, "CREATE TABLE IF NOT EXISTS user_reports (id INT PRIMARY KEY AUTO_INCREMENT, plugin varchar(25),  name varchar(50), UUID varchar(50), report varchar(250), stamp TIMESTAMP, status varchar(350));");
            table.createTable(sql, "CREATE TABLE report_bans (id INT NOT NULL PRIMARY KEY AUTO_INCREMENT, Name varchar(25), UUID varchar(45), banned_by varchar(35), ban_reason varchar(250), stamp TIMESTAMP); ");
            if (this.log) {}
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public SQL getAccess() {
        return this.sql;
    }
    
    public void connectionExists() {
        this.sql = new SQL("107.170.21.151", "Logger", "REQUEST1", "Logs");
    }
    
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cannot be used from console.");
            return true;
        }
        runConnection();
        Player p = (Player)sender;
        ReportBans bans = new ReportBans();
        String name = p.getName();
        String uuid = "" + p.getUniqueId();
        String plugin = "Broadcast";
        if (cmd.getName().equals("br-bugreport")) {
            bans.checkBanned(sql, p);
            if (bans.getStatus()) {
                bans.getReason(sql, p);
            }
            else if (args.length < 1) {
                p.sendMessage("Command usage: /br-bugreport <message>");
            }
            else if (args.length > 0) {
                StringBuilder str = new StringBuilder();
                for (int j = 0; j < args.length; ++j) {
                    str.append(args[j] + " ");
                }
                String msg = str.toString();
                msg = msg.replace("&", "§");
                try {
                    PreparedStatement statement = this.sql.getConnection().prepareStatement("INSERT INTO user_reports (plugin,name,UUID,report) VALUES (?,?,?,?)");
                    statement.setString(1, plugin);
                    statement.setString(2, name);
                    statement.setString(3, uuid);
                    statement.setString(4, msg);
                    statement.execute();
                    statement.close();
                    p.sendMessage(ChatColor.GREEN + "Your report was filed! Thanks, " + p.getName() + ChatColor.GREEN + ".");
                }
                catch (SQLException e) {
                    e.printStackTrace();
                    p.sendMessage(ChatColor.RED + "Error - Report couldn't be filed!");
                }
                return true;
            }
        }
        return true;
    }
}
