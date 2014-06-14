/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jaggy.bungeelogin.BungeeLogin;

/**
 *
 * @author Matthew
 */
public class Utils {
    public final BungeeLogin plugin;
    public Messages Messages = new Messages();
    public Database DB = new Database();

    public Utils(BungeeLogin lplugin) {
        plugin = lplugin;
    }
    public class Messages {

        public void sendNotLoginMsg(ProxiedPlayer player) {
            if (plugin.config.getOnlineMode()) {
                if (plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                    plugin.sendError(player, "You must /register to be able to use our service.");
                } else {
                    plugin.sendError(player, "This server is linked to a website and you must /register to be able to use our service.");
                }
            } else {
                if (plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                    plugin.sendError(player, "You must /login or /register to be able to use our service.");
                } else {
                    plugin.sendError(player, "You must /login or /register to be able to use our service.");
                }
            }
        }
    }
    public class Database {
        public void connect() {
            if(plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                String connectionstr = "jdbc:mysql://"+plugin.config.getMysqlHost()+":"+plugin.config.getMysqlPort()+"/"+
                        plugin.config.getMysqlDbName()+"?" + "user="+plugin.config.getMysqlUsername()+"&password="+plugin.config.getMysqlPassword();
                try {
                    Connection connection = DriverManager.getConnection(connectionstr);
                } catch (SQLException ex) {
                    Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
}

