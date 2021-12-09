package controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author HungND - 20183548
 */
public class LocationSupportRushOrderTest {

    private PlaceRushOrderController placeRushOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeRushOrderController = new PlaceRushOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "Hà Nội,true",
            "Hồ Chí Minh,false",
            "H62a,false",
            "H()a,false"
    })
    void test(String province, boolean expected) {
        boolean isValid = placeRushOrderController.isLocationSupportRushOrder(province);
        Assertions.assertEquals(isValid, expected);
    }
}
