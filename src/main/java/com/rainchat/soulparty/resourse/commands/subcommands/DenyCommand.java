package com.rainchat.soulparty.resourse.commands.subcommands;


import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.utilities.general.Chat;
import com.rainchat.soulparty.utilities.general.Command;
import com.rainchat.soulparty.utilities.general.Message;
import com.rainchat.soulparty.utilities.objects.PartyRequest;
import org.bukkit.entity.Player;

public class DenyCommand extends Command {


    private final PartyManager partyManager;


    public DenyCommand(SoulParty soulParty) {
        super("deny", "deny");
        this.partyManager = soulParty.getPartyMenager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        PartyRequest partyRequest = partyManager.getRequest(player);
        if (partyRequest == null) {
            player.sendMessage(Chat.format(Message.REQUEST_NULL.toString()));
        } else {
            player.sendMessage(Chat.format(Message.REQUEST_DENIED.toString()));
            partyManager.removeP(player);
        }
        return false;
    }
}
