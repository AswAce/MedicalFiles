package medical.MedicalProject.Repositories;


import medical.MedicalProject.entities.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Location findByPartOfTheBody(String part);


}
