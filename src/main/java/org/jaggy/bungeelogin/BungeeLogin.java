/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin;
import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jaggy.bungeelogin.events.PlayerChat;
import org.jaggy.bungeelogin.events.PlayerPostLogin;
import net.md_5.bungee.api.plugin.Plugin;
import org.jaggy.bungeelogin.config.Config;

/**
 *
 * @author Matthew
 */
public class BungeeLogin extends Plugin {
    public Config config;
    public Logger log;
    
    @Override
    public void onEnable() {
        log = this.getLogger();
        config = new Config(this, "config.yml");
                
        this.getProxy().getPluginManager().registerListener(this, new PlayerPostLogin());
        this.getProxy().getPluginManager().registerListener(this, new PlayerChat(this));
        
    }
    /**
     * Send an message to player.
     * @param reciever
     * @param msg 
     */
    public void sendMessage(ProxiedPlayer reciever, String msg) {
        
        TextComponent component =  new TextComponent(msg);
        component.setColor(ChatColor.GREEN);
        component.setBold(true);
        reciever.sendMessage(component);
    }
    /**
     * Sends an error message to player.
     * @param reciever
     * @param msg 
     */
    public void sendError(ProxiedPlayer reciever, String msg) {
        
        TextComponent component =  new TextComponent(msg);
        component.setColor(ChatColor.DARK_RED);
        component.setBold(Boolean.TRUE);
        reciever.sendMessage(component);
    }
    /**
     * Gets the plugins config.yml
     */
    public void getConfig() {
        config = new Config(this, "config.yml");
        
       
        config.saveConfig();
    }
}
