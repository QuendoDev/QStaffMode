package com.quendo.qstaffmode.storage;

import com.eatthepath.uuid.FastUUID;
import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.storage.Storage;
import com.quendo.qstaffmode.models.LeaveInformation;
import com.quendo.qstaffmode.models.StaffInformation;
import org.bukkit.configuration.ConfigurationSection;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class LeaveStorageManager implements Storage<UUID, LeaveInformation> {

    @Inject
    @Named("leaveInformation")
    private YMLFile leaveInformationFile;

    private final Map<UUID, LeaveInformation> leaveInformation = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, LeaveInformation> get() {
        return leaveInformation;
    }

    @Override
    public Optional<LeaveInformation> find(UUID uuid) {
        return Optional.ofNullable(leaveInformation.get(uuid));
    }

    @Override
    public Optional<LeaveInformation> findFromData(UUID uuid) {
        if (!leaveInformationFile.contains("saved." + FastUUID.toString(uuid))) {
            return Optional.empty();
        }

        Object o = leaveInformationFile.get("saved." + FastUUID.toString(uuid));

        if (o instanceof Map) {
            return Optional.of(new LeaveInformation((Map<String, Object>) o));
        } else if (o instanceof ConfigurationSection) {
            return Optional.of(new LeaveInformation(leaveInformationFile.getConfigurationSection("saved." + FastUUID.toString(uuid)).getValues(false)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void save(UUID uuid) {
        find(uuid).ifPresent(staff -> {
            leaveInformationFile.set("saved." + FastUUID.toString(uuid), staff.serialize());
            leaveInformationFile.save();

            remove(uuid);
        });
    }

    @Override
    public void saveObject(UUID key, LeaveInformation value) {
        leaveInformationFile.set("saved." + FastUUID.toString(key), value.serialize());
        leaveInformationFile.save();

        remove(key);
    }

    @Override
    public void remove(UUID uuid) {
        leaveInformation.remove(uuid);
    }

    @Override
    public void add(UUID uuid, LeaveInformation value) {
        leaveInformation.put(uuid, value);
    }

    @Override
    public void saveAll() {
        leaveInformation.keySet().forEach(this::save);
    }

    @Override
    public void loadAll() {
        if (!leaveInformationFile.contains("saved") || leaveInformationFile.getConfigurationSection("saved") == null) {
            return;
        }

        leaveInformationFile.getConfigurationSection("saved").getKeys(false).
                forEach(uuid -> findFromData(FastUUID.parseUUID(uuid)).ifPresent(user -> add(FastUUID.parseUUID(uuid), user)));
    }
}
