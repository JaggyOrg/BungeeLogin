/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin.utils;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.jaggy.bungeelogin.BungeeLogin;

/**
 *
 * @author Matthew
 */
public class Utils {
    private final BungeeLogin plugin;

    public Utils(BungeeLogin lplugin) {
        plugin = lplugin;
    }
    public void sendNotLoginMsg(ProxiedPlayer player) {
        if(plugin.config.getOnlineMode()) {
            if(plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                plugin.sendError(player, "You must /register to be able to use our service.");
            } else {
                plugin.sendError(player, "This server is linked to a website and you must /register to be able to use our service.");
            }
        } else {
            if(plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                plugin.sendError(player, "You must /login or /register to be able to use our service.");
            } else {
                plugin.sendError(player, "You must /login or /register to be able to use our service.");
            }
        }
    }
}
