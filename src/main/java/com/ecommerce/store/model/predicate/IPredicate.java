package com.ecommerce.store.model.predicate;

import com.ecommerce.store.model.MetricSearch;
import com.querydsl.core.types.Predicate;

public interface IPredicate {
    Predicate findByMetricFilter(MetricSearch metricSearch);
}
