package com.github.fge.grappa.debugger.tracetab;

import com.github.fge.grappa.debugger.tracetab.statistics.ParseNode;
import com.github.fge.grappa.debugger.tracetab.statistics.TracingCharEscaper;
import com.github.parboiled1.grappa.buffers.InputBuffer;
import com.github.parboiled1.grappa.trace.TraceEvent;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Strings;
import com.google.common.escape.CharEscaper;
import org.parboiled.support.Position;

import java.util.ArrayList;
import java.util.List;

public class TraceTabPresenter
{
    private static final CharEscaper ESCAPER = new TracingCharEscaper();

    private final TraceTabModel model;
    private final InputBuffer buffer;

    private TraceTabView view;

    public TraceTabPresenter(final TraceTabModel model)
    {
        this.model = model;
        buffer = model.getInputBuffer();
    }

    public void setView(final TraceTabView view)
    {
        this.view = view;
    }

    public void loadTrace()
    {
        final List<TraceEvent> events = model.getTraceEvents();

        if (events.isEmpty())
            return;

        view.setParseDate(model.getTrace().getStartDate());
        view.setTraceEvents(events);
        view.setStatistics(model.getRuleStats());
        view.setInputTextInfo(model.getInputTextInfo());
        view.setInputText(buffer.extract(0, buffer.length()));
        view.setParseTree(model.getParseTreeRoot());
    }

    void handleParseNodeShow(final ParseNode node)
    {
        final Position position = buffer.getPosition(node.getStart());
        final int line = position.getLine();
        final int column = position.getColumn();
        final boolean success = node.isSuccess();

        final StringBuilder sb = new StringBuilder("Match information:\n");
        sb.append("Matcher: ").append(node.getRuleName());
        sb.append("\nStarting position: line ").append(line)
            .append(", column ").append(column)
            .append("\n----\n").append(buffer.extractLine(line))
            .append('\n')
            .append(Strings.repeat(" ", column - 1)).append("^\n----\n");
        if (success) {
            sb.append("Match SUCCESS; text matched:\n<")
                .append(buffer.extract(node.getStart(), node.getEnd()))
                .append('>');
        } else {
            sb.append("Match FAILED");
        }
        view.setParseNodeDetails(sb.toString());
        final List<String> fragments = getFragments(node);
        view.highlightText(fragments, position, success);
    }

    @VisibleForTesting
    List<String> getFragments(final ParseNode node)
    {
        final int length = buffer.length();
        final int start = node.getStart();
        final int end = node.getEnd();

        final List<String> ret = new ArrayList<>(3);
        ret.add(buffer.extract(0, start));
        final String match = buffer.extract(Math.min(start, length), end);
        final String displayed;
        if (node.isSuccess())
            displayed = match.isEmpty() ? "\u2205"
                : '\u21fe' + ESCAPER.escape(match) + '\u21fd';
        else
            displayed = "\u2612";
        ret.add(displayed);
        ret.add(buffer.extract(Math.min(end, length), length));

        return ret;
    }
}
