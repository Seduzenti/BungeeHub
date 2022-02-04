package com.github.guilhxrme;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class Main extends Plugin {
    public static Configuration cg;
    public static Main plugin;

    public void onEnable() {
        plugin = this;
        ProxyServer.getInstance().getPluginManager().registerListener(this, new CommandEvent());
        this.createFiles();
        registerConfig();
    }

    public void createFiles() {
        if (!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }

        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            try {
                InputStream in = this.getResourceAsStream("config.yml");
                Files.copy(in, file.toPath(), new CopyOption[0]);
            } catch (IOException var3) {
                var3.printStackTrace();
            }
        }

    }

    public static void registerConfig() {
        try {
            cg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException var1) {
            var1.printStackTrace();
        }

    }
}
