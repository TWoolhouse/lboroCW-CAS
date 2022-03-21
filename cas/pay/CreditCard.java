package cas.pay;

public class CreditCard extends PaymentMethod {

	String number;
	String security;

	@Override
	protected String name() {
		return "Credit Card";
	}
}
