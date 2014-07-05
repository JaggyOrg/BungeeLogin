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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.logging.Level;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Matthew
 */
class cmdRegister extends Command {

    private final BungeeLogin plugin;

    cmdRegister(BungeeLogin aThis) {
        super("register", "bungeelogin.register");
        plugin = aThis;

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (p.hasPermission("bungeelogin.register")) {
                if (args.length == 2) {
                    if (plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                        ResultSet result = plugin.utils.DB.query("SELECT * FROM " + plugin.config.getMysqlTable() + " WHERE " + plugin.config.getUsernameField() + " = \"" + p.getName() + "\"");
                        try {
                            if (result.getRow() == 0) {
                                String sql = "INSERT INTO " + plugin.config.getMysqlTable() + "(" + plugin.config.getUsernameField() + "," + plugin.config.getPasswordField() + ", email, UUID) VALUES ('"
                                        + p.getName() + "', " + plugin.config.getPasswordType() + "('" + args[0] + "'), '" + args[1] + "', '" + p.getUniqueId().toString() + "')";
                                plugin.utils.DB.query(sql);
                                plugin.log.log(Level.INFO, "{0} with UUID of {1}, and ip of {2} Registered! Sending e-mail to {3}", new Object[]{p.getName(), p.getUniqueId(), p.getAddress().getAddress(), args[1]});
                                Collection<ProxiedPlayer> players = plugin.getProxy().getPlayers();
                                for (ProxiedPlayer player : players) {
                                    if (player.hasPermission("bungeelogin.seeregistrations")) {
                                        TextComponent component = new TextComponent("[BL] "+p.getName()+" with the ip of "+p.getAddress().getAddress()+" Registered!");
                                        component.setColor(ChatColor.WHITE);
                                        player.sendMessage(component);
                                    }
                                }
                                plugin.utils.Email.send(args[1], plugin.getProxy().getName()+" - E-mail verification", "Thank you for registering!"+
                                        " To verify your email type in or copy and paste in this command while on our service: /verify "+java.util.UUID.randomUUID());
                            } else {
                                plugin.sendError(p, "This username is already registered! If you forgot your password do /forgotpassword and we will send you an e-mail with a new password.");
                            }
                        } catch (SQLException ex) {
                            plugin.log.log(Level.SEVERE, null, ex);
                        }
                    }
                } else {
                    plugin.sendError(p, "To register do: /register <password> <email>");
                }
            } else {
                plugin.sendError(p, "You do not have permissions to use /register contact an admin!");
            }
        }
    }

}
