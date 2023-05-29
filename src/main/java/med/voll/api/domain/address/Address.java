package med.voll.api.domain.address;

import jakarta.persistence.Embeddable;
import lombok.Data;

@Embeddable

public @Data class Address {
	
	private String town;
	private String number;
	private String state;
	private String street;

	public Address() {}
	
	public Address(AddressData address) {
		this.number = address.number();
		this.state = address.state();
		this.street = address.street();
		this.town = address.town();}

	public String getTown() {return town;}
	public void setTown(String town) {this.town = town;}
	public String getNumber() {return number;}
	public void setNumber(String number) {this.number = number;}
	public String getState() {return state;}
	public void setState(String state) {this.state = state;}
	public String getStreet() {return street;}
	public void setStreet(String street) {this.street = street;}

	public Address updateAddress(AddressData address) {
		this.number = address.number();
		this.state = address.state();
		this.street = address.street();
		this.town = address.town();
		return this;}
}