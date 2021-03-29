package sanstv.aimlesssurvival.listener;

import sanstv.aimlesssurvival.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinEvents implements Listener
{
    private final Main plugin;

    public PlayerJoinEvents(Main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        if (player.hasPlayedBefore())
            event.setJoinMessage(null);
        else
        {
            player.teleport(this.plugin.randomLocation(player));
            player.setCompassTarget(player.getWorld().getSpawnLocation());
            event.setJoinMessage("§c§l누군가가 서버에 처음으로 접속했다.");
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        event.setQuitMessage(null);
    }
}