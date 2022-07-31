package com.quendo.qstaffmode.commandflow.builder;

import com.quendo.qore.files.config.OldYMLFile;
import lombok.AllArgsConstructor;
import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.usage.DefaultUsageBuilder;
import net.kyori.text.Component;
import net.kyori.text.TextComponent;
import net.kyori.text.format.TextColor;

@AllArgsConstructor
public class UsageBuilderImpl extends DefaultUsageBuilder {

    private final OldYMLFile messages;

    @Override
    public Component getUsage(CommandContext context) {
        Component usage = super.getUsage(context);
        return TextComponent.of(messages.getString("command-translator.usage.text"))
                .append(usage).color(TextColor.valueOf(
                        messages.getString("command-translator.usage.text")
                ));
    }

}
