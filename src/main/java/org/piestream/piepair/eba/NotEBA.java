package org.piestream.piepair.eba;


import org.piestream.events.PointEvent;

public   class NotEBA extends EBA {
    private final EBA expression;

    public NotEBA(EBA expression) {
        this.expression = expression;
    }

    @Override
    public boolean evaluate(PointEvent event) {
        return !expression.evaluate(event);
    }
}