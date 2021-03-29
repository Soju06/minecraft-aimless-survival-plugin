package sanstv.aimlesssurvival;

import java.util.Random;
import java.util.logging.Level;
import sanstv.aimlesssurvival.listener.PacketManipulationEvents;
import sanstv.aimlesssurvival.listener.PlayerChatEvents;
import sanstv.aimlesssurvival.listener.PlayerDeathEvents;
import sanstv.aimlesssurvival.listener.PlayerJoinEvents;
import sanstv.aimlesssurvival.listener.PlayerSignBookEvents;
import sanstv.aimlesssurvival.scheduler.PlayerList;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.NumberConversions;

public final class Main extends JavaPlugin
{
    public String tabString;

    public void onEnable()
    {
        if (!getConfig().getBoolean("AllowChatCommand")) // 명령어 사용 여부
            getServer().getPluginManager().registerEvents(new PlayerChatEvents(), this);

        if (getConfig().getBoolean("PacketManipulation")) {
            getServer().getPluginManager().registerEvents(new PacketManipulationEvents(this), this);
            getServer().getScheduler().runTaskTimer(this, new PlayerList(), 0L, 1L);
        }

        getServer().getPluginManager().registerEvents(new PlayerJoinEvents(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvents(this), this);
        getServer().getPluginManager().registerEvents(new PlayerSignBookEvents(this), this);

        getLogger().log(Level.INFO, "이벤트 핸들러 로드.");

        configInit();
        getLogger().log(Level.INFO, "설정 로드.");

        this.tabString = getConfig().getString("TabContext");

        getLogger().log(Level.INFO, "플레이어 리스트 설정.");

        getServer().setSpawnRadius(1);

        for (World world : getServer().getWorlds()) {
            WorldBorder worldBorder = world.getWorldBorder();

            worldBorder.setCenter(0.0D, 0.0D); // 월드보더 위치
            worldBorder.setSize(getConfig().getDouble("WorldSize")); // 월드보더 크기

            world.setSpawnLocation(world.getHighestBlockAt(0, 0).getLocation()); // 월드 스폰 위치
            if(getConfig().getBoolean("AllowDebugInfo"))
                world.setGameRule(GameRule.REDUCED_DEBUG_INFO, true); // 디버딩 제거
            world.setGameRule(GameRule.ANNOUNCE_ADVANCEMENTS, false); // 도전 과제
        }
        getLogger().log(Level.INFO, "세계 설정.");

        getLogger().log(Level.INFO, "플러그인 시작.");
    }

    public void configInit()
    {
        getConfig().options().copyDefaults(true);
        getConfig().addDefault("WorldSize", Integer.valueOf(6000));
        getConfig().addDefault("AllowDebugInfo", Boolean.valueOf(false));
        getConfig().addDefault("AllowChatCommand", Boolean.valueOf(false));
        getConfig().addDefault("PacketManipulation", Boolean.valueOf(true));
        getConfig().addDefault("ChatRange", Integer.valueOf(25));
        getConfig().addDefault("MOTD", "§6§l§kaaaaa§r   §l§aAimless §cSurvival §dServer   §b§l§kaaaaa§r");
        getConfig().addDefault("TabContext", "§6§l§kaaaaa§r   §l§aAimless §cSurvival §dServer   §b§l§kaaaaa§r\n§l§9By Soju06 §fWa sans!\n§l§d[  All users who have played  ]");
        saveConfig();
    }

    public void onDisable()
    {
        getLogger().log(Level.INFO, "플러그인 종료.");
    }

    public double getDistance(double x1, double z1, double x2, double z2)
    {
        return Math.sqrt(Math.pow(Math.abs(x2 - x1), 2.0D) + Math.pow(Math.abs(z2 - z1), 2.0D));
    }

    public Location randomLocation(Player player)
    {
        int hash = player.getName().hashCode();
        Random random = new Random(hash ^ 0x19940423);
        World world = player.getWorld();
        double size = getConfig().getDouble("WorldSize") / 2.0D - 100.0D;
        double x = getRandom(random, size);
        double z = getRandom(random, size);

        return world.getHighestBlockAt(NumberConversions.floor(x), NumberConversions.floor(z)).getLocation().add(0.5D, 1.0D, 0.5D);
    }

    private double getRandom(Random random, double size)
    {
        return random.nextDouble() * size - size;
    }
}