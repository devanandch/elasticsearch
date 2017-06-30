/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.plan.physical;

import org.elasticsearch.xpack.sql.expression.Attribute;
import org.elasticsearch.xpack.sql.expression.Expression;
import org.elasticsearch.xpack.sql.expression.Expressions;
import org.elasticsearch.xpack.sql.expression.NamedExpression;

import java.util.List;
import java.util.Objects;

public class AggregateExec extends UnaryExec implements Unexecutable {

    private final List<? extends Expression> groupings;
    private final List<? extends NamedExpression> aggregates;

    public AggregateExec(PhysicalPlan child, List<? extends Expression> groupings, List<? extends NamedExpression> aggregates) {
        super(child);
        this.groupings = groupings;
        this.aggregates = aggregates;
    }

    public List<? extends Expression> groupings() {
        return groupings;
    }

    public List<? extends NamedExpression> aggregates() {
        return aggregates;
    }

    @Override
    public List<Attribute> output() {
        return Expressions.asAttributes(aggregates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupings, aggregates, child());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        AggregateExec other = (AggregateExec) obj;

        return Objects.equals(groupings, other.groupings)
                && Objects.equals(aggregates, other.aggregates)
                && Objects.equals(child(), other.child());
    }
}
