package med.voll.api.domain.doctor;

public record DoctorDataList(Long id, String name,String email, String DNI, String specialization) {
	
public DoctorDataList(Doctor doctor){
	this(doctor.getId(), doctor.getName(), doctor.getEmail(), doctor.getDNI(), doctor.getSpecialization().toString());}
}