package dev.slne.event.utils;

import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Team;

public class TeamUtils {

    private Team adminTeam;
    private Team playerTeam;

    public TeamUtils() {
        this.adminTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("admin");
        this.playerTeam = Bukkit.getScoreboardManager().getMainScoreboard().getTeam("player");

        if (this.adminTeam == null) {
            this.adminTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("admin");
        }

        if (this.playerTeam == null) {
            this.playerTeam = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam("player");
        }
    }

    public Team getAdminTeam() {
        return adminTeam;
    }

    public Team getPlayerTeam() {
        return playerTeam;
    }

}
