package dev.benergy10.eventdebugger;

import dev.benergy10.minecrafttools.utils.Logging;
import dev.benergy10.minecrafttools.utils.ReflectHelper;
import org.bukkit.Bukkit;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Method;

public class EventListener {

    private EventModel event;
    private Listener listener;
    private EventExecutor executor;
    private HandlerList handlerList;

    public EventListener(EventModel event) {
        this.event = event;
    }

    public boolean register(Plugin plugin) {
        this.listener = new Listener() { };
        this.executor = createExecutor();
        Method handlerListMethod = ReflectHelper.getMethod(this.event.eventClass, "getHandlerList");
        if (handlerListMethod == null) {
            Logging.severe("%s is not a valid event. Event does not have a getHandlerList method.");
            return false;
        }
        this.handlerList = ReflectHelper.invokeMethod(null, handlerListMethod);
        if (this.handlerList == null) {
            Logging.severe("%s is not a valid event. Event's HandleList is null.");
            return false;
        }
        Bukkit.getPluginManager().registerEvent(this.event.eventClass, listener, EventPriority.NORMAL, executor, plugin, this.event.ignoreCancelled);
        return true;
    }

    private EventExecutor createExecutor() {
        return (listener, event) -> {
            Logging.warning("%s WAS FIRED:", this.event.eventClass.getName());
            if (event instanceof Cancellable) {
                Cancellable cancellableEvent = (Cancellable) event;
                Logging.warning("  Cancel state: %s", cancellableEvent.isCancelled());
            }
        };
    }

    public void unregister() {
        this.handlerList.unregister(this.listener);
    }

    public EventModel getEvent() {
        return event;
    }
}
