package entity.shipping;

import java.time.LocalDateTime;

public class Shipment {

    private String instruction;
    private LocalDateTime receivingTime;

    public Shipment() {
    }

    public Shipment(String instruction, LocalDateTime receivingTime) {
        this.instruction = instruction;
        this.receivingTime = receivingTime;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public LocalDateTime getReceivingTime() {
        return receivingTime;
    }

    public void setReceivingTime(LocalDateTime receivingTime) {
        this.receivingTime = receivingTime;
    }

    public void validateDeliveryInfo(){
        // TODO: implement later on
    }

    public Shipment createNewShipment(){
        // TODO: implement later on
        return new Shipment();
    }
}
