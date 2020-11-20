import br.net.fabiozumbi12.RedProtect.Bukkit.RedProtect;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public class RegionFly extends JavaPlugin {
    public static boolean redProtect;

    @Override
    public void onEnable(){
        super.onEnable();
        redProtect = checkRP();
        new TNTManager(this); //manage dispense tnt
        new LoginManager(this); //manage priority login
        new RFManager(this);//manage region fly
    }
    private boolean checkRP(){
        Plugin pRP = Bukkit.getPluginManager().getPlugin("RedProtect");
        if (pRP != null && pRP.isEnabled()){
            return true;
        }
        return false;
    }
}
