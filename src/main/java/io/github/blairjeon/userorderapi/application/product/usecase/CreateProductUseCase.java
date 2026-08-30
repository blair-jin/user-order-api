package io.github.blairjeon.userorderapi.application.product.usecase;

import io.github.blairjeon.userorderapi.application.product.command.ProductCommandMapper;
import io.github.blairjeon.userorderapi.application.product.service.ProductCommandService;
import io.github.blairjeon.userorderapi.application.product.command.ProductCreateCommand;
import io.github.blairjeon.userorderapi.dto.product.ProductCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductUseCase {
    private final ProductCommandService productCommandService;

    public void execute(Long userId, ProductCreateRequest request){
        ProductCreateCommand command = ProductCommandMapper.toCommand(request);
        productCommandService.create(userId, command);
    }
}