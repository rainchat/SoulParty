package com.rainchat.soulparty;

import com.google.gson.reflect.TypeToken;
import com.rainchat.soulparty.menagers.ConfigSettings;
import com.rainchat.soulparty.menagers.FileManager;
import com.rainchat.soulparty.menagers.PartyManager;
import com.rainchat.soulparty.resourse.commands.PartyCommand;
import com.rainchat.soulparty.resourse.commands.subcommands.*;
import com.rainchat.soulparty.resourse.listeners.PlayerListener;
import com.rainchat.soulparty.utilities.general.Message;
import com.rainchat.soulparty.utilities.objects.Party;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.Set;

public final class SoulParty extends JavaPlugin {


    private static SoulParty instance;
    private FileManager fileManager = FileManager.getInstance();
    private PartyManager partyManager;

    @Override
    public void onEnable() {

        setInstance(this);

        getLogger().info("Loading Party Data.");
        partyManager = new PartyManager(this);
        partyManager.load(new TypeToken<Set<Party>>() {
        }.getType());


        String languages = "/messages";
        fileManager.logInfo(true)
                .registerCustomFilesFolder("/messages")
                .registerDefaultGenerateFiles("ru_RU.yml", "/messages", languages)
                .registerDefaultGenerateFiles("en_EN.yml", "/messages", languages)
                .setup(this);


        registerMessages(ConfigSettings.LANGUAGE.getString());


        PartyCommand partyCommand = new PartyCommand(this);
        partyCommand.initialise(
                new AcceptCommand(this),
                new DenyCommand(this),
                new HelpCommand(partyCommand),
                new InviteCommand(this),
                new KickCommand(this),
                new LeaveCommand(this),
                new CreateCommand(this),
                new DisbandCommand(this),
                new SetOwnerCommand(this),
                new PanelComand(this)
        );
        getLogger().info("Registered " + partyCommand.getCommands().size() + " sub-command(s).");
        Objects.requireNonNull(getCommand(partyCommand.toString())).setExecutor(partyCommand);


        getServer().getPluginManager().registerEvents(new PlayerListener(this),this);
    }

    @Override
    public void onDisable() {
        partyManager.unload();
    }



    private void registerMessages(String name) {
        FileConfiguration file = fileManager.getFile(name).getFile();
        Message.setConfiguration(file);

        int index = 0;
        for (Message message : Message.values()) {
            if (message.getList() != null) {
                file.set(message.getPath(), message.getList());
            } else {
                index += 1;
                file.set(message.getPath(), message.getDef());
            }
        }
        FileManager.getInstance().saveFile(name);
        getLogger().info("Registered " + index + " message(s).");
    }

    public PartyManager getPartyMenager(){
        return partyManager;
    }

    public static void setInstance(SoulParty soulParty){
        instance = soulParty;
    }

    public static SoulParty getPluginInstance(){
        return instance;
    }

}
