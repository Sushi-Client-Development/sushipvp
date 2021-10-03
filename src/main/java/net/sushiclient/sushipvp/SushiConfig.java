package net.sushiclient.sushipvp;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SushiConfig {

    @Config("night-vision")
    private boolean nightVision = true;

    @Config("no-fall-damage")
    private boolean noFallDamage = true;

    @Config("frame-dispenser")
    private boolean frameDispenser = true;

    @Config("infinity-anvil")
    private boolean infinityAnvil = true;

    @Config("disable_ender_chest")
    private boolean disableEnderChest = true;

    @Config("enforce-combat-restriction")
    private boolean enforceCombatRestriction = true;

    @Config("protect-bedrock")
    private boolean protectBedrock = true;

    @Config("clear-inventory")
    private boolean clearInventory = true;

    @Config("disable-vehicle-move")
    private boolean disableVehicleMove = true;

    @Config("disable-vehicle-quit")
    private boolean disableVehicleQuit = true;

    @Config("block-reset-delay")
    private int blockResetDelay = 2000;

    @Config("anti-phase-delay")
    private int antiPhaseDelay = 100;

    @Config("kill-command-threshold")
    private int killCommandThreshold = 5000;

    @Config("offhand-threshold")
    private int offhandThreshold = 10;

    @Config("teleport-threshold")
    private double teleportThreshold = 3;

    @Config("remove-ticks")
    private int removeTicks = 1200;

    @Config("keep-radius")
    private int keepRadius = 10;

    @Config("combat.world")
    private String combatWorld = "world";

    @Config("combat.y")
    private int combatY = 15;

    @Config("build-y")
    private int buildY = 50;

    @Config("combat.ticks")
    private int combatTicks = 400;

    @Config("spawn.world")
    private String spawnWorld = "world";

    @Config("spawn.x")
    private double spawnX = 0.5;

    @Config("spawn.y")
    private double spawnY = 100;

    @Config("spawn.z")
    private double spawnZ = 0.5;

    @Config("blocked-messages")
    private List<String> blockedMessages = new ArrayList<>();

    public boolean isNoFallDamageEnabled() {
        return noFallDamage;
    }

    public boolean isNightVisionEnabled() {
        return nightVision;
    }

    public boolean isFrameDispenserEnabled() {
        return frameDispenser;
    }

    public boolean isInfinityAnvilEnabled() {
        return infinityAnvil;
    }

    public boolean isEnderChestEnabled() {
        return !disableEnderChest;
    }

    public boolean isCombatRestrictionEnforced() {
        return enforceCombatRestriction;
    }

    public boolean isClearInventoryEnabled() {
        return clearInventory;
    }

    public boolean isBedrockProtectionEnabled() {
        return protectBedrock;
    }

    public boolean isVehicleMoveEnabled() {
        return !disableVehicleMove;
    }

    public boolean isVehicleQuitEnabled() {
        return !disableVehicleQuit;
    }

    public int getBlockResetDelay() {
        return blockResetDelay;
    }

    public int getAntiPhaseDelay() {
        return antiPhaseDelay;
    }

    public int getKillCommandThreshold() {
        return killCommandThreshold;
    }

    public int getOffhandThreshold() {
        return offhandThreshold;
    }

    public double getTeleportThreshold() {
        return teleportThreshold;
    }

    public int getRemoveTicks() {
        return removeTicks;
    }

    public int getKeepRadius() {
        return keepRadius;
    }

    public Location getSpawnLocation() {
        World world = Bukkit.getWorld(spawnWorld);
        if (world == null) return null;
        return new Location(world, spawnX, spawnY, spawnZ);
    }

    public World getCombatWorld() {
        return Bukkit.getWorld(combatWorld);
    }

    public int getCombatY() {
        return combatY;
    }

    public int getBuildY() {
        return buildY;
    }

    public int getCombatTicks() {
        return combatTicks;
    }

    public List<String> getBlockedMessages() {
        return blockedMessages;
    }

    public void setBlockedMessages(List<String> messages) {
        blockedMessages = new ArrayList<>(messages);
    }

    public void load(FileConfiguration config) {
        try {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Config annotation = field.getAnnotation(Config.class);
                if (annotation == null) continue;
                String confName = annotation.value().isEmpty() ? field.getName() : annotation.value();
                if (config.contains(confName)) {
                    Class<?> decl = field.getDeclaringClass();
                    if (decl.equals(Double.class)) field.set(this, config.getDouble(config.getName()));
                    else field.set(this, config.get(confName));
                } else {
                    config.set(confName, field.get(this));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void save(FileConfiguration config) {
        try {
            for (Field field : getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Config annotation = field.getAnnotation(Config.class);
                if (annotation == null) continue;
                String confName = annotation.value().isEmpty() ? field.getName() : annotation.value();
                config.set(confName, field.get(this));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
