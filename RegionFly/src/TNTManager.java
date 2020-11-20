import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;

public class TNTManager implements Listener{
    RegionFly plugin;
    public TNTManager(RegionFly plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.plugin = plugin;
    }
    @EventHandler(ignoreCancelled = true)
    public void onBlockDispense(BlockDispenseEvent e){ //BlockDispenseEvent
        Material itemtype = e.getItem().getType();
        //plugin.getLogger().info(itemtype.toString());
        if(itemtype== Material.TNT){
            e.setCancelled(true);
        }
    }

}
