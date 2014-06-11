/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin;
import java.io.File;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jaggy.bungeelogin.events.PlayerChat;
import org.jaggy.bungeelogin.events.PlayerPostLogin;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;

/**
 *
 * @author Matthew
 */
public class BungeeLogin extends Plugin {
    
    @Override
    public void onEnable() {
        this.getProxy().getPluginManager().registerListener(this, new PlayerPostLogin());
        this.getProxy().getPluginManager().registerListener(this, new PlayerChat());
        
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
}
