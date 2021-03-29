package sanstv.aimlesssurvival.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import sanstv.aimlesssurvival.Emote;

public class PlayerChatEvents implements Listener
{
    private Emote emote = new Emote();

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event)
    {
        event.setCancelled(true);

        String f = event.getMessage();

        if(Arrays.stream(emote.Commands).anyMatch(f::equals)) // 이모트 명령어가 있다면
        {
            emote.Run(f, event.getPlayer());
            event.setCancelled(true);
        }
        // else
        // {
        //     event.getPlayer().sendMessage("§c§l채팅을 쓸 수 없다.");
        // }
    }
}