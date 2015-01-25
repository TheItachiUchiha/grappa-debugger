package com.github.fge.grappa.debugger.tracetab;

import com.github.fge.grappa.debugger.statistics.StatsType;
import com.github.fge.grappa.internal.NonFinalForTesting;

import java.net.URL;
import java.util.EnumMap;
import java.util.Map;

@NonFinalForTesting
class JavafxStatsTabFactory
{
    private static final Class<?> MYSELF = JavafxStatsTabFactory.class;
    private static final Map<StatsType, URL> STAT_TABS_FXML_FILES;

    static {
        STAT_TABS_FXML_FILES = new EnumMap<>(StatsType.class);

        StatsType type;
        String location;
        URL url;

        type = StatsType.GLOBAL;
        location = "/stats/global.fxml";
        url = MYSELF.getResource(location);
        if (url == null)
            throw new ExceptionInInitializerError("resource " + location
                + " not found");
        STAT_TABS_FXML_FILES.put(type, url);
    }
}
