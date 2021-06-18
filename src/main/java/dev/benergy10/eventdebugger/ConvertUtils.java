package dev.benergy10.eventdebugger;

import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.minecrafttools.utils.ReflectHelper;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ConvertUtils {

    @Nullable
    public static Class<? extends Event> getEventClass(String classPath) {
        Class<?> eventClass = ReflectHelper.getClass(classPath);
        if (eventClass == null) {
            Logging.severe("%s is not a valid class path.", classPath);
            return null;
        }
        if (!Event.class.isAssignableFrom(eventClass)) {
            Logging.severe("%s is not an event. It does not extend the Event class.", classPath);
            return null;
        }
        return (Class<? extends Event>) eventClass;
    }
}
