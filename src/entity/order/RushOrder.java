package entity.order;

import java.time.LocalDateTime;

public class RushOrder {

    private Order order;
    private String rushOrderInstruction;
    private String rushOrderInfo;
    private LocalDateTime receiveTime;

    public RushOrder(Order order, String rushOrderInstruction, String rushOrderInfo, LocalDateTime receiveTime) {
        this.order = order;
        this.rushOrderInstruction = rushOrderInstruction;
        this.rushOrderInfo = rushOrderInfo;
        this.receiveTime = receiveTime;
    }

    public RushOrder() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getRushOrderInstruction() {
        return rushOrderInstruction;
    }

    public void setRushOrderInstruction(String rushOrderInstruction) {
        this.rushOrderInstruction = rushOrderInstruction;
    }

    public String getRushOrderInfo() {
        return rushOrderInfo;
    }

    public void setRushOrderInfo(String rushOrderInfo) {
        this.rushOrderInfo = rushOrderInfo;
    }

    public LocalDateTime getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(LocalDateTime receiveTime) {
        this.receiveTime = receiveTime;
    }
}
