package dev.benergy10.eventdebugger;

import dev.benergy10.minecrafttools.configs.ConfigOption;
import dev.benergy10.minecrafttools.configs.ConfigOptionHandler;
import org.bukkit.event.EventPriority;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class EventOptions {

    private static final List<ConfigOption<?>> OPTIONS = new ArrayList<>();

    private static void register(ConfigOption<?> option) {
        OPTIONS.add(option);
    }

    public static List<ConfigOption<?>> getOptions() {
        return OPTIONS;
    }

    public static String[] getHeader() {
        return new String[] {
                " ___             _   ___      _                           ",
                "| __|_ _____ _ _| |_|   \\ ___| |__ _  _ __ _ __ _ ___ _ _ ",
                "| _|\\ V / -_) ' \\  _| |) / -_) '_ \\ || / _` / _` / -_) '_|",
                "|___|\\_/\\___|_||_\\__|___/\\___|_.__/\\_,_\\__, \\__, \\___|_|",
                "                                       |___/|___/         ",
                "",
                ""
        };
    }

    public static final ConfigOption<List<EventListener>> LISTENERS = new ConfigOption.Builder<List<EventListener>>()
            .path("events")
            .comment("Stuff")
            .defaultValue(new ArrayList<EventListener>() {{
                add(new EventListener(EventListener.getClass("org.bukkit.event.player.PlayerJoinEvent")));
            }})
            .handler(new ConfigOptionHandler<List<EventListener>, Object>() {
                @Override
                public Object serialize(List<EventListener> eventListeners) {
                    List<Map<String, Object>> eventDataList = new ArrayList<>();
                    for (EventListener event : eventListeners) {
                        Map<String, Object> eventData = new LinkedHashMap<>();
                        eventData.put("classPath", event.getEventClass().getName());
                        eventData.put("priority", event.getPriority().name());
                        eventData.put("cooldown", event.getCoolDown());
                        eventData.put("ignoreCancelled", event.isIgnoreCancelled());
                        eventDataList.add(eventData);
                    }
                    return eventDataList;
                }

                @Override
                public List<EventListener> deserialize(Object obj) {
                    List<Map<String, Object>> eventDataList = (List<Map<String, Object>>) obj;
                    List<EventListener> eventListeners = new ArrayList<>();
                    for (Map<String, Object> eventData : eventDataList) {
                        eventListeners.add(new EventListener(
                                EventListener.getClass(String.valueOf(eventData.get("classPath"))),
                                EventPriority.valueOf(String.valueOf(eventData.get("priority")).toUpperCase()),
                                (int) eventData.get("cooldown"),
                                (boolean) eventData.get("ignoreCancelled")
                        ));
                    }
                    return eventListeners;
                }
            })
            .register(EventOptions::register);
}
