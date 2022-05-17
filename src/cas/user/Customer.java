package cas.user;

import cas.inv.Basket;
import cas.pay.PaymentMethod;

public class Customer extends User {

	private Basket basket;

	public Customer(String[] data) {
		super(data);
		this.basket = new Basket();
	}

	public String pay(PaymentMethod method) {
		return method.display(basket.price(), address);
	}

}
