package io.github.blairjin.user_order_api.repository.order;

import io.github.blairjin.user_order_api.domain.order.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final EntityManager em;

    @Override
    public Slice<Order> findSlice(Specification<Order> spec, Pageable pageable) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);

        Root<Order> root = query.from(Order.class);

        Predicate predicate = spec.toPredicate(root, query, cb);

        query.select(root);

        if(predicate!=null){
            query.where(predicate);
        }

        List<Order> content = em.createQuery(query)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize()+1)
                .getResultList();

        boolean hasNext = content.size() > pageable.getPageSize();

        if(hasNext){
            content.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(
                content,
                pageable,
                hasNext
        );

    }
}