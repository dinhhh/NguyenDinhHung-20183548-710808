package controller;

import controller.impl.ShippingFeeCalculatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author HungND - 20183548
 */
public class ValidateNameTest {

    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController(new ShippingFeeCalculatorImpl());
    }

    @ParameterizedTest
    @CsvSource({
            "Hoang,true",
            "Viet Manh,false",
            "%Hoa,false",
            "H62a,false",
            "H()a,false"
    })
    void test(String name, boolean expected) {
        boolean isValid = placeOrderController.validateName(name);
        Assertions.assertEquals(isValid, expected);
    }
}
