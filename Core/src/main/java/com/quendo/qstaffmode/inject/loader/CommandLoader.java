package com.quendo.qstaffmode.inject.loader;

import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.loaders.Loader;
import com.quendo.qstaffmode.QStaffMode;
import com.quendo.qstaffmode.commandflow.translator.CustomTranslatorProvider;
import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import team.unnamed.inject.InjectAll;
import team.unnamed.inject.InjectIgnore;

import javax.inject.Named;

@InjectAll
public class CommandLoader implements Loader {

    private QStaffMode plugin;

    @Named("messages")
    private YMLFile messages;

    @InjectIgnore
    private final AnnotatedCommandTreeBuilder builder = createBuilder();

    @InjectIgnore
    private final CommandManager commandManager = new BukkitCommandManager("QStaffMode");

    private AnnotatedCommandTreeBuilder createBuilder() {
        PartInjector injector = PartInjector.create();
        injector.install(new DefaultsModule());
        return new AnnotatedCommandTreeBuilderImpl(injector);
    }

    private void registerCommands(CommandClass... commandClasses) {
        for (CommandClass commandClass : commandClasses) {
            commandManager.registerCommands(builder.fromClass(commandClass));
        }
    }

    @Override
    public void load() {
        commandManager.getTranslator().setProvider(new CustomTranslatorProvider(messages));
        registerCommands();
    }
}
