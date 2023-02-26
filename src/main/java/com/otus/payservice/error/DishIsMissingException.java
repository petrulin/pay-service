package com.otus.payservice.error;

public class DishIsMissingException extends StoreServiceException {
    public DishIsMissingException() {
        super(ApplicationError.DISH_IS_MISSING);
    }
}
