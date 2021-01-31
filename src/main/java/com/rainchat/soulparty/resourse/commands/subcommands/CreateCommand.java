package com.rainchat.soulparty.resourse.commands.subcommands;

import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.utilities.general.Chat;
import com.rainchat.soulparty.utilities.general.Command;
import com.rainchat.soulparty.utilities.general.Message;
import com.rainchat.soulparty.utilities.objects.Party;
import org.bukkit.entity.Player;

public class CreateCommand extends Command {


    private final PartyManager partyManager;

    public CreateCommand(SoulParty soulParty) {
        super("create", "create [player]");
        this.partyManager = soulParty.getPartyMenager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        if (partyManager.getParty(player) == null) {
            Party party = new Party(player.getName(), player.getUniqueId());
            party.addMember(player.getUniqueId());
            partyManager.add(party);
            player.sendMessage(Chat.format(Message.PARTY_CREATE.toString().replace("{0}", player.getName())));
        } else {
            player.sendMessage(Chat.format(Message.PARTY_NOT_NULL.toString().replace("{0}", player.getName())));
        }

        return false;
    }
}
