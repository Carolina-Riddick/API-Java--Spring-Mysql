package med.voll.api.domain.address;

import jakarta.validation.constraints.NotBlank;

public record AddressData(
		@NotBlank String street, 
		@NotBlank String town, 
		@NotBlank String state, 
		@NotBlank String number) {
}