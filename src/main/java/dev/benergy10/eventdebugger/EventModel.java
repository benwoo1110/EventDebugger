package dev.benergy10.eventdebugger;

import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;

import java.util.Objects;

public class EventModel {
    Class<? extends Event> eventClass;
    EventPriority priority;
    int coolDown;
    boolean ignoreCancelled;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventModel that = (EventModel) o;
        return coolDown == that.coolDown &&
                ignoreCancelled == that.ignoreCancelled &&
                Objects.equals(eventClass, that.eventClass) &&
                priority == that.priority;
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventClass, priority, coolDown, ignoreCancelled);
    }

    @Override
    public String toString() {
        return "EventModel{" +
                "eventClass=" + eventClass +
                ", priority=" + priority +
                ", coolDown=" + coolDown +
                ", ignoreCancelled=" + ignoreCancelled +
                '}';
    }
}
