package com.rainchat.soulparty.resourse.commands;


import com.rainchat.soulparty.SoulParty;
import com.rainchat.soulparty.resourse.commands.subcommands.PanelComand;
import com.rainchat.soulparty.utilities.general.Chat;
import com.rainchat.soulparty.utilities.general.Command;
import com.rainchat.soulparty.utilities.general.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.*;

public class PartyCommand extends Command implements TabCompleter {

    private final SoulParty soulParty;
    private Set<Command> commands;

    public PartyCommand(SoulParty soulParty) {
        super("party", " ");
        this.soulParty = soulParty;
        this.commands = new HashSet<>();
    }

    public boolean run(Player player, String[] args) {
            if (args.length > 0) {
                for (Command command : commands) {
                    if (args[0].equalsIgnoreCase(command.toString())) {
                        if (player.hasPermission("party.command." + command.toString())) {
                            command.run(player, args);
                        } else {
                            player.sendMessage(Chat.format(Message.NO_COMMAND_PERMISSION.toString()));
                        }
                        break;
                    }
                }
            } else {
                if (player.hasPermission("party.command." + "panel")) {
                    return new PanelComand(soulParty).run(player, args);
                } else {
                    player.sendMessage(Chat.format(Message.NO_COMMAND_PERMISSION.toString()));
                }
            }
        return false;
    }

    public void initialise(Command... commands) {
        this.commands.addAll(Arrays.asList(commands));
    }

    public Set<Command> getCommands() {
        return Collections.unmodifiableSet(commands);
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String s, String[] strings) {
        List<String> completions = new ArrayList<>();
        List<String> list = new ArrayList<>();

        if (strings.length == 1) {
            for (Command subCommand : commands) {
                if (commandSender.hasPermission("party.command." + subCommand.toString())) {
                    list.add(subCommand.toString());

                }
            }
            StringUtil.copyPartialMatches(strings[0], list, completions);
        } else if (strings.length == 2) {
            for (Player player: Bukkit.getOnlinePlayers()){
                list.add(player.getName());
            }
            StringUtil.copyPartialMatches(strings[1], list, completions);
        }

        Collections.sort(completions);
        return completions;
    }
}
