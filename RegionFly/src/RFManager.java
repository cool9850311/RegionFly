import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;
import br.net.fabiozumbi12.RedProtect.Bukkit.Region;
import br.net.fabiozumbi12.RedProtect.Core.region.PlayerRegion;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.Set;

public class RFManager implements Listener{
    RegionFly plugin;

    public RFManager(RegionFly plugin){
        plugin.getServer().getPluginManager().registerEvents(this,plugin);
        this.plugin = plugin;
    }
    @EventHandler(ignoreCancelled = true)
    public void onPlayerMovement(PlayerMoveEvent e){
        setIfPlayerAllowFly(e.getPlayer(), e.getFrom(), e.getTo());
    }



    @EventHandler(ignoreCancelled = true)
    public void onPlayerTeleport(PlayerTeleportEvent e) { //fix tp bugs
        setIfPlayerAllowFly(e.getPlayer(), e.getFrom(), e.getTo());
    }

    private void setIfPlayerAllowFly(Player player, Location from, Location to) {
        Player p = player;
        if(!p.hasPermission("regionfly.rf")||p.getGameMode() == GameMode.SPECTATOR){
            return;
        }

        Location lfrom = from;
        Location lto = to;

        Region rfrom =  RedProtect.get().getAPI().getRegion(lfrom);
        Region rto = RedProtect.get().getAPI().getRegion(lto);
        if(rfrom==null && rto ==null){ //do noting when in wildness
            return;
        }
        else if(rfrom==null&& rto !=null){ //enter region
            if(getIsRegionMAL(rto,p)){
                p.setAllowFlight(true);
                p.sendRawMessage("開啟飛行模式");
            }else {
                return;
            }
        }
        else if (rfrom!=null&& rto ==null){ //exit region
            if(getIsRegionMAL(rfrom,p)){
                p.setAllowFlight(false);
                p.sendRawMessage("關閉飛行模式");
            }else {
                return;
            }
        }
        else if (rfrom.equals(rto)){ //do nothing still in the same region
            return;
        }
        else{ //both regions exist but not the same region
            if(getIsRegionMAL(rfrom,p)&& !getIsRegionMAL(rto,p)){
                p.setAllowFlight(false);
                p.sendRawMessage("關閉飛行模式");
            }
            else if (!getIsRegionMAL(rfrom,p)&& getIsRegionMAL(rto,p)){
                p.setAllowFlight(true);
                p.sendRawMessage("開啟飛行模式");
            }

        }
    }

    private boolean getIsRegionMAL(Region r,Player p){ //is member admin or leader
        Set<PlayerRegion> leaders = r.getLeaders();
        Set<PlayerRegion> admins = r.getAdmins();
        Set<PlayerRegion> members = r.getMembers();
        boolean isleader= false;
        boolean isadmin = false;
        boolean ismember= false;
        if (!leaders.isEmpty()){
            //String uuid= leaders.get(0).getUUID();
            //String player = leaders.get(0).getPlayerName();
            for (PlayerRegion pr:leaders) {
//                    plugin.getLogger().info(pr.getUUID());
//                    plugin.getLogger().info(p.getUniqueId().toString());
                if(pr.getUUID().equals(p.getUniqueId().toString())){
                    isleader =true;
                }
            }
        }
        if (!admins.isEmpty()){
            for (PlayerRegion pr:admins) {
                if(pr.getUUID().equals(p.getUniqueId().toString())){
                    isadmin =true;
                }
            }
        }
        if (!members.isEmpty()){
            for (PlayerRegion pr:members) {
                if(pr.getUUID().equals(p.getUniqueId().toString())){
                    ismember =true;
                }
            }
        }
        if(isleader||isadmin||ismember){
            return true;
        }
        else{
            return false;
        }
    }

}