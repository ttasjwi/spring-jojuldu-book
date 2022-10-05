package com.ttasjwi.jojulduBook.web.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString(of = {"name", "amount"})
public class HelloResponseDto {

    private final String name;
    private final int amount;
}
