package io.github.blairjeon.userorderapi.repository.product;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.github.blairjeon.userorderapi.domain.product.Product;
import io.github.blairjeon.userorderapi.dto.product.SearchProductCondition;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.time.LocalDate;
import java.util.List;

import static io.github.blairjeon.userorderapi.domain.product.QProduct.product;

@RequiredArgsConstructor
public class ProductRepositoryImpl {
    private final JPAQueryFactory queryFactory;

    public Slice<Product> search(SearchProductCondition condition, Pageable pageable){
        List<Product> list = queryFactory
                .selectFrom(product)
                .where(
                        keywordContains(condition.keyword()),
                        minPrice(condition.minPrice()),
                        maxPrice(condition.maxPrice()),
                        startDate(condition.startDate()),
                        startDate(condition.endDate())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize()+1)
                .fetch();

        boolean hasNext = list.size() > pageable.getPageSize();

        if(hasNext){
            list.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(list, pageable, hasNext);
    }

    private BooleanExpression keywordContains(String keyword){
        return keyword!=null ? product.productName.contains(keyword) : null;
    }

    private BooleanExpression minPrice(Long minPrice){
        return minPrice!=null ? product.unitPrice.goe(minPrice) : null;
    }

    private BooleanExpression maxPrice(Long maxPrice){
        return maxPrice!=null ? product.unitPrice.loe(maxPrice) : null;
    }

    private BooleanExpression startDate(LocalDate startDate){
        return startDate !=null ? product.createdAt.goe(startDate.atStartOfDay()) : null;
    }

    private BooleanExpression endDate(LocalDate endDate){
        return endDate != null
                ? product.createdAt.loe(endDate.plusDays(1).atStartOfDay())
                : null;
    }
}