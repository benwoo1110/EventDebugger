package dev.benergy10.eventdebugger;

import dev.benergy10.minecrafttools.MinecraftPlugin;
import dev.benergy10.minecrafttools.configs.CommentedYamlFile;
import dev.benergy10.minecrafttools.configs.YamlFile;

import java.util.List;
import java.util.stream.Collectors;

public final class EventDebugger extends MinecraftPlugin {

    private List<EventListener> eventListenerList;

    @Override
    public void enable() {
        this.commandManager.usePerIssuerLocale(true, true);
        this.commandManager.registerCommand(new ReloadCommand());

        YamlFile config = new CommentedYamlFile(this.getConfigFile(), EventOptions.getOptions(), EventOptions.getHeader());

        this.eventListenerList = config.getValue(EventOptions.LISTENERS).stream().map(event -> {
            EventListener eventListener = new EventListener(event);
            eventListener.register(this);
            return eventListener;
        }).collect(Collectors.toList());
    }
}
