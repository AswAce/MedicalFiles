package medical.MedicalProject.Repositories;

import medical.MedicalProject.entities.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthProfileRepository extends JpaRepository<HealthProfile,Long> {}
