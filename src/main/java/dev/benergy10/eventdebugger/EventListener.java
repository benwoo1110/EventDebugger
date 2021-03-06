package dev.benergy10.eventdebugger;

import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.minecrafttools.utils.ReflectHelper;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class EventListener {

    @Nullable
    public static Class<? extends Event> getClass(String classPath) {
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

    private final Plugin plugin;
    private final Class<? extends Event> eventClass;
    private final EventPriority priority;
    private final int coolDown;

    private Listener listener;
    private EventExecutor executor;
    private HandlerList handlerList;

    public EventListener(Plugin plugin, Class<? extends Event> eventClass, EventPriority priority, int coolDown) {
        this.plugin = plugin;
        this.eventClass = eventClass;
        this.priority = priority;
        this.coolDown = coolDown;
    }

    private EventExecutor createExecutor() {
        return (listener, event) -> {
            Logging.warning("%s WAS FIRED!", String.valueOf(this.eventClass));
        };
    }

    public boolean register() {
        this.listener = new Listener() { };
        this.executor = createExecutor();
        Method handlerListMethod = ReflectHelper.getMethod(eventClass, "getHandlerList");
        if (handlerListMethod == null) {
            Logging.severe("%s is not a valid event. Event does not have a getHandlerList method.");
            return false;
        }
        this.handlerList = ReflectHelper.invokeMethod(null, handlerListMethod);
        if (this.handlerList == null) {
            Logging.severe("%s is not a valid event. Event's HandleList is null.");
            return false;
        }
        Bukkit.getPluginManager().registerEvent(eventClass, listener, EventPriority.NORMAL, executor, this.plugin);
        return true;
    }

    public void unregister() {
        this.handlerList.unregister(this.listener);
    }

    public Class<? extends Event> getEventClass() {
        return eventClass;
    }

    public Listener getListener() {
        return listener;
    }

    public EventExecutor getExecutor() {
        return executor;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }
}
