package cas.pay;

public class PayPal extends PaymentMethod {

	String email;

	protected String name() {
		return "PayPal";
	}
}
