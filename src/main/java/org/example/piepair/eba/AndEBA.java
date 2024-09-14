package org.example.piepair.eba;

import org.example.events.PointEvent;

public   class AndEBA extends EBA {
    private final EBA left;
    private final EBA right;

    public AndEBA(EBA left, EBA right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate(PointEvent event) {
        return left.evaluate(event) && right.evaluate(event);
    }
}
