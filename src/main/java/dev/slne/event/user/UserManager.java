package dev.slne.event.user;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;

public class UserManager {

    private Map<Player, User> users;

    public void ignite() {
        this.users = new HashMap<Player, User>();
    }

    public Map<Player, User> getUsersMap() {
        return users;
    }

    public List<User> getUsers() {
        return this.users.values().stream().toList();
    }

    public void addUser(Player player) {
        if (hasUser(player)) {
            return;
        }

        this.users.put(player, new User(player));
    }

    public void removeUser(Player player) {
        if (!hasUser(player)) {
            return;
        }

        this.users.remove(player);
    }

    public User getUser(Player player) {
        if (!hasUser(player)) {
            addUser(player);
        }

        return this.users.get(player);
    }

    public boolean hasUser(Player player) {
        return this.users.containsKey(player);
    }

}
