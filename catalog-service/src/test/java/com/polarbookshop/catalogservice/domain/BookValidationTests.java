package com.polarbookshop.catalogservice.domain;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import static org.assertj.core.api.Assertions.assertThat;


public class BookValidationTests {
    private static Validator validator;

    @BeforeAll
    static void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsCorrectThenValidationSuccess() {
        var book = new Book("1234567890", "Title", "Author", 9.90);
        var violations = validator.validate(book);
        assertThat(violations.isEmpty());
    }

    @Test
    void whenIsbnDefinedButIncorrectThenValidationFailed() {
        var book = new Book("1234567ABC", "Title", "Author", 9.90);
        var violations = validator.validate(book);
        assertThat(violations).hasSize(1);
        assertThat(violations.iterator().next().getMessage()).isEqualTo("The ISBN format must be valid.");
    }
}
