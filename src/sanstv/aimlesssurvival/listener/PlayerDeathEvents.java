package sanstv.aimlesssurvival.listener;

import sanstv.aimlesssurvival.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class PlayerDeathEvents implements Listener
{
    private final Main plugin;

    public PlayerDeathEvents(Main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerDeathEvent event) {
        event.setDeathMessage("§l§c누군가가 죽었다.");
        System.out.println(event.getEntity().getName() + "이(가) 사망하셨습니다.");
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if ((event.isBedSpawn()) || (event.isAnchorSpawn()))
            return;

        event.setRespawnLocation(this.plugin.randomLocation(player));
    }
}
