/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.CharBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jaggy.bungeelogin.events.PlayerChat;
import org.jaggy.bungeelogin.events.PlayerPostLogin;
import net.md_5.bungee.api.plugin.Plugin;
import org.jaggy.bungeelogin.config.Config;
import org.jaggy.bungeelogin.utils.Utils;

/**
 *
 * @author Matthew
 */
public class BungeeLogin extends Plugin {
    public Config config;
    public Logger log;
    public Utils utils;
    
    @Override
    public void onLoad() {
        log = this.getLogger();
        config = new Config(this, "config.yml");
        utils = new Utils(this);
    }
    @Override
    public void onEnable() {
        this.getProxy().getPluginManager().registerListener(this, new PlayerPostLogin(this));
        this.getProxy().getPluginManager().registerListener(this, new PlayerChat(this));
        if(!config.cFile.exists()) {
            config.createConfig("config.yml", "onlineMode: true\nAuthType: mysql\n"+
                    "MysqlDbName: bungeecord\nMysqlUserName: root\nMysqlPassword: ''\n"+
                    "MysqlHost: localhost\nMysqlPort: 3306\n\n#configure what fields"+
                    " to query and how.\n\nUsernameField: username\nPasswordField: password\n"+
                    "#type of encrption used\nPasswordType: ''");
            config.loadConfig();
        }
        utils.DB.connect();
        
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
