package views.screen.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import controller.PlaceOrderController;
import common.exception.InvalidDeliveryInfoException;
import controller.PlaceRushOrderController;
import controller.impl.SimpleRushOrderValidator;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;

public class ShippingScreenHandler extends BaseScreenHandler implements Initializable {

	@FXML
	private Label screenTitle;

	@FXML
	private TextField name;

	@FXML
	private TextField phone;

	@FXML
	private TextField address;

	@FXML
	private TextField instructions;

	@FXML
	private ComboBox<String> province;

	@FXML
	private RadioButton isRushOrderBtn;

	private static Logger LOGGER = Utils.getLogger(ShippingScreenHandler.class.getName());

	private Order order;

	public ShippingScreenHandler(Stage stage, String screenPath, Order order) throws IOException {
		super(stage, screenPath);
		this.order = order;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
		name.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
		this.province.getItems().addAll(Configs.PROVINCES);
	}

	@FXML
	void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

		PlaceOrderController placeOrderController = new PlaceOrderController();
		// add info to messages
		HashMap messages = new HashMap<>();
		messages.put("name", name.getText());
		messages.put("phone", phone.getText());
		messages.put("address", address.getText());
		messages.put("instructions", instructions.getText());
		messages.put("province", province.getValue());
		try {
			// process and validate delivery info
			getBController().processDeliveryInfo(messages);
		} catch (InvalidDeliveryInfoException e) {
			throw new InvalidDeliveryInfoException(e.getMessage());
		}

		AtomicBoolean supportRushOrder = new AtomicBoolean(true);
		if (isRushOrderBtn.isSelected()) {
			PlaceRushOrderController placeRushOrderController = new PlaceRushOrderController(new SimpleRushOrderValidator());
			String location = address.getText();
			this.order.getlstOrderMedia().forEach(c -> {
				OrderMedia orderMedia = (OrderMedia) c;
				if (!placeRushOrderController.isSupportRushOrder(province.getValue(), orderMedia.getMedia().getId())) {
					supportRushOrder.set(false);
				}
			});
		}

		if (!supportRushOrder.get()) {
			String message = "Your location not support rush order for medias";
			PopupScreen.error(message);
		} else if (!placeOrderController.validateDeliveryInfo(messages)) {
			String message = "Your info is invalid. Please check again";
			PopupScreen.error(message);
		} else {
			// calculate shipping fees
			int shippingFees = getBController().calculateShippingFee(order);
			order.setShippingFees(shippingFees);
			order.setDeliveryInfo(messages);

			// create invoice screen
			Invoice invoice = getBController().createInvoice(order);
			BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
			InvoiceScreenHandler.setPreviousScreen(this);
			InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
			InvoiceScreenHandler.setScreenTitle("Invoice Screen");
			InvoiceScreenHandler.setBController(getBController());
			InvoiceScreenHandler.show();
		}
	}

	public PlaceOrderController getBController(){
		return (PlaceOrderController) super.getBController();
	}

	public void notifyError(){
		// TODO: implement later on if we need
	}

}
