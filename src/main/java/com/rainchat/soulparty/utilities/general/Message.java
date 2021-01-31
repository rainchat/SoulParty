package com.rainchat.soulparty.utilities.general;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Arrays;
import java.util.List;

public enum Message {
    DISBAND("Messages.disband", "&7Oh no! &a&l{0} &7the party was disbanded ..."),
    DISBAND_OTHER("Messages.disband-other", "&7The party &a&l{0} &7was disbanded, it happens..."),
    HELP("Messages.help", "Type &b/party help [page] &7to look at the help pages."),
    MENU_PARTY_GUITITLE("Menu.party.guititle", "{0} Party"),
    MENU_INFORMATION_TITLE("Menu.party.information.title", "&e&lInformation"),
    MENU_INFORMATION_LORE("Menu.party.information.lore", Arrays.asList(
            "",
            "&7Owner: &b{owner}",
            "&7Members: &e{members}"
    )),
    NO_COMMAND_PERMISSION("Messages.no-command-permission", "You do not have permissions for that command."),
    NO_PERMISSION("Messages.no-permission", "You do not have permissions for &b{0}&7."),
    PAGE_FORMAT("Messages.page-format", "&b{0}. &7{1}"),
    PAGE_HELP("Messages.page-help", "&3&lHelp: &7[{0}/{1}]"),
    PAGE_LIMIT("Messages.page-limit", "There are only &b{0} &7help pages."),
    PAGE_NEXT("Messages.page-next", "Type &b/party help {0} &7for the next page."),
    PAGE_PREVIOUS("Messages.page-previous", "Type &b/party help {0} &7for the previous page."),
    PLAYER_OFFLINE("Messages.player-offline", "The player &b{0} &7does not seem to be online."),
    PLAYER_NULL("Messages.player-null", "The player named &b{0} does not exist ."),
    PREFIX("Messages.prefix", "&3&lParty: &7"),
    REQUEST_ACCEPT("Messages.request-accept", " &7[&aAccept&7] "),
    REQUEST_DENY("Messages.request-deny", " &7[&cDeny&7] "),
    REQUEST_DENIED("Messages.request-denied", "You have decided to deny the request."),
    REQUEST_DISBAND("Messages.request-disband", "Are you sure you want to disband the party? Members of your party may not be happy!"),
    REQUEST_INVITE("Messages.request-invite", "You have sent an invite request to &b{0}&7."),
    REQUEST_INVITE_SELF("Messages.request-invite-self", "You can't invite yourself."),
    REQUEST_INVITE_TARGET("Messages.request-invite-target", "You have been invited to join &b{0}&7. Do you wish to join?"),
    REQUEST_INVITE_TARGET_NOT_NULL("Messages.request-invite-target-not-null", "The player &b{0} &7already belongs to a party."),
    REQUEST_JOIN("Messages.request-join", "&b{0} &7has joined the party."),
    REQUEST_JOIN_TARGET("Messages.request-join-target", "&7You have joined the &b{0} &7party."),
    REQUEST_KICK("Messages.request-kick", "Are you sure you want to kick &b{0} &7 from your party?"),
    REQUEST_KICK_SELF("Messages.request-kick-self", "You can't kick yourself from the party."),
    REQUEST_KICK_TARGET("Messages.request-kick-target", "You have been kicked from the &b{0} &7party."),
    REQUEST_KICK_TARGET_SELF("Messages.request-kick-target-self", "You have been kick &b{0} &7from your party."),
    REQUEST_NULL("Messages.request-null", "You do not have any pending requests."),
    REQUEST_PENDING("Messages.request-pending", "You already have a pending request."),
    TOOLTIP("Messages.tooltip", "&7Click to select."),
    PARTY_SET_OWNER("Messages.party-set-owner", "You have set the new party owner to &b{0}&7."),
    PARTY_ALREADY_OWNER("Messages.party-already-owner", "You can't set the new owner to yourself."),
    PARTY_CREATE("Messages.party-create", "You have established a new party named &b{0}&7."),
    PARTY_LEAVE("Messages.party-leave", "You have left the &7party."),
    PARTY_LEAVE_OWNER("Messages.party-leave-owner", "You can't leave your current party because you are the owner."),
    PARTY_MAX_MEMBERS("Messages.party-max-claims", "Group limit exceeded &e{0} &7players ."),
    PARTY_MEMBER_NULL("Messages.party-member-null", "&b{0} &7this player is not from your party."),
    PARTY_NOT_NULL("Messages.party-not-null", "You are already in the party."),
    PARTY_NULL("Messages.party-null", "You don't have a party."),
    PARTY_OWNER("Messages.party-owner", "You must be the owner of the party to do that action.");

    private String path, def;
    private List<String> list;
    private static FileConfiguration configuration;

    Message(String path, String def) {
        this.path = path;
        this.def = def;
    }

    Message(String path, List<String> list) {
        this.path = path;
        this.list = list;
    }

    public String getDef() {
        return configuration.getString(path, def);
    }

    @Override
    public String toString() {
        return Chat.color(configuration.getString(path, def));
    }

    public List<String> toList() {
        return configuration.getStringList(path);
    }

    public static void setConfiguration(FileConfiguration configuration) {
        Message.configuration = configuration;
    }

    public String getPath() {
        return path;
    }

    public List<String> getList() {
        return list;
    }
}
