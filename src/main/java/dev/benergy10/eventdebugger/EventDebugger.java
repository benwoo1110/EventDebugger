package dev.benergy10.eventdebugger;

import dev.benergy10.minecrafttools.MinecraftPlugin;
import dev.benergy10.minecrafttools.configs.CommentedYamlFile;
import dev.benergy10.minecrafttools.configs.YamlFile;

public final class EventDebugger extends MinecraftPlugin {

    @Override
    public void enable() {
        this.commandManager.usePerIssuerLocale(true, true);
        this.commandManager.registerCommand(new ReloadCommand());

        YamlFile config = new CommentedYamlFile(this.getConfigFile(), EventOptions.getOptions());

        config.getValue(EventOptions.LISTENERS).forEach(event -> event.register(this));
    }
}
