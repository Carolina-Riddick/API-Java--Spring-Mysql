package med.voll.api.domain.doctor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import med.voll.api.domain.address.AddressData;

public record MedicalDataRegistry(
		
		@NotBlank String name, 
		@NotBlank @Email String email, 
		@NotBlank @Pattern(regexp = "\\d{7,10}") String DNI, 
		@NotNull Specialization specialization, 
		@NotNull @Valid AddressData address,
		@NotBlank String phone)
		{}