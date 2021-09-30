package net.sushiclient.sushipvp;

import co.aikar.commands.BukkitCommandManager;
import net.sushiclient.sushipvp.commands.KillCommand;
import net.sushiclient.sushipvp.commands.SushiCommands;
import net.sushiclient.sushipvp.commands.ToggleDeathMessagesCommand;
import net.sushiclient.sushipvp.services.*;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;

public class SushiPlugin extends JavaPlugin implements Runnable {

    private final HashSet<Service> services = new HashSet<>();
    private SushiConfig sushiConfig;
    private int tick;

    private void register(Service service) {
        service.setPlugin(this);
        service.setConfig(sushiConfig);
        service.setEnabled(true);
        services.add(service);
    }

    @Override
    public void onEnable() {
        // set up configurations
        sushiConfig = new SushiConfig();
        FileConfiguration conf = getConfig();
        sushiConfig.load(conf);
        saveConfig();

        // set up commands
        BukkitCommandManager commands = new BukkitCommandManager(this);
        commands.registerCommand(new SushiCommands(this, sushiConfig));
        commands.registerCommand(new KillCommand());
        commands.registerCommand(new ToggleDeathMessagesCommand());

        // set up services
        register(new AntiVoidService());
        register(new AntiPhaseService());
        register(new ItemRemoveService());
        register(new BlockResetService());
        register(new NightVisionService());
        register(new QuitMessageDisableService());
        register(new AntiOffhandCrashService());
        register(new CombatTagService());
        register(new DeathMessageService());
        register(new NoFallDamageService());
        register(new SpawnLocationService());
        register(new FrameDispenserService());
        register(new ClearInventoryService());
        register(new InfinityAnvilService());
        register(new BedrockProtectService());
        register(new EnderChestDisableService());
        register(new AntiOffhandCrashService());
        register(new AntiHoleTpService());
        register(new VehicleMoveDisableService());
        register(new VehicleQuitDisableService());
        register(new CombatRestrictionEnforceService());
        register(new AntiKillSpamService());
        Bukkit.getScheduler().runTaskTimer(this, this, 1, 1);
    }

    @Override
    public void onDisable() {
        for (Service service : services) {
            service.setEnabled(false);
        }
    }

    @Override
    public void run() {
        tick++;
        for (Service service : services) {
            service.onTick(tick);
        }
    }
}
