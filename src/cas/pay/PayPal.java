package cas.pay;

public class PayPal extends PaymentMethod {

	String email = "";

	protected String name() {
		return "PayPal";
	}

	@Override
	public boolean valid() {
		return email.contains("@");
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
