/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jaggy.bungeelogin.BungeeLogin;

/**
 *
 * @author Matthew
 */
public class PlayerPostLogin implements Listener {
    private final BungeeLogin plugin;
    public PlayerPostLogin(BungeeLogin lplugin) {
        plugin = lplugin;
    }
    @EventHandler
    public void onPostLogin(PostLoginEvent event) {
        ProxiedPlayer player = event.getPlayer();
        boolean loggedin = false;
        if(loggedin == false) {
            plugin.utils.Messages.sendNotLoginMsg(player);
        }
    }
}
