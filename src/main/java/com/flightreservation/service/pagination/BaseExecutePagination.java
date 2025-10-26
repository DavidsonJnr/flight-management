package com.flightreservation.service.pagination;

import java.util.List;

import com.flightreservation.model.dto.SearchPagination;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public abstract class BaseExecutePagination<T> implements ExecutePagination<T> {

    protected final EntityManager entityManager;
    protected final SearchPagination pagination;

    protected BaseExecutePagination(EntityManager entityManager, SearchPagination pagination) {
        this.entityManager = entityManager;
        this.pagination = pagination;
    }

    @Override
    public String order(SearchPagination pagination) {
        return String.format(" ORDER BY obj.%s %s", pagination.getSortField(), pagination.getSort().name());
    }

    @Override
    public Long count() {
        String hql = getBaseQuery("count(*)");
        TypedQuery<Long> query = entityManager.createQuery(hql, Long.class);
        setParameters(query);
        return query.getSingleResult();
    }

    @Override
    public List<T> result() {
        String hql = getBaseQuery(getSelectFields()) + order(pagination);
        org.hibernate.query.Query<?> query = entityManager.createQuery(hql, getEntityClass())
                .unwrap(org.hibernate.query.Query.class);

        query.setMaxResults(pagination.getSize());
        query.setFirstResult(pagination.getOffset());
        setParameters(query);
        return buildClassList(query);
    }

    protected abstract Class<T> getEntityClass();

    protected abstract String getSelectFields();

    protected abstract <T> List<T> buildClassList(org.hibernate.query.Query<?> query);

}
