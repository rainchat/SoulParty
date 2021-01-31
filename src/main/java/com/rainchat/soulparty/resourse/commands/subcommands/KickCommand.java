package com.rainchat.soulparty.resourse.commands.subcommands;

import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.utilities.general.Chat;
import com.rainchat.soulparty.utilities.general.Command;
import com.rainchat.soulparty.utilities.general.Message;
import com.rainchat.soulparty.utilities.objects.Party;
import com.rainchat.soulparty.utilities.objects.PartyRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class KickCommand extends Command {


    private final PartyManager partyManager;

    public KickCommand(SoulParty soulParty) {
        super("kick", "kick [player]");
        this.partyManager = soulParty.getPartyMenager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Party party = partyManager.getParty(player);
        if (party != null) {
            Player target = Bukkit.getPlayer(args[1]);
            if (target != null) {
                if (party.getOwner().equals(player.getUniqueId())) {
                        if (party.getMembers().contains(target.getUniqueId())) {
                            if (!party.getOwner().equals(target.getUniqueId())) {
                                PartyRequest partyRequest = partyManager.getRequest(player);
                                if (partyRequest == null) {
                                    partyRequest = new PartyRequest(party, player.getUniqueId(), target.getUniqueId(), PartyRequest.PartyRequestAction.KICK);
                                    partyRequest.send();
                                    partyManager.addP(partyRequest, player);
                                } else {
                                    player.sendMessage(Chat.format(Message.REQUEST_PENDING.toString().replace("{0}", player.getName())));
                                }
                            } else {
                                player.sendMessage(Chat.format(Message.REQUEST_KICK_SELF.toString().replace("{0}", player.getName())));
                            }
                        } else {
                            player.sendMessage(Chat.format(Message.PARTY_MEMBER_NULL.toString().replace("{0}", target.getName())));
                        }
                }
            } else {
                player.sendMessage(Chat.format(Message.PLAYER_NULL.toString().replace("{0}", args[1])));
            }
        } else if (partyManager.getParty(player) == null) {
            player.sendMessage(Chat.format(Message.PARTY_NULL.toString().replace("{0}", player.getName())));
        }

        return false;
    }
}
