package controller;

public interface RushOrderInputValidator {

    boolean isValidReceiveTime(String time, String pattern);
    boolean isValidRushOrderInfo(String info);
    boolean isValidRushOrderInstruction(String instruction);

}
