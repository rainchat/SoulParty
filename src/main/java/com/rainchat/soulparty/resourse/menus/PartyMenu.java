package com.rainchat.soulparty.resourse.menus;

import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.utilities.general.Item;
import com.rainchat.soulparty.utilities.general.Message;
import com.rainchat.soulparty.utilities.objects.Party;
import de.themoep.inventorygui.InventoryGui;
import de.themoep.inventorygui.StaticGuiElement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PartyMenu {

    public Party party;
    public Player player;
    public SoulParty plugin;
    public InventoryGui gui;

    String[] guiSetup = {
            "  s  ",
    };

    public PartyMenu(Party party, Player player){
        this.party = party;
        this.player = player;
        this.plugin = SoulParty.getPluginInstance();
        String guiTitle = Message.MENU_PARTY_GUITITLE.toString().replace("{0}", party.getName());
        this.gui = new InventoryGui(plugin, player, guiTitle , guiSetup);
    }

    public void createMenu(){
        gui.setFiller(new ItemStack(Material.GRAY_STAINED_GLASS_PANE, 1));
        gui.addElement(new StaticGuiElement('s',
                information(),
                1, // Display a number as the item count
                click -> {
                    return true; // returning false will not cancel the initial click event to the gui
                }
        ));
    }

    public void openMenu(){
        gui.show(player);
    }


    private ItemStack information() {

        List<String> lore = Message.MENU_INFORMATION_LORE.toList();
        List<String> updated = new ArrayList<>();

        for (String string : lore) {
            string = string.replace("{owner}", Objects.requireNonNull(Bukkit.getOfflinePlayer(party.getOwner()).getName()));
            string = string.replace("{members}", String.valueOf(party.getMembers().size()));
            updated.add(string);
        }

        return new Item()
                .material(Material.WRITABLE_BOOK)
                .name(Message.MENU_INFORMATION_TITLE.toString())
                .lore(updated)
                .build();
    }
}
