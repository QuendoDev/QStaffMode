package com.quendo.qstaffmode.inject.loader;

import com.kino.kore.utils.loaders.Loader;
import com.quendo.qstaffmode.menus.InspectMenu;
import team.unnamed.inject.InjectAll;


@InjectAll
public class MenuLoader implements Loader {

    private InspectMenu inspectMenu;

    @Override
    public void load() {
        inspectMenu.create();
    }
}
