package com.ecommerce.store.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PageConstant {
    PAGE_DEFAULT(0),

    PAGE_SIZE_DEFAULT(10);

    private final int num;

}
