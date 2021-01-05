package medical.MedicalProject.Repositories;


import medical.MedicalProject.entities.MedicineBranch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineBranchRepository extends JpaRepository<MedicineBranch, Long> {
    MedicineBranch findByName(String medicineBranch);
}
