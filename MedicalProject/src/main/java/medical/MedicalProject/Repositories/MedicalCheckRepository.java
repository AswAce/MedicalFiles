package medical.MedicalProject.Repositories;


import medical.MedicalProject.entities.MedicalCheck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicalCheckRepository extends JpaRepository<MedicalCheck,Long> {
}
