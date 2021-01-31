package com.rainchat.soulparty.resourse.commands.subcommands;

import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.utilities.general.Chat;
import com.rainchat.soulparty.utilities.general.Command;
import com.rainchat.soulparty.utilities.general.Message;
import com.rainchat.soulparty.utilities.objects.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DisbandCommand extends Command {


    private final PartyManager partyManager;

    public DisbandCommand(SoulParty soulParty) {
        super("disband", "disband");
        this.partyManager = soulParty.getPartyMenager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Party party = partyManager.getParty(player);
        if (party != null) {
            if (party.getOwner().equals(player.getUniqueId())) {
                partyManager.remove(party);
                player.sendMessage(Chat.format(Message.DISBAND.toString().replace("{0}", player.getName())));
                for (UUID playerMemberUUID: party.getMembers()) {
                    Player playerMember = Bukkit.getPlayer(playerMemberUUID);

                    if (playerMember != null){
                        if (playerMember.equals(player)){

                        } else if (playerMember.isOnline()){
                            player.sendMessage(Chat.format(Message.DISBAND_OTHER.toString().replace("{0}", player.getName())));
                        }
                    }
                }
            } else {
                player.sendMessage(Chat.format(Message.PARTY_OWNER.toString().replace("{0}", player.getName())));
            }
        } else {
            player.sendMessage(Chat.format(Message.PARTY_NULL.toString().replace("{0}", player.getName())));
        }

        return false;
    }
}