/*
 * Copyright 2014 JaggyOrg.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
        this.getProxy().getPluginManager().registerCommand(this, new cmdRegister(this));
        
        
        if(!config.cFile.exists()) {
            config.createConfig("config.yml", "onlineMode: true\nAuthType: mysql\n"+
                    "MysqlDbName: bungeecord\nMysqlTable:users\nMysqlUserName: root\nMysqlPassword: ''\n"+
                    "MysqlHost: localhost\nMysqlPort: 3306\n\n#configure what fields"+
                    " to query and how.\n\nUsernameField: username\nPasswordField: password\n"+
                    "#type of encrption used\nPasswordType: ''\n\n#Email Settings: Recomended to change these!"+
                    "\nMailFrom: no-reply@someemailservice.com\nMailServer: localhost\nMailUsername: ''\n"+
                    "MailPassword: ''");
            
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
