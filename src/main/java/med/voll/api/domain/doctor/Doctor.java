package med.voll.api.domain.doctor;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.address.Address;

@Table(name = "Doctors")
@Entity(name = "Doctors")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")

public class Doctor {
//	  @Data
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String email;
	private String phone;
	private String DNI;
	private Boolean active;
	
	@Embedded
	private Address address;

	@Enumerated(EnumType.STRING)
	private Specialization specialization;
	
	public Doctor() {}

	public Doctor(MedicalDataRegistry medicalDataRegistry) {
		this.name = medicalDataRegistry.name();
		this.email = medicalDataRegistry.email();
		this.DNI = medicalDataRegistry.DNI();
		this.specialization = medicalDataRegistry.specialization();
		this.address = new Address(medicalDataRegistry.address());
		this.phone = medicalDataRegistry.phone();
		this.active = true;}

	// Getters and Setters
	
	public String getName() {return name;}
	public void setName(String name) {this.name = name;}
	public String getEmail() {return email;}
	public void setEmail(String email) {this.email = email;}
	public String getPhone() {return phone;}
	public void setPhone(String phone) {this.phone = phone;}
	public Address getAddress() {return address;}
	public void setAddress(Address address) {this.address = address;}
	public String getDNI() {return DNI;}
	public void setDNI(String dNI) {DNI = dNI;}
	public Specialization getSpecialization() {return specialization;}
	public void setSpecialization(Specialization specialization) {this.specialization = specialization;}
	public Long getId() {return id;}

	public void setActive(Boolean active){this.active = active;};
	public Boolean getActive() {return active;};
	
	
//	@RequestBody @Valid
	public void updateData( UpdateDataDoctors updateData) {
		if (updateData.name() != null) {
			this.name = updateData.name();
		}
		if (updateData.phone() != null) {
			this.phone = updateData.phone();
		}
		if (updateData.address() != null) {
			this.address = address.updateAddress(updateData.address());
		}}

	public void inactiveDoctor() {
		this.setActive(false);
	}
}