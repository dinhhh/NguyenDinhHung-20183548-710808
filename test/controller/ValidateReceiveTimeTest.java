package controller;

import controller.impl.SimpleRushOrderValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author HungND - 20183548
 */
public class ValidateReceiveTimeTest {

    private PlaceRushOrderController placeRushOrderController;

    @BeforeEach
    void setUp() {
        placeRushOrderController = new PlaceRushOrderController(new SimpleRushOrderValidator());
    }

    @ParameterizedTest
    @CsvSource({
            "08-08-2020 14:00,true",
            "08/08/2020 14:00,false",
            "08/13/2020 14:00,false",
            "08-08/2020 14:00,false",
            "08-08-2020 25:00,false",
            "33-08-2020 14:00,false"
    })
    void test(String time, boolean expected) {
        boolean isValid = placeRushOrderController.validateReceiveTime(time);
        Assertions.assertEquals(isValid, expected);
    }
}
