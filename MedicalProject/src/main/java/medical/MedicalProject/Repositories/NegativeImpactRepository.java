package medical.MedicalProject.Repositories;

import medical.MedicalProject.entities.HealthProfile;
import medical.MedicalProject.entities.NegativeImpact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegativeImpactRepository extends JpaRepository<NegativeImpact, Long> {
    NegativeImpact findByType(String type);
}
