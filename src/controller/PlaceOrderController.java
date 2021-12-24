package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    /**
     * For reduce hard code
     */
    public static final String ADDRESS = "address";
    public static final String PHONE_NUMBER = "phone";
    public static final String NAME = "name";

    private ShippingFeeCalculator calculateShippingFee;

    public PlaceOrderController() {
    }

    public PlaceOrderController(ShippingFeeCalculator calculateShippingFee) {
        this.calculateShippingFee = calculateShippingFee;
    }

    /**
     * Just for logging purpose
     */
    private static Logger LOGGER = utils.Utils.getLogger(PlaceOrderController.class.getName());

    /**
     * This method checks the availability of product when user click PlaceOrder button
     * @throws SQLException
     */
    public void placeOrder() throws SQLException{
        Cart.getCart().checkAvailabilityOfProduct();
    }

    /**
     * This method creates the new Order based on the Cart
     * @return Order
     * @throws SQLException
     */
    public Order createOrder() throws SQLException{
        Order order = new Order();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(), 
                                                   cartMedia.getQuantity(), 
                                                   cartMedia.getPrice());    
            order.getlstOrderMedia().add(orderMedia);
        }
        return order;
    }

    /**
     * This method creates the new Invoice based on order
     * @param order
     * @return Invoice
     */
    public Invoice createInvoice(Order order) {
        return new Invoice(order);
    }

    /**
     * This method takes responsibility for processing the shipping info from user
     * @param info
     * @throws InterruptedException
     * @throws IOException
     */
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());
        validateDeliveryInfo(info);
    }
    
    /**
   * The method validates the info
   * @param info
   * @throws InterruptedException
   * @throws IOException
   */
    public boolean validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException{
        String address = info.get(ADDRESS);
        String name = info.get(NAME);
        String phoneNumber = info.get(PHONE_NUMBER);
        return validateAddress(address) && validateName(name) && validatePhoneNumber(phoneNumber);
    }

    /**
     * The method validates the phone number of user
     * @param phoneNumber
     */
    public boolean validatePhoneNumber(String phoneNumber) {
        // HungND-20183548
        if (phoneNumber == null || phoneNumber.isEmpty() || phoneNumber.charAt(0) != '0' || phoneNumber.length() != 10) {
            return false;
        }

        boolean isValid = true;
        for (char ch : phoneNumber.toCharArray()) {
            if ( !Character.isDigit(ch) ) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }

    /**
     * The method validates name of user
     * @param name User's name
     */
    public boolean validateName(String name) {
        // HungND-20183548
        if (name == null || name.isEmpty()) {
            return false;
        }

        boolean isValid = true;
        for (char ch : name.toCharArray()) {
            if ( !Character.isLetter(ch) ) {
                isValid = false;
            }
        }

        return isValid;
    }

    /**
     * The method validates user's address
     * @param address
     */
    public boolean validateAddress(String address) {
        // HungND-20183548
        if (address == null || address.isEmpty()) {
            return false;
        }

        boolean isValid = true;
        for (char ch : address.toCharArray()) {
            if ( ch == ' ' ) {
                continue;
            }
            if ( !Character.isLetter(ch) ) {
                isValid = false;
                break;
            }
        }

        return isValid;
    }
    

    /**
     * This method calculates the shipping fees of order
     * @param order
     * @return shippingFee
     */
    public int calculateShippingFee(Order order){
        return calculateShippingFee.calculateShippingFee(order);
    }
}
