package cas.user;

public class Address {
	String number;
	String postcode;
	String city;

	public Address(String number, String postcode, String city) {
		this.number = number;
		this.postcode = postcode;
		this.city = city;
	}

	public String toString() {
		return number + " " + city + " " + postcode;
	}

}
