package sanstv.aimlesssurvival.scheduler;

import com.comphenix.protocol.wrappers.EnumWrappers.NativeGameMode;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import sanstv.aimlesssurvival.packetwrapepr.WrapperPlayServerPlayerInfo;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class PlayerList implements Runnable
{
    private static boolean update = false;

    public static void update()
    {
        update = true;
    }

    public void run()
    {
        if (update)
        {
            update = false;
            updatePlayerList();
        }
    }

    private void updatePlayerList() {
        WrapperPlayServerPlayerInfo packet = new WrapperPlayServerPlayerInfo();
        List playerInfoData = new ArrayList();

        for (OfflinePlayer offlinePlayer : Bukkit.getOfflinePlayers())
        {
            WrappedGameProfile profile = (offlinePlayer instanceof Player) ? WrappedGameProfile.fromPlayer((Player)offlinePlayer) :
                    WrappedGameProfile.fromOfflinePlayer(offlinePlayer).withName(offlinePlayer.getName());

            playerInfoData.add(new PlayerInfoData(profile, 0, NativeGameMode.NOT_SET,
                    WrappedChatComponent.fromText(offlinePlayer.getName())));
        }

        packet.setAction(PlayerInfoAction.ADD_PLAYER);
        packet.setData(playerInfoData);

        for (var a = Bukkit.getOnlinePlayers().iterator(); ((Iterator)a).hasNext(); )
        {
            Player player = (Player)((Iterator)a).next();
            packet.sendPacket(player);
        }
    }
}