package controller;

import controller.impl.ShippingFeeCalculatorImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author HungND - 20183548
 */
public class ValidateAddressTest {

    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController(new ShippingFeeCalculatorImpl());
    }

    @ParameterizedTest
    @CsvSource({
            "&Ha Noi,false",
            "Ha%Noi,false,",
            "Ha Noi,true"
    })
    void test(String address, boolean expected) {
        boolean isValid = placeOrderController.validateAddress(address);
        Assertions.assertEquals(isValid, expected);
    }
}
