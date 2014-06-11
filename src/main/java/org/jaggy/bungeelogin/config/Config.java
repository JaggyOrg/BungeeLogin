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
        if(cFile.exists()) {
            
        }
    }

    @Override
    public void saveConfig() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
}
