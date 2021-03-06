package dev.benergy10.eventdebugger;

import dev.benergy10.minecrafttools.MinecraftPlugin;
import dev.benergy10.minecrafttools.utils.Logging;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Map;

public final class EventDebugger extends MinecraftPlugin {

    private static final String DEFAULT_PRIORITY = "NORMAL";
    private static final int DEFAULT_COOLDOWN = 10;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        FileConfiguration config = this.getConfig();

        ConfigurationSection defaultSection = config.getConfigurationSection("defaults");
        EventPriority defaultEventPriority = EventPriority.valueOf(defaultSection.getString("priority", DEFAULT_PRIORITY).toUpperCase());
        int defaultCoolDown = defaultSection.getInt("cooldown", DEFAULT_COOLDOWN);

        List<Map<?, ?>> events = config.getMapList("events");
        Logging.info(String.valueOf(events));
        for (Map<?, ?> event : events) {
            String classPath = String.valueOf(event.get("classPath"));
            Class<? extends Event> eventClass = EventListener.getClass(classPath);
            if (eventClass == null) {
                continue;
            }
            new EventListener(this, eventClass, defaultEventPriority, defaultCoolDown).register();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
