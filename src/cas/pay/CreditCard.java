package cas.pay;

public class CreditCard extends PaymentMethod {

	String number = "";
	String security = "";

	@Override
	protected String name() {
		return "Credit Card";
	}

	/**
	 *
	 * @param string The {@link String} to check if it only contains digits
	 * @return If it only contains digits
	 */
	private static boolean digit_only(String string) {
		for (int i = 0; i < string.length(); i++) {
			if (!Character.isDigit(string.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * If the credit card details are valid
	 */
	@Override
	public boolean valid() {
		return number.length() == 6 && security.length() == 3 && digit_only(number) && digit_only(security);
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

}
