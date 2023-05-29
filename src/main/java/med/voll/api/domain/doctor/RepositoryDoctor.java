package med.voll.api.domain.doctor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryDoctor extends JpaRepository<Doctor, Long>  {

	Page<Doctor> findByActiveTrue(Pageable website);
}