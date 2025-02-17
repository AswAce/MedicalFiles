package medical.medical.files.repositorie;

import medical.medical.files.model.enteties.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    UserEntity findById(long id);

    Optional<UserEntity> findByUsername(String username);

    Optional< UserEntity> findByDoctorEntity_Id(long id);

    Optional< UserEntity> findByPatientEntity_Id(long id);
}
