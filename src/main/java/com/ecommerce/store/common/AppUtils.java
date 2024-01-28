package com.ecommerce.store.common;

import com.ecommerce.store.model.security.User;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Set;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppUtils {
    public static Pageable getPageable(Integer page, Integer size, String field, Boolean isDesc) {
        if (field == null || field.isEmpty() || isDesc == null) {
            return PageRequest.of(page == null ? 0 : page, size == null ? PageConstant.PAGE_SIZE_DEFAULT.getNum() : size);
        }
        return PageRequest.of(page == null ? 0 : page, size == null ? PageConstant.PAGE_SIZE_DEFAULT.getNum() : size,
                isDesc ? Sort.Direction.DESC : Sort.Direction.ASC, field);
    }


    public static void validatorUser(User user) {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        for (ConstraintViolation<User> violation : violations) {
            log.error(violation.getMessage());
            throw new RuntimeException(violation.getMessage());
        }
    }

}
