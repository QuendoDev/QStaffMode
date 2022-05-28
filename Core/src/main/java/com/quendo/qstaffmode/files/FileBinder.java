package com.quendo.qstaffmode.files;

import com.kino.kore.utils.files.YMLFile;
import team.unnamed.inject.Module;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class FileBinder {
    private final Map<String, YMLFile> files = new HashMap<>();

    public FileBinder bind(String name, YMLFile file) {
        files.put(name, file);

        return this;
    }

    public Optional<YMLFile> get(String name) {
        return Optional.ofNullable(files.get(name));
    }

    public Module build() {
        return binder -> files.forEach((name, file) -> binder.bind(YMLFile.class).named(name).toInstance(file));
    }

}
