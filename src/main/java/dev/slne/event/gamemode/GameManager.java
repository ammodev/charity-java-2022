package dev.slne.event.gamemode;

import java.util.ArrayList;
import java.util.List;

import dev.slne.event.Main;
import dev.slne.event.gamemode.commands.GameModeCommand;
import dev.slne.event.gamemode.commands.HelpCommand;
import dev.slne.event.gamemode.gamemodes.buildGamemode.BuildGameMode;

public class GameManager {

    private List<EventGameMode> gameModes;
    private EventGameMode currentGameMode = null;

    public GameManager() {
        this.gameModes = new ArrayList<EventGameMode>();

        new GameModeCommand(Main.getInstance().getCommand("game"));
        new HelpCommand(Main.getInstance().getCommand("help"));

        this.gameModes.add(new BuildGameMode());
    }

    public void loadGamemode(String gameMode) {
        EventGameMode eventGameMode = getGameMode(gameMode);

        if (gameMode == null) {
            throw new RuntimeException("The gamemode " + gameMode + " does not exist");
        }

        this.loadGamemode(eventGameMode);
    }

    public void loadGamemode(EventGameMode eventGameMode) {
        this.clearGamemode();

        eventGameMode.onLoaded();
        eventGameMode.setLoaded(true);
        eventGameMode.setDisabled(false);
        eventGameMode.bukkitRegisterListeners();

        this.currentGameMode = eventGameMode;
    }

    public void enableGamemode(String gameMode) {
        EventGameMode eventGameMode = getGameMode(gameMode);

        if (gameMode == null) {
            throw new RuntimeException("The gamemode " + gameMode + " does not exist");
        }

        this.enableGamemode(eventGameMode);
    }

    public void enableGamemode(EventGameMode eventGameMode) {
        eventGameMode.onEnabled();
        eventGameMode.setEnabled(true);
    }

    public void disableGamemode(String gameMode) {
        EventGameMode eventGameMode = getGameMode(gameMode);

        if (gameMode == null) {
            throw new RuntimeException("The gamemode " + gameMode + " does not exist");
        }

        this.disableGamemode(eventGameMode);
    }

    public void disableGamemode(EventGameMode eventGameMode) {
        eventGameMode.onDisabled();
        eventGameMode.setDisabled(true);
        eventGameMode.setEnabled(false);
        eventGameMode.setLoaded(false);
        eventGameMode.setStarted(false);
    }

    public void startGamemode(String gameMode) {
        EventGameMode eventGameMode = getGameMode(gameMode);

        if (gameMode == null) {
            throw new RuntimeException("The gamemode " + gameMode + " does not exist");
        }

        this.startGamemode(eventGameMode);
    }

    public void startGamemode(EventGameMode eventGameMode) {
        eventGameMode.onStarted();
    }

    public void addGameMode(EventGameMode gameMode) {
        this.gameModes.add(gameMode);
    }

    public void removeGameMode(EventGameMode gameMode) {
        this.gameModes.remove(gameMode);
    }

    public EventGameMode getGameMode(String name) {
        for (EventGameMode gameMode : gameModes) {
            if (gameMode.getName().equalsIgnoreCase(name)) {
                return gameMode;
            }
        }

        return null;
    }

    public List<EventGameMode> getGameModes() {
        return gameModes;
    }

    public EventGameMode getCurrentGameMode() {
        return currentGameMode;
    }

    public void clearGamemode() {
        if (this.currentGameMode != null) {
            this.currentGameMode.onDisabled();
            this.currentGameMode.setDisabled(true);
            this.currentGameMode.bukkitUnregisterListeners();

            if (this.currentGameMode.getStartTask() != null && !this.currentGameMode.getStartTask().isCancelled()) {
                this.currentGameMode.getStartTask().cancel();
            }

            this.currentGameMode = null;
        }
    }

}
