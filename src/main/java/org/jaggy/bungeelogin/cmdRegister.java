/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jaggy.bungeelogin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 *
 * @author Matthew
 */
class cmdRegister extends Command {
    private final BungeeLogin plugin;
    
    cmdRegister(BungeeLogin aThis) {
         super( "register", "bungeelogin.register" );
       plugin = aThis;
               
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if ((sender instanceof ProxiedPlayer)) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if (p.hasPermission("bungeelogin.register")) {
                if (args.length == 2) {
                    if(plugin.config.getAuthType().equalsIgnoreCase("mysql")) {
                        ResultSet result = plugin.utils.DB.query("SELECT * FROM " + plugin.config.getMysqlTable()+" WHERE "+plugin.config.getUsernameField()+ " = \""+p.getName()+"\"");
                        try {
                            if(result.getRow() == 0) {
                                String sql = "INSERT INTO " + plugin.config.getMysqlTable()+"("+plugin.config.getUsernameField()+","+plugin.config.getPasswordField()+", email, UUID) VALUES ('"+
                                        p.getName()+"', "+plugin.config.getPasswordType()+"('"+args[0]+"'), '"+args[1]+"', '"+p.getUniqueId().toString()+"')";
                                plugin.utils.DB.query(sql);
                            } else {
                                plugin.sendError(p, "This username is already registered! If you fdorgot your password do /forgotpassword and we will send you an e-mail with a new password.");
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
