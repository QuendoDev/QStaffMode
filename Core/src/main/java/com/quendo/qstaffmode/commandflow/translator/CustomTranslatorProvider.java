package com.quendo.qstaffmode.commandflow.translator;

import com.kino.kore.utils.files.YMLFile;
import me.fixeddev.commandflow.Namespace;
import me.fixeddev.commandflow.translator.TranslationProvider;

import java.util.HashMap;
import java.util.Map;

public class CustomTranslatorProvider implements TranslationProvider {

    protected Map<String, String> translations;

    public CustomTranslatorProvider(YMLFile messages) {
        translations = new HashMap<>();
        translations.put("command.subcommand.invalid", messages.getString("command-translator.invalid-subcommand"));
        translations.put("command.no-permission", messages.getString("command-translator.no-permission"));
        translations.put("argument.no-more",messages.getString("command-translator.no-more-arguments"));
        translations.put("number.out-range", messages.getString("command-translator.number-out-range"));
        translations.put("invalid.byte", messages.getString("command-translator.invalid-values.byte"));
        translations.put("invalid.integer", messages.getString("command-translator.invalid-values.int"));
        translations.put("invalid.float", messages.getString("command-translator.invalid-values.float"));
        translations.put("invalid.double", messages.getString("command-translator.invalid-values.double"));
        translations.put("invalid.boolean", messages.getString("command-translator.invalid-values.boolean"));
        translations.put("invalid.enum-value", messages.getString("command-translator.invalid-values.enum"));
    }

    public String getTranslation(String key) {
        return translations.get(key);
    }

    @Override
    public String getTranslation(Namespace namespace, String key){
        return getTranslation(key);
    }
}
