package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import common.exception.InvalidDeliveryInfoException;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import views.screen.popup.PopupScreen;

/**
 * This class controls the flow of place order usecase in our AIMS project
 * @author nguyenlm
 */
public class PlaceOrderController extends BaseController{

    public static final String ADDRESS = "Address";
    public static final String PHONE_NUMBER = "Phone number";
    public static final String NAME = "Name";

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
    
    public boolean validatePhoneNumber(String phoneNumber) {
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
    
    public boolean validateName(String name) {
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
    
    public boolean validateAddress(String address) {
        if (address == null || address.isEmpty()) {
            return false;
        }

        boolean isValid = true;
        for (char ch : address.toCharArray()) {
            if ( Character.isSpace(ch) ) {
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
        Random rand = new Random();
        int fees = (int)( ( (rand.nextFloat()*10)/100 ) * order.getAmount() );
        LOGGER.info("Order Amount: " + order.getAmount() + " -- Shipping Fees: " + fees);
        return fees;
    }
}
