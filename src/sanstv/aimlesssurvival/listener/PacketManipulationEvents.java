package sanstv.aimlesssurvival.listener;

import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import java.util.Calendar;
import sanstv.aimlesssurvival.Main;
import sanstv.aimlesssurvival.scheduler.PlayerList;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PacketManipulationEvents implements Listener
{
    private final Main plugin;

    public PacketManipulationEvents(Main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler(priority=EventPriority.LOWEST)
    public void onPaperServerListPing(PaperServerListPingEvent event)
    {
        Calendar c = Calendar.getInstance();

        event.getPlayerSample().clear();
        event.setNumPlayers(c.get(1) * 10000 + (c.get(2) + 1) * 100 + c.get(5));
        event.setMaxPlayers(c.get(10) * 10000 + c.get(12) * 100 + c.get(13));
        event.setMotd(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("MOTD")));
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        PlayerList.update();
        event.getPlayer().setPlayerListHeader(this.plugin.tabString);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        PlayerList.update();
    }
}