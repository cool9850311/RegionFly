import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginManager implements Listener {
    RegionFly plugin;
    public LoginManager(RegionFly plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerLoginEvent(PlayerLoginEvent event) {
        // Checking if the reason we are being kicked is a full server
        if (event.getResult() == PlayerLoginEvent.Result.KICK_FULL) {
            // Checking if the player has the specified permission node
            if (event.getPlayer().hasPermission("regionfly.bypass.loginlimit")){
                event.allow();
            }
                // If the condition above is true, we execute the following code, that allows the player on the server

        }

    }
}
