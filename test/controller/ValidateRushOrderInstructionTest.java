package controller;

import controller.impl.SimpleRushOrderValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

/**
 * @author HungND - 20183548
 */
public class ValidateRushOrderInstructionTest {

    private PlaceRushOrderController placeRushOrderController;

    @BeforeEach
    void setUp() {
        placeRushOrderController = new PlaceRushOrderController(new SimpleRushOrderValidator());
    }

    @ParameterizedTest
    @CsvSource({
            "Den dung gio vi toi co hen,true",
            "123,false",
            "Di vao ngo thu ba roi re trai,true",
            "@123,false"
    })
    void test(String instruction, boolean expected) {
        boolean isValid = placeRushOrderController.validateRushOrderInstruction(instruction);
        Assertions.assertEquals(isValid, expected);
    }
}
