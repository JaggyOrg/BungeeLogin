/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin.events;

import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.jaggy.bungeelogin.BungeeLogin;

/**
 *
 * @author Matthew
 */
public class PlayerChat implements Listener {
    private final boolean loggedin = false;
    private final BungeeLogin plugin;

    /**
     * Intitializes playerchat listener
     * @param lplugin
     */
    public PlayerChat(BungeeLogin lplugin) {
       plugin = lplugin;
    }
    @EventHandler
    public void onPlayerChat(ChatEvent event) {
        ProxiedPlayer player = (ProxiedPlayer) event.getSender();
        if(loggedin == false) {
            plugin.utils.sendNotLoginMsg(player);
            event.setCancelled(true);
        }
    }
}
