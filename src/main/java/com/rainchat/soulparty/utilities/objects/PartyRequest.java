package com.rainchat.soulparty.utilities.objects;

import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.utilities.general.Chat;
import com.rainchat.soulparty.utilities.general.Message;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.UUID;

public class PartyRequest {

    public enum PartyRequestAction {
        INVITE,
        KICK
    }

    private final Party party;
    private final UUID uuid, target;
    private final PartyRequestAction PartyRequestAction;

    public PartyRequest(Party party, UUID uuid, UUID target, PartyRequestAction villageRequestAction) {
        this.party = party;
        this.uuid = uuid;
        this.target = target;
        this.PartyRequestAction = villageRequestAction;
    }

    public void send() {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return;

        TextComponent accept = new TextComponent(Chat.color(Message.REQUEST_ACCEPT.toString()) + " ");
        accept.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Chat.color(Message.TOOLTIP.toString())).create()));
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party accept"));

        TextComponent deny = new TextComponent(Chat.color(Message.REQUEST_DENY.toString()));
        deny.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Chat.color(Message.TOOLTIP.toString())).create()));
        deny.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/party deny"));

        switch (PartyRequestAction) {
            case INVITE: {
                Player target = Bukkit.getPlayer(this.target);
                if (target == null) return;
                player.sendMessage(Chat.format(Message.REQUEST_INVITE.toString().replace("{0}", target.getName())));
                target.sendMessage(Chat.format(Message.REQUEST_INVITE_TARGET.toString().replace("{0}", party.getName())));
                target.spigot().sendMessage(accept, deny);
            }
            break;
            case KICK: {
                Player target = Bukkit.getPlayer(this.target);
                if (target != null) {
                    player.sendMessage(Chat.format(Message.REQUEST_KICK.toString().replace("{0}", target.getName())));
                    player.spigot().sendMessage(accept, deny);
                }
            }
            break;
        }
    }

    public void complete(PartyManager partyManager) {
        switch (PartyRequestAction) {
            case INVITE: {
                Player uuid = Bukkit.getPlayer(this.uuid);
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
                Player player = offlinePlayer.getPlayer();
                if (player != null && uuid != null) {
                    player.sendMessage(Chat.format(Message.REQUEST_JOIN_TARGET.toString().replace("{0}", party.getName())));
                    uuid.sendMessage(Chat.format(Message.REQUEST_JOIN.toString().replace("{0}", player.getName())));
                    partyManager.remove(partyManager.getParty(player));
                    party.addMember(player.getUniqueId());
                }
            }
            break;
            case KICK: {
                Player uuid = Bukkit.getPlayer(this.uuid);
                OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(target);
                Player player = offlinePlayer.getPlayer();
                if (player != null && uuid != null) {
                    party.removeMember(player.getUniqueId());
                    player.sendMessage(Chat.format(Message.REQUEST_KICK_TARGET.toString().replace("{0}", party.getName())));
                    uuid.sendMessage(Chat.format(Message.REQUEST_KICK_TARGET_SELF.toString().replace("{0}", player.getName())));
                }
            }
            break;
        }
    }

    public UUID getUniqueId() {
        return uuid;
    }

    public UUID getTarget() {
        return target;
    }
}
