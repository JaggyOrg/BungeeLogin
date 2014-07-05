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
            if(event.isCommand()) {
                if(!player.hasPermission("bungeelogin.usecommands") && !event.getMessage().contains("register")) {
                    plugin.utils.Messages.sendNotLoginMsg(player);
                    event.setCancelled(true);
                }
            } else {
                if(!player.hasPermission("bungeelogin.canchat")) {
                    plugin.utils.Messages.sendNotLoginMsg(player);
                    event.setCancelled(true);
                }
            }
        }
    }
}
