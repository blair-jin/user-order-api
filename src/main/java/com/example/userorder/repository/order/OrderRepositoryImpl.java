package com.example.userorder.repository.order;

import com.example.userorder.domain.order.Order;
import com.example.userorder.dto.order.OrderSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.util.List;

import static com.example.userorder.domain.order.QOrder.order;

@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Order> searchOrders(Long userId, OrderSearchCondition condition, Pageable pageable) {
        List<Order> orders = queryFactory
                .selectFrom(order)
                .where(
                        userIdEq(userId),
                        minPrice(condition.minPrice()),
                        maxPrice(condition.maxPrice()),
                        startDate(condition.startDate()),
                        endDate(condition.endDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = orders.size() > pageable.getPageSize();

        if (hasNext) {
            orders.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(orders, pageable, hasNext);
    }

    private BooleanExpression userIdEq(Long userId) {
        return order.userId.eq(userId);
    }

    private BooleanExpression minPrice(Long minPrice) {
        return minPrice != null ? order.totalAmount.goe(minPrice) : null;
    }

    private BooleanExpression maxPrice(Long maxPrice) {
        return maxPrice != null ? order.totalAmount.loe(maxPrice) : null;
    }

    private BooleanExpression startDate(LocalDate startDate) {
        return startDate != null ? order.createdAt.goe(startDate.atStartOfDay()) : null;
    }

    private BooleanExpression endDate(LocalDate endDate) {
        return endDate != null ? order.createdAt.lt(endDate.plusDays(1).atStartOfDay()) : null;
    }
}