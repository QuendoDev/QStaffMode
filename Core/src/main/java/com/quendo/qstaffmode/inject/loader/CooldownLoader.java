package com.quendo.qstaffmode.inject.loader;

import com.kino.kore.utils.loaders.Loader;
import com.quendo.qstaffmode.cooldown.ChatCooldown;
import team.unnamed.inject.InjectAll;


@InjectAll
public class CooldownLoader implements Loader {

    private ChatCooldown chatCooldown;

    @Override
    public void load() {
        chatCooldown.launchCooldown();
    }
}