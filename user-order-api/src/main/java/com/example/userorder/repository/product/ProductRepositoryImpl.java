package com.example.userorder.repository.product;

import com.example.userorder.domain.product.Product;
import com.example.userorder.dto.product.ProductSearchCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

import static com.example.userorder.domain.product.QProduct.product;


@Component
@RequiredArgsConstructor
public class ProductRepositoryImpl implements ProductRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Product> searchProducts(ProductSearchCondition condition, Pageable pageable) {
        List<Product> products = queryFactory
                .selectFrom(product)
                .where(
                        nameContains(condition.name()),
                        priceGoe(condition.minPrice()),
                        priceLoe(condition.maxPrice()),
                        isAfter(condition.startDate()),
                        isBefore(condition.endDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        boolean hasNext = products.size() > pageable.getPageSize();

        if (hasNext) {
            products.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(products, pageable, hasNext);
    }

    private BooleanExpression nameContains(String name) {
        return name != null && !name.isBlank()
                ? product.name.contains(name)
                : null;
    }

    private BooleanExpression priceGoe(Long min) {
        return min != null
                ? product.unitPrice.goe(min)
                : null;
    }

    private BooleanExpression priceLoe(Long max) {
        return max != null
                ? product.unitPrice.loe(max)
                : null;
    }

    private BooleanExpression isAfter(LocalDate startDate) {
        return startDate != null
                ? product.createdAt.goe(startDate.atStartOfDay())
                : null;
    }

    private BooleanExpression isBefore(LocalDate endDate) {
        return endDate != null
                ? product.createdAt.lt(endDate.plusDays(1).atStartOfDay())
                : null;
    }
}
