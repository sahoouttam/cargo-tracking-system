package com.cargo.tracking.system.domain.shared;

public class NotSpecification<T> extends AbstractSpecification<T> {

    private final Specification<T> specification;

    public NotSpecification(Specification<T> specification) {
        this.specification = specification;
    }

    @Override
    public boolean isSatisfiedBy(T t) {
        return !specification.isSatisfiedBy(t);
    }
}
