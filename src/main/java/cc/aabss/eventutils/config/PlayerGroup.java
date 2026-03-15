package cc.aabss.eventutils.config;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * A named group of players with optional nametag visibility.
 * Used for cycling visibility (hide players keybind) and group chat.
 */
public class PlayerGroup {
    @NotNull private String name;
    @NotNull private List<String> players;
    private boolean showNametags;

    public PlayerGroup(@NotNull String name, @NotNull List<String> players, boolean showNametags) {
        this.name = name;
        this.players = new ArrayList<>(players);
        this.showNametags = showNametags;
    }

    /** For Gson deserialization */
    public PlayerGroup() {
        this.name = "New Group";
        this.players = new ArrayList<>();
        this.showNametags = true;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    @NotNull
    public List<String> getPlayers() {
        return players;
    }

    public void setPlayers(@NotNull List<String> players) {
        this.players = new ArrayList<>(players);
    }

    public boolean isShowNametags() {
        return showNametags;
    }

    public void setShowNametags(boolean showNametags) {
        this.showNametags = showNametags;
    }

    /** Returns true if the given (lowercased) player name is in this group. */
    public boolean containsPlayer(@NotNull String nameLower) {
        return players.contains(nameLower);
    }
}
