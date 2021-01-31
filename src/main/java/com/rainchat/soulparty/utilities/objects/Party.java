package com.rainchat.soulparty.utilities.objects;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Party {

    private String name;
    private UUID owner;
    private final Set<UUID> members;

    public Party(String name, UUID owner) {
        this.name = name;
        this.owner = owner;

        this.members = new HashSet<>();
    }

    public void setOwner(UUID owner){
        this.owner = owner;
    }

    public void setName(String name){
        this.name = name;
    }

    public void removeMember(UUID uuid){
        members.remove(uuid);
    }

    public void addMember(UUID uuid){
        members.add(uuid);
    }

    public UUID getOwner(){
        return owner;
    }

    public String getName(){
        return name;
    }


    public Set<UUID> getMembers(){
        return members;
    }

}
