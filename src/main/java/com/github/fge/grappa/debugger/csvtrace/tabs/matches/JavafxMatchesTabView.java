package com.github.fge.grappa.debugger.csvtrace.tabs.matches;

import com.github.fge.grappa.debugger.common.JavafxView;

import java.io.IOException;

public final class JavafxMatchesTabView
    extends JavafxView<MatchesTabPresenter, MatchesTabDisplay>
    implements MatchesTabView
{
    public JavafxMatchesTabView()
        throws IOException
    {
        super("/tabs/matchesTab.fxml");
    }

    @Override
    public void disableTabRefresh()
    {
        // TODO

    }

    @Override
    public void displayInvocationStatisticsComplete()
    {
        // TODO

    }

    @Override
    public void displayInvocationStatisticsIncomplete()
    {
        // TODO

    }

    @Override
    public void displayRuleInvocationStatistics(final Object stats)
    {
        // TODO

    }
}
