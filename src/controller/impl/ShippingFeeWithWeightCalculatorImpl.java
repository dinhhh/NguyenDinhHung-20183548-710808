package controller.impl;

import controller.ShippingFeeCalculator;
import entity.order.Order;

import java.util.Random;

public class ShippingFeeWithWeightCalculatorImpl implements ShippingFeeCalculator {

    private final int LENGTH = 60;
    private final int WIDTH = 70;
    private final int HEIGHT = 80;
    private final int COEFFICIENT = 6000;

    @Override
    public int calculateShippingFee(Order order) {
        Random rand = new Random();
        return (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() + LENGTH * WIDTH * HEIGHT / COEFFICIENT);
    }

}
