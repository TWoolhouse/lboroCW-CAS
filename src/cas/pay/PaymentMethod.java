package cas.pay;

import cas.user.Address;

public abstract class PaymentMethod {

	/**
	 *
	 * @return The name of the payment method
	 */
	protected abstract String name();

	/**
	 *
	 * @return If the method information is valid
	 */
	public abstract boolean valid();

	/**
	 *
	 * @param amount  The price on the transaction.
	 * @param address The billing address.
	 * @return A {@link String} to display to the user.
	 */
	public String display(Double amount, Address address) {
		return amount.toString() + " paid using " + name() + ", and the delivery address is "
				+ address.toString();
	}

}
