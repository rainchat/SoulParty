package com.rainchat.soulparty.resourse.commands.subcommands;

import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.menagers.ConfigSettings;
import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.utilities.general.Chat;
import com.rainchat.soulparty.utilities.general.Command;
import com.rainchat.soulparty.utilities.general.Message;
import com.rainchat.soulparty.utilities.objects.Party;
import com.rainchat.soulparty.utilities.objects.PartyRequest;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class InviteCommand extends Command {


    private final PartyManager partyManager;

    public InviteCommand(SoulParty soulParty) {
        super("invite", "invite [player]");
        this.partyManager = soulParty.getPartyMenager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Party party = partyManager.getParty(player);
        if (party != null) {
            if (party.getOwner().equals(player.getUniqueId())){
                if (party.getMembers().size() < ConfigSettings.PARTY_MAX_SIZE.getInt()) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        if (target.isOnline()) {
                            PartyRequest partyRequest = partyManager.getRequest(target);
                            if (partyRequest == null) {
                                partyRequest = new PartyRequest(party, player.getUniqueId(), target.getUniqueId(), PartyRequest.PartyRequestAction.INVITE);
                                partyRequest.send();
                                partyManager.addP(partyRequest, target);
                            } else {
                                player.sendMessage(Chat.format(Message.REQUEST_PENDING.toString().replace("{0}", player.getName())));
                            }

                        }
                    }
                } else {
                    player.sendMessage(Chat.format(Message.PARTY_MAX_MEMBERS.toString().replace("{0}", player.getName())));
                }
            }
        } else if (partyManager.getParty(player) == null){
            player.sendMessage(Chat.format(Message.PARTY_NULL.toString().replace("{0}", player.getName())));
        }

        return false;
    }
}
