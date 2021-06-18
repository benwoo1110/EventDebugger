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

    public static final ConfigOption<List<EventModel>> LISTENERS = new ConfigOption.Builder<List<EventModel>>()
            .path("events")
            .comment("Put all the events you want to log here")
            .defaultValue(new ArrayList<EventModel>() {{
                EventModel event = new EventModel();
                event.eventClass = ConvertUtils.getEventClass("org.bukkit.event.player.PlayerJoinEvent");
                add(event);
            }})
            .handler(new ConfigOptionHandler<List<EventModel>, Object>() {
                @Override
                public Object serialize(List<EventModel> eventListeners) {
                    List<Map<String, Object>> eventDataList = new ArrayList<>();
                    for (EventModel event : eventListeners) {
                        Map<String, Object> eventData = new LinkedHashMap<>();
                        eventData.put("classPath", event.eventClass.getName());
                        eventData.put("priority", event.priority.name());
                        eventData.put("cooldown", event.coolDown);
                        eventData.put("ignoreCancelled", event.ignoreCancelled);
                        eventDataList.add(eventData);
                    }
                    return eventDataList;
                }

                @Override
                public List<EventModel> deserialize(Object obj) {
                    List<Map<String, Object>> eventDataList = (List<Map<String, Object>>) obj;
                    List<EventModel> eventModelList = new ArrayList<>();
                    for (Map<String, Object> eventData : eventDataList) {
                        EventModel event = new EventModel();
                        event.eventClass = ConvertUtils.getEventClass(String.valueOf(eventData.get("classPath")));
                        event.priority = EventPriority.valueOf(String.valueOf(eventData.get("priority")).toUpperCase());
                        event.coolDown = (int) eventData.get("cooldown");
                        event.ignoreCancelled = (boolean) eventData.get("ignoreCancelled");
                        eventModelList.add(event);
                    }
                    return eventModelList;
                }
            })
            .register(EventOptions::register);
}
