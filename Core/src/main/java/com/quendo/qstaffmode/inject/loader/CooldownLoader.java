package com.quendo.qstaffmode.inject.loader;

import com.quendo.qore.setup.Loader;
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