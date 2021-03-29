package sanstv.aimlesssurvival.listener;

import sanstv.aimlesssurvival.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;

public class PlayerSignBookEvents implements Listener
{
    private final Main plugin;

    public PlayerSignBookEvents(Main plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerEditBook(PlayerEditBookEvent event)
    {
        Player player = event.getPlayer();
        Location loc = player.getLocation();

        double chatRange = plugin.getConfig().getDouble("ChatRange");

        if (this.plugin.getDistance(0.0D, 0.0D, loc.getX(), loc.getZ()) > chatRange)
        {
            sendInfoMessage(player);

            BookMeta bookMeta = event.getNewBookMeta();

            if ((bookMeta.hasTitle()) && (bookMeta.getTitle() != null))
                bookMeta.setTitle(removeLang(bookMeta.getTitle()));

            for (int page = 1; page <= bookMeta.getPageCount(); page++)
                bookMeta.setPage(page, removeLang(bookMeta.getPage(page)));

            event.setNewBookMeta(bookMeta);
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent event)
    {
        Player player = event.getPlayer();
        Location loc = event.getBlock().getLocation();

        double chatRange = plugin.getConfig().getDouble("ChatRange");
        if (this.plugin.getDistance(0.0D, 0.0D, loc.getX(), loc.getZ()) > chatRange)
        {
            sendInfoMessage(player);

            if (event.getLines().length >= 1)
                for (int line = 0; line < event.getLines().length; line++)
                    if (event.getLine(line) != null)
                        event.setLine(line, removeLang(event.getLine(line)));
        }
    }

    public  void sendInfoMessage(Player player)
    {
        player.sendMessage("§l§c월드 중심에서만 글자를 쓸 수 있다.");
    }

    public String removeLang(String text)
    {
        return text.replaceAll("([a-zA-Z])|([ㄱ-힣])", "와 샌즈!");
    }
}