package com.github.guilhxrme;

import java.util.List;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ChatEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class CommandEvent implements Listener {
    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer player = e.getPlayer();
        if (player.getName().equals("izzypixel")) {
            player.sendMessage("");
            player.sendMessage("§aBungeHub > §eEste servidor esta usando o plugin BungeeHub");
            player.sendMessage("§aBungeHub > §6Versão §b" + Main.plugin.getDescription().getVersion());
        }

    }

    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.isCommand()) {
            ProxiedPlayer player = (ProxiedPlayer)e.getSender();
            List<String> commands = Main.cg.getStringList("Lobby-Comando");
            List<String> denyServers = Main.cg.getStringList("Negar-Servidores");
            String lobbyServer = Main.cg.getString("Hub-Server");
            ServerInfo sv = Main.plugin.getProxy().getServerInfo(lobbyServer);
            if (commands.contains(e.getMessage())) {
                if (!denyServers.contains(player.getServer().getInfo().getName())) {
                    if (!player.getServer().getInfo().getName().equals(lobbyServer)) {
                        player.connect(sv);
                        player.sendMessage(Main.cg.getString("Connected").replace("&", "§"));
                    } else {
                        player.sendMessage(Main.cg.getString("Already-Connected").replace("&", "§"));
                    }
                } else {
                    player.sendMessage(Main.cg.getString("Disabled-Command").replace("&", "§"));
                }

                e.setCancelled(true);
            }

            if (e.getMessage().equalsIgnoreCase("/bungehubreload")) {
                if (player.hasPermission("bungehub.reload")) {
                    Main.registerConfig();
                    player.sendMessage("§b[BungeHub] §aConfig reiniciada com sucesso!");
                } else {
                    player.sendMessage("§b[BungeHub] §cVc não tem permissão para utilizar esse comando!");
                }

                e.setCancelled(true);
            }
        }

    }
}
