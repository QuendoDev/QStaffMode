package com.quendo.qstaffmode.commandflow.translator;

import com.quendo.qore.files.OldYMLFile;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;


public class CustomTranslatorProvider implements TranslationProvider {

    private final OldYMLFile messages;

    public CustomTranslatorProvider(OldYMLFile messages) {
        this.messages = messages;
    }

    @Override
    public String getTranslation(Namespace namespace, String key){
        return messages.getString("command-translator." + key);
    }
}
