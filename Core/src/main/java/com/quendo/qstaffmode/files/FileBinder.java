package com.quendo.qstaffmode.files;


import com.quendo.qore.files.OldYMLFile;
import team.unnamed.inject.Module;

import java.util.HashMap;

import java.util.Map;
import java.util.Optional;

public class FileBinder {
    private final Map<String, OldYMLFile> files = new HashMap<>();

    public FileBinder bind(String name, OldYMLFile file) {
        files.put(name, file);

        return this;
    }

    public Optional<OldYMLFile> get(String name) {
        return Optional.ofNullable(files.get(name));
    }

    public Module build() {
        return binder -> files.forEach((name, file) -> binder.bind(OldYMLFile.class).named(name).toInstance(file));
    }

}
