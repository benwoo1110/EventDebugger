package dev.benergy10.eventdebugger;

import dev.benergy10.minecrafttools.acf.BaseCommand;
import dev.benergy10.minecrafttools.acf.CommandIssuer;
import dev.benergy10.minecrafttools.acf.annotation.CommandAlias;
import dev.benergy10.minecrafttools.acf.annotation.Subcommand;
import dev.benergy10.minecrafttools.locales.MessageKey;

@CommandAlias("eventdebugger")
public class ReloadCommand extends BaseCommand {

    private static final MessageKey RELOAD_MESSAGE = MessageKey.of("eventdebugger.reload");

    @Subcommand("reload")
    public void onReload(CommandIssuer issuer) {
        issuer.sendInfo(RELOAD_MESSAGE);
    }
}
