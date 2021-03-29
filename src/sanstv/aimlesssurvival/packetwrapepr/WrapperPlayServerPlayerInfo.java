package sanstv.aimlesssurvival.packetwrapepr;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.PacketType.Play.Server;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers.PlayerInfoAction;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import java.util.List;

public class WrapperPlayServerPlayerInfo extends AbstractPacket
{
    public static final PacketType TYPE = Server.PLAYER_INFO;

    public WrapperPlayServerPlayerInfo()
    {
        super(new PacketContainer(TYPE), TYPE);

        this.handle.getModifier().writeDefaults();
    }

    public void setAction(PlayerInfoAction value)
    {
        this.handle.getPlayerInfoAction().write(0, value);
    }

    public void setData(List<PlayerInfoData> value)
    {
        this.handle.getPlayerInfoDataLists().write(0, value);
    }
}