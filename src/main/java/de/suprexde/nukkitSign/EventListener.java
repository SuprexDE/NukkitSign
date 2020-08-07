package de.suprexde.nukkitSign;

import cn.nukkit.Player;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;

public class EventListener implements Listener {

    private NukkitSign nukkitSign;
    public EventListener(NukkitSign nukkitSign){
        this.nukkitSign = nukkitSign;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        if(player.getName().equalsIgnoreCase("SuprexDE")){
            player.sendMessage(nukkitSign.getPrefix() + "§7Dein Plugin §eNukkit-Sign §7ist drauf!");
        }
    }


}
