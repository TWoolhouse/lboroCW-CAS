package cas.pay;

import cas.user.Address;

public abstract class PaymentMethod {

	protected abstract String name();

	// Move
	public String display(Double amount, Address address) {
		return amount.toString() + " paid using " + name() + ", and the delivery address is " + address.toString();
	}

}
