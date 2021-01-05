package medical.MedicalProject.Repositories;

import medical.MedicalProject.entities.BloodResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository

public interface BloodResultRepository extends JpaRepository<BloodResult, Long> {

//    @Query("from blood_result r  where r.health_profile_id = :id")
//     BloodResult findByHealthProfileId(@Param("id") int id);


   ArrayList<BloodResult>  findByHealthProfileIdEquals(long id);
}
