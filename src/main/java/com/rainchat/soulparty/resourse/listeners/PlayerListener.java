package com.rainchat.soulparty.resourse.listeners;

import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.utilities.objects.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {

    private final PartyManager partyManager;

    public PlayerListener(SoulParty partyManager) {
        this.partyManager = partyManager.getPartyMenager();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){

    }


    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if (e.getEntity() instanceof Player){
            Party party = SoulParty.getPluginInstance().getPartyMenager().getParty(((Player) e.getEntity()).getPlayer());
            for (UUID playerUUID: party.getMembers()){
                Player player = Bukkit.getPlayer(playerUUID);
                if (player != ((Player) e.getEntity()).getPlayer()){
                    assert player != null;
                    player.damage(e.getDamage());
                }
            }
        }
    }
}
