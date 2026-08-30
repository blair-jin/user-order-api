package io.github.blairjeon.userorderapi.controller;

import io.github.blairjeon.userorderapi.application.cart.usecase.AddCartItemUseCase;
import io.github.blairjeon.userorderapi.application.cart.usecase.DeleteCartItemUseCase;
import io.github.blairjeon.userorderapi.application.cart.usecase.GetCartItemsUseCase;
import io.github.blairjeon.userorderapi.application.cart.usecase.UpdateCartItemUseCase;
import io.github.blairjeon.userorderapi.application.order.usecase.CancelOrderUseCase;
import io.github.blairjeon.userorderapi.dto.cart.CartItemAddRequest;
import io.github.blairjeon.userorderapi.dto.cart.CartItemResponse;
import io.github.blairjeon.userorderapi.dto.cart.CartItemUpdateRequest;
import io.github.blairjeon.userorderapi.infrastructure.jwt.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/carts")
public class CartController {
    private final AddCartItemUseCase addCartItemUseCase;
    private final GetCartItemsUseCase getCartItemsUseCase;
    private final UpdateCartItemUseCase updateCartItemUseCase;
    private final DeleteCartItemUseCase deleteCartItemUseCase;
    private final CancelOrderUseCase cancelOrderUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody CartItemAddRequest request
    ){
        addCartItemUseCase.execute(principal.userId(), request);
    }

    @GetMapping
    public List<CartItemResponse> getItems(@AuthenticationPrincipal CustomUserPrincipal principal){
        return getCartItemsUseCase.execute(principal.userId());
    }

    @PatchMapping("/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItem(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemUpdateRequest request
    ){
        updateCartItemUseCase.execute(principal.userId(), cartItemId, request);
    }

    @DeleteMapping("/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteItem(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long cartItemId
    ){
        deleteCartItemUseCase.execute(principal.userId(), cartItemId);
    }

    @DeleteMapping("/{orderId}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clear(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long orderId
    ){
        cancelOrderUseCase.execute(principal.userId(), orderId);
    }
}