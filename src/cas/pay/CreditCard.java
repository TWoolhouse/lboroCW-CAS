package cas.pay;

public class CreditCard extends PaymentMethod {

	String number = "";
	String security = "";

	@Override
	protected String name() {
		return "Credit Card";
	}

	private static boolean digit_only(String string) {
		for (int i = 0; i < string.length(); i++) {
			if (!Character.isDigit(string.charAt(i)))
				return false;
		}
		return true;
	}

	@Override
	public boolean valid() {
		System.out.println(number);
		System.out.println(security);
		System.out.println(digit_only(number));
		System.out.println(digit_only(security));
		return number.length() == 6 && security.length() == 3 && digit_only(number) && digit_only(security);
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

}
