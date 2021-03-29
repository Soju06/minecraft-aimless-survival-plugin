package sanstv.aimlesssurvival;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import java.util.*;


public class Emote
{
    public String[] Commands = new String[]
        {
            //
            "no", "안돼", "하지마",
            //
            "즐",
            //
            "눈물",
            "피눈물",
            "눈꼽",
            //
            "열받", "빡",
            //
            "퉤", "ㅗ", "h",
            //
            "아야", "상처", "아픔"
        };

    public void Run(String name, Player player)
    {
        var location = player.getLocation();
        var world = player.getWorld();
        double x = location.getX(), y = location.getY(), z = location.getZ();

        switch (name)
        {
            case "no":
            case "안돼":
            case "하지마":
                world.spawnParticle(Particle.BARRIER, x, y + 2.5, z, 0, 0.0, 0.0, 0.0, 0.0, null, true);
                world.playSound(location, Sound.BLOCK_ANVIL_LAND, 0.5F, 0.1F);
                break;
            case "즐":
                world.spawnParticle(Particle.NOTE, x, y + 2.0, z, 0, 0.0, 0.0, 0.0, 1.0, null, true);
                break;
            case "눈물":
            case "피눈물":
            case "눈꼽":
                Particle particle = Particle.FALLING_WATER;

                switch (name)
                {
                    case "눈물": particle = Particle.FALLING_WATER; break;
                    case "피눈물": particle = Particle.FALLING_LAVA; break;
                    case "눈꼽": particle = Particle.FALLING_HONEY; break;
                }

                for (int i = 0; i < 2; i++)
                {
                    var v = new Vector(-0.1 + i * 0.2, 0.1, 0.4);

                    v.rotateAroundX(Math.toRadians(location.getPitch())).rotateAroundY(Math.toRadians(-location.getYaw()));
                    world.spawnParticle( particle,
                            x + v.getX(), y + 1.3 + v.getY(), z + v.getZ(),
                            0, 0.0, 0.0, 0.0, 1.0,
                            null, true
                    );
                }
            break;
            case "열받":
            case "빡":
                world.spawnParticle( Particle.LAVA,
                        x, y + 2.0, z,
                        128, 0.0, 0.0, 0.0, 1.0,
                        null, true
                );
                world.playSound(location, Sound.ENTITY_GENERIC_EXPLODE, 1.0F, 1.2F);
            break;
            case "퉤":
            case "ㅗ":
            case "h":
                var v = location.getDirection();

                world.spawnParticle( Particle.SPIT,
                        x, y + 1.62, z, 0,
                        v.getX(), v.getY(), v.getZ(),
                        1.0, null, true
                );
                world.playSound(location, Sound.ENTITY_LLAMA_SPIT, 1.0F, 1.0F);
            break;
            case "아야":
            case "아픔":
            case "상처":
                world.spawnParticle(Particle.DAMAGE_INDICATOR,
                        x, y + 2.0, z,
                        16, 0.5, 0.5, 0.5, 0.0,
                        null, true
                );
            break;
        }

        player.sendMessage("§b§l" + name + "을(를) 시전했다.");
    }
}
