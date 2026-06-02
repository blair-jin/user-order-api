package com.example.userorder.controller;

import com.example.userorder.application.cart.usecase.*;
import com.example.userorder.dto.cart.CartItemAddRequest;
import com.example.userorder.dto.cart.CartItemResponse;
import com.example.userorder.dto.cart.CartItemUpdateRequest;
import com.example.userorder.security.principal.CustomUserPrincipal;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart/items")
@RequiredArgsConstructor
public class CartController {

    private final AddCartItemUseCase addCartItemUseCase;
    private final GetItemsUseCase getItemsUseCase;
    private final GetItemUseCase getItemUseCase;
    private final UpdateCartItemUseCase updateCartItemUseCase;
    private final DeleteCartItemUseCase deleteCartItemUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addItem(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @Valid @RequestBody CartItemAddRequest request
    ) {
        addCartItemUseCase.execute(principal.userId(), request);
    }

    @GetMapping
    public Slice<CartItemResponse> getItems(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            Pageable pageable
    ) {
        return getItemsUseCase.execute(principal.userId(), pageable);
    }

    @GetMapping("/{cartItemId}")
    public CartItemResponse getItem(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long cartItemId
    ) {
        return getItemUseCase.execute(principal.userId(), cartItemId);
    }

    @PatchMapping("/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateItemQuantity(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long cartItemId,
            @Valid @RequestBody CartItemUpdateRequest request
    ) {
        updateCartItemUseCase.execute(principal.userId(), cartItemId, request);
    }

    @DeleteMapping("/{cartItemId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeItem(
            @AuthenticationPrincipal CustomUserPrincipal principal,
            @PathVariable Long cartItemId
    ) {
        deleteCartItemUseCase.execute(principal.userId(), cartItemId);
    }
}