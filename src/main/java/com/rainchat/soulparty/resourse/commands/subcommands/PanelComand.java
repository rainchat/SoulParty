package com.rainchat.soulparty.resourse.commands.subcommands;

import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.resourse.menus.PartyMenu;
import com.rainchat.soulparty.utilities.general.Chat;
import com.rainchat.soulparty.utilities.general.Command;
import com.rainchat.soulparty.utilities.general.Message;
import com.rainchat.soulparty.utilities.objects.Party;
import org.bukkit.entity.Player;

public class PanelComand extends Command {


    private final PartyManager partyManager;

    public PanelComand(SoulParty soulParty) {
        super("panel", "panel");
        this.partyManager = soulParty.getPartyMenager();
    }

    @Override
    public boolean run(Player player, String[] args) {
        Party party = partyManager.getParty(player);
        if (party != null){
            PartyMenu partyMenu = new PartyMenu(party,player);
            partyMenu.createMenu();
            partyMenu.openMenu();
        } else {
            player.sendMessage(Chat.format(Message.PARTY_NULL.toString().replace("{0}", player.getName())));
        }

        return false;
    }
}
