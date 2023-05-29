package med.voll.api.controller;

import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.address.AddressData;
import med.voll.api.domain.doctor.Doctor;
import med.voll.api.domain.doctor.DoctorDataList;
import med.voll.api.domain.doctor.MedicalDataRegistry;
import med.voll.api.domain.doctor.MedicalResponseData;
import med.voll.api.domain.doctor.RepositoryDoctor;
import med.voll.api.domain.doctor.UpdateDataDoctors;

@RestController
@RequestMapping("/doctors")

public class DoctorController {
	
	@Autowired
	private RepositoryDoctor repositoryDoctor;
	
	// Creating Doctor
	@PostMapping
	public ResponseEntity<MedicalResponseData> registerDoctor(@RequestBody @Valid MedicalDataRegistry medicalDataRegistry, UriComponentsBuilder uriComponentsBuilder) {
		Doctor doctor = repositoryDoctor.save(new Doctor(medicalDataRegistry));
		MedicalResponseData medicalResponseData = new MedicalResponseData(
				doctor.getId(), 
				doctor.getEmail(),
				doctor.getDNI(), 
				doctor.getPhone(),
				doctor.getName(),
				doctor.getSpecialization().toString(), 
				new AddressData(doctor.getAddress().getStreet(), doctor.getAddress().getTown(), doctor.getAddress().getState(), doctor.getAddress().getNumber()));	
		URI url = uriComponentsBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();
		return ResponseEntity.created(url).body(medicalResponseData);
		}

	
	// Getting all the doctors
	@GetMapping
	public ResponseEntity<Page<DoctorDataList>> doctorList(@PageableDefault(page = 1, size = 3, sort = "name") Pageable website) {
		return ResponseEntity.ok(repositoryDoctor.findAll(website).map(DoctorDataList::new)) ;}
	
	
	//Getting all the active doctors
	@RequestMapping("/activeList")
	@GetMapping
	@Transactional
	public ResponseEntity<Page<DoctorDataList>> activeDoctors(@PageableDefault(sort = "active") Pageable website) {
		return ResponseEntity.ok(repositoryDoctor.findByActiveTrue(website).map(DoctorDataList::new));}
	
	
	@GetMapping("/oneDoctor/{id}")
	public ResponseEntity<MedicalResponseData> getOneDoctor(@PathVariable Long id) {
		Doctor doctor = repositoryDoctor.getReferenceById(id);
		var dataDoctor = new MedicalResponseData(doctor.getId(), 
				doctor.getEmail(),
				doctor.getDNI(), 
				doctor.getPhone(), 
				doctor.getName(),
				doctor.getSpecialization().toString(), 	
				new AddressData(doctor.getAddress().getStreet(), doctor.getAddress().getTown(), doctor.getAddress().getState(), 
						doctor.getAddress().getNumber()));	
		return ResponseEntity.ok(dataDoctor);
	}

	
	// Updating data doctors
	@PutMapping
	@Transactional
	public ResponseEntity<MedicalResponseData> updateDoctor(@RequestBody @Valid UpdateDataDoctors updateData) {
		Doctor doctor = repositoryDoctor.getReferenceById(updateData.id());
		doctor.updateData(updateData);
		
		return ResponseEntity.ok(new MedicalResponseData(
				doctor.getId(), 
				doctor.getEmail(),
				doctor.getDNI(), 
				doctor.getPhone(), 
				doctor.getName(),
				doctor.getSpecialization().toString(), 
				new AddressData(doctor.getAddress().getStreet(), 
						doctor.getAddress().getTown(), 
						doctor.getAddress().getState(), 
						doctor.getAddress().getNumber())));}
	
	//Inactivating Doctors
	@Transactional
	@PutMapping("desactivated/{id}")
	public void desactivateDoctor(@PathVariable Long id) {
		Doctor doctor = repositoryDoctor.getReferenceById(id);
		doctor.inactiveDoctor();}
	
	
	// --------------------------------- List of inactive doctors delete
	@DeleteMapping("/desactivated/{id}")
	@Transactional
	public void deletePasiveDoctor(@PathVariable Long id) {
		Doctor doctor = repositoryDoctor.getReferenceById(id);
		if(!doctor.getActive()) {
			repositoryDoctor.delete(doctor);
		}
	}
	
	
	// ---------------------------------  Delete from DDBB
	@DeleteMapping("/{id}")
	@Transactional
	public ResponseEntity deleteDoctor(@PathVariable Long id) {
		Doctor doctor = repositoryDoctor.getReferenceById(id);
		repositoryDoctor.delete(doctor);
		return ResponseEntity.noContent().build();
	}
}