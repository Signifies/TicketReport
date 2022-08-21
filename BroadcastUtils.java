package Utilities;

import org.bukkit.entity.*;
import me.es359.Broadcast.*;
import org.bukkit.command.*;
import org.bukkit.*;
import java.util.*;

public class BroadcastUtils
{
    private final String VERSION_ID = "3.0.2 6/5/16";
    private String prefix;
    private String permission;
    private String donationURL;
    String author;
    
    public BroadcastUtils() {
        this.prefix = ChatColor.translateAlternateColorCodes('&', "&2&l[&b&lBroadcast&2&l]&6: &f");
        this.permission = this.getPrefix() + this.color("&cUnknown command. Type \"/help\" for help.");
        this.donationURL = this.color("https://www.paypal.me/ES359");
        this.author = "9c5dd792-dcb3-443b-ac6c-605903231eb2";
    }
    
    public String getPermission() {
        return this.permission;
    }
    
    public String getDonationURL() {
        return this.donationURL;
    }
    
    public boolean checkAuth(final UUID user) {
        return user.toString().equals(this.author);
    }
    
    public void displayAuthInfo(final Player p) {
        if (this.checkAuth(p.getUniqueId())) {
            p.sendMessage(this.color("&a&l&oHello, &7" + p.getName() + "\n &aThis server is using " + this.getPrefix() + "&r\n&cVersion ID: &7" + "3.0.2 6/5/16"));
        }
    }
    
    public String getPluginVersion(final Broadcast main, final CommandSender sender) {
        return this.color("&fHello, &a&n" + sender.getName() + ".&r\nYou are currently running version &b&n" + main.pdfFile.getVersion() + "&r of &e&n" + main.pdfFile.getName() + "&r\n \n&6Your server is running version &c&n" + main.getServer().getBukkitVersion());
    }
    
    public void authorToggle(final Broadcast main, final Player author) {
        if (!main.getAuthor().contains(author.getUniqueId())) {
            this.displayAuthInfo(author);
        }
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void logToConsole(final String message) {
        Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
    
    public void desc(final CommandSender sender, final Broadcast main) {
        sender.sendMessage(this.color("&2========== " + this.getPrefix().replace(":", "") + "&2=========="));
        sender.sendMessage(this.color("&7[&9" + main.pdfFile.getName() + "&7] &6Created by, &b&l" + main.pdfFile.getAuthors() + "&6."));
        sender.sendMessage(this.color("&2" + main.pdfFile.getDescription() + "&2."));
        sender.sendMessage(this.color("&bWebsite: &e&l" + main.pdfFile.getWebsite()));
        sender.sendMessage(this.color("&bList of sounds: &3&nhttps://gist.github.com/ES359/7aa8da5dbf88496e4098 - Configuration sounds... "));
        sender.sendMessage(this.color("&bMinecraft Color Codes: &a&nhttp://minecraftcolorcodes.com/"));
        sender.sendMessage(this.color("&7If you find bugs, errors, or would like to suggest ideas: https://github.com/ES359/Broadcast/issues/new"));
        sender.sendMessage(this.color("&9If you like my work, you can support me by donating &ahere: " + this.getDonationURL()));
        sender.sendMessage(this.color("&8-----------------------------"));
    }
    
    public void displayHelp(final CommandSender sender, final String title, final String command, final String info) {
        sender.sendMessage(this.color(title));
        sender.sendMessage("");
        sender.sendMessage(this.color(command));
        sender.sendMessage("");
        sender.sendMessage(this.color(info));
    }
    
    public void displayHelpMsg(final Player player, final String title, final String body, final String information) {
        player.sendMessage(this.color(title));
        player.sendMessage("");
        player.sendMessage(this.color(body));
        player.sendMessage(" ");
        player.sendMessage(this.color(information));
    }
    
    public String color(final String message) {
        String msg = message;
        msg = msg.replace("&", "§");
        msg = msg.replace("%prefix%", this.getPrefix());
        return msg;
    }
    
    public void exceptionDebug(final Exception e) {
        if (Broadcast.DEBUG) {
            this.logToConsole("&cERROR: &3" + e.getMessage());
            e.printStackTrace();
        }
        else {
            this.logToConsole("&cERROR: &3" + e.getMessage());
        }
    }
    
    public void broadcastSound(final String sound, final boolean enabled) {
        if (enabled) {
            try {
                for (final Player p : Bukkit.getServer().getOnlinePlayers()) {
                    p.playSound(p.getLocation(), Sound.valueOf(sound), 1.0f, 1.0f);
                }
            }
            catch (NullPointerException e) {
                this.exceptionDebug(e);
            }
        }
    }
    
    public void sendText(final ArrayList<String> text, final CommandSender sender) {
        for (String txt : text) {
            txt = txt.replace("%player%", sender.getName());
            sender.sendMessage(this.color(txt));
        }
    }
}
