package com.rainchat.soulparty.resourse.commands.subcommands;

import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.utilities.general.Chat;
import com.rainchat.soulparty.utilities.general.Command;
import com.rainchat.soulparty.utilities.general.Message;
import com.rainchat.soulparty.utilities.objects.Party;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SetOwnerCommand extends Command {


    private final PartyManager partyManager;

    public SetOwnerCommand(SoulParty soulParty) {
        super("setowner", "setowner [player]");
        this.partyManager = soulParty.getPartyMenager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Party party = partyManager.getParty(player);
        if (party != null) {
            if (party.getOwner().equals(player.getUniqueId())) {
                Player target = Bukkit.getPlayer(args[1]);
                if (target != null){
                    if (target.isOnline()){
                        if (!target.equals(player)){
                            party.setName(target.getName());
                            party.setOwner(target.getUniqueId());
                            player.sendMessage(Chat.format(Message.PARTY_SET_OWNER.toString().replace("{0}", target.getName())));
                        } else {
                            player.sendMessage(Chat.format(Message.PARTY_ALREADY_OWNER.toString().replace("{0}", player.getName())));
                        }
                    } else {
                        player.sendMessage(Chat.format(Message.PLAYER_OFFLINE.toString().replace("{0}", player.getName())));
                    }
                } else {
                    player.sendMessage(Chat.format(Message.PLAYER_NULL.toString().replace("{0}", player.getName())));
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