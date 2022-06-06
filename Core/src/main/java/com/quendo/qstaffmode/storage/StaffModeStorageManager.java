package com.quendo.qstaffmode.storage;

import com.eatthepath.uuid.FastUUID;
import com.kino.kore.utils.files.YMLFile;
import com.kino.kore.utils.storage.Storage;
import com.quendo.qstaffmode.models.data.StaffInformation;
import org.bukkit.configuration.ConfigurationSection;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@SuppressWarnings("unchecked")
public class StaffModeStorageManager implements Storage<UUID, StaffInformation> {

    @Inject
    @Named("staffInformation")
    private YMLFile staffInformation;

    private final Map<UUID, StaffInformation> inStaffMode = new ConcurrentHashMap<>();

    @Override
    public Map<UUID, StaffInformation> get() {
        return inStaffMode;
    }

    @Override
    public Optional<StaffInformation> find(UUID uuid) {
        return Optional.ofNullable(inStaffMode.get(uuid));
    }

    @Override
    public Optional<StaffInformation> findFromData(UUID uuid) {
        if (!staffInformation.contains("staff." + FastUUID.toString(uuid))) {
            return Optional.empty();
        }

        Object o = staffInformation.get("staff." + FastUUID.toString(uuid));

        if (o instanceof Map) {
            return Optional.of(new StaffInformation((Map<String, Object>) o));
        } else if (o instanceof ConfigurationSection) {
            return Optional.of(new StaffInformation(staffInformation.getConfigurationSection("staff." + FastUUID.toString(uuid)).getValues(false)));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void save(UUID uuid) {
        find(uuid).ifPresent(staff -> {
            staffInformation.set("staff." + FastUUID.toString(uuid), staff.serialize());
            staffInformation.save();

            remove(uuid);
        });
    }

    @Override
    public void saveObject(UUID key, StaffInformation value) {
        staffInformation.set("staff." + FastUUID.toString(key), value.serialize());
        staffInformation.save();

        remove(key);
    }

    @Override
    public void remove(UUID uuid) {
        inStaffMode.remove(uuid);
    }

    @Override
    public void add(UUID uuid, StaffInformation value) {
        inStaffMode.put(uuid, value);
    }

    @Override
    public void saveAll() {
        inStaffMode.keySet().forEach(this::save);
    }

    @Override
    public void loadAll() {
        if (!staffInformation.contains("staff") || staffInformation.getConfigurationSection("staff") == null) {
            return;
        }

        staffInformation.getConfigurationSection("staff").getKeys(false).
                forEach(uuid -> findFromData(FastUUID.parseUUID(uuid)).ifPresent(user -> add(FastUUID.parseUUID(uuid), user)));
    }
}
