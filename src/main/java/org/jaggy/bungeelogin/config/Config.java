/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package org.jaggy.bungeelogin.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jaggy.bungeelogin.BungeeLogin;

/**
 *
 * @author Matthew
 */
public class Config implements YAMLConfig {
    public static Configuration            config;
    public static ConfigurationProvider    cProvider;
    public static File                    cFile;
    private final BungeeLogin plugin = new BungeeLogin();
    private File cFolder;

    @Override
    public void loadConfig(String file) {
        cFolder = plugin.getDataFolder();
        cFile = new File(cFolder.toString()+ File.separator + file);
        if(!cFolder.exists()) {
            cFolder.mkdir();
        }
        if(!cFile.exists()) {
            try {
                cFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
         // This requests the ConfigurationProvider that Bungee uses
    cProvider = ConfigurationProvider.getProvider(YamlConfiguration.class);
    try {
        config = cProvider.load(cFile);
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    }

    @Override
    public void saveConfig() {
        try {
            cProvider.save(config, cFile);
        } catch (IOException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void createConfig(String file) {
        cFile = new File(cFolder.toString()+ File.separator + file);
        if (!cFile.exists()) {
            try {
                cFile.createNewFile();
            } catch (IOException e) {
            }
        }
    } 

    @Override
    public void createConfig(String file, String data) {
        cFile = new File(cFolder.toString()+ File.separator + file);
        if (!cFile.exists()) {
            try {
                cFile.createNewFile();
                 FileWriter fw = new FileWriter(cFile);
            BufferedWriter out = new BufferedWriter(fw);
            out.write(data);
            out.close();
            fw.close();
            } catch (IOException e) {
            }
        } else {
            
            try {
                FileWriter fw;
                fw = new FileWriter(cFile);
                BufferedWriter out = new BufferedWriter(fw);
                out.write(data);
                out.close();
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }

    @Override
    public boolean getOnlineMode() {
        return config.getBoolean("onlineMode");
    }

    @Override
    public String getAuthType() {
        return config.getString("AuthType");
    }

    @Override
    public String getMysqlDbName() {
        return config.getString("MysqlDbName");
    }

    @Override
    public String getMysqlUsername() {
        return config.getString("MysqlUserName");
    }

    @Override
    public String getMysqlPassword() {
        return config.getString("MysqlPassword");
    }

    @Override
    public String getMysqlHost() {
        return config.getString("MysqlHost");
    }

    @Override
    public Integer getMysqlPort() {
        return config.getInt("MysqlPort");
    }

    @Override
    public String getUsernameField() {
        return config.getString("UsernameField");
    }

    @Override
    public String getPasswordField() {
        return config.getString("PasswordField");
    }

    @Override
    public String getPasswordType() {
        return config.getString("PasswordType");
    }

    @Override
    public String getServerToken() {
        return config.getString("ServerToken");
    }

    @Override
    public String getAuthURL() {
        return config.getString("AuthURL");
    }
}
