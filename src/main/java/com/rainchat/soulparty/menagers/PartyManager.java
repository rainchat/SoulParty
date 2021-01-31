package com.rainchat.soulparty.menagers;

import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.utilities.general.Manager;
import com.rainchat.soulparty.utilities.objects.Party;
import com.rainchat.soulparty.utilities.objects.PartyRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class PartyManager extends Manager<Party> {

    private final SoulParty plugin;
    private final HashMap<UUID, PartyRequest> partyRequestHashMap = new HashMap<>();

    public PartyManager(SoulParty plugin) {
        super("party", plugin);
        this.plugin = plugin;
    }

    public void addP(PartyRequest partyRequest, Player player) {
        partyRequestHashMap.put(player.getUniqueId(), partyRequest);
    }

    public void removeP(Player player) {
        partyRequestHashMap.remove(player.getUniqueId());
    }


    public PartyRequest getRequest(Player player) {
        if (partyRequestHashMap.containsKey(player.getUniqueId())) {
            return partyRequestHashMap.get(player.getUniqueId());
        }

        return null;
    }

    public Party getParty(Player player) {
        for (Party party : toSet()) {
            for (UUID member: party.getMembers()){
                Player player1 = Bukkit.getPlayer(member);
                if (player1 == player){
                    return party;
                }
            }
        }
        return null;
    }

    public Party getPartyByOwner(Player player) {
        for (Party party : toSet()) {
            Player player1 = Bukkit.getPlayer(party.getOwner());
            if (player1 == player) {
                return party;
            }
        }
        return null;
    }


}
