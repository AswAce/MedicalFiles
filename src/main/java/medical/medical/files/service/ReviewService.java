package medical.medical.files.service;

import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.serviceModels.AddReviewServiceModel;
import medical.medical.files.model.viewModels.AllReviewStartView;
import medical.medical.files.model.viewModels.ReviewViewModel;

import java.util.Set;

public interface ReviewService {
    void addFeedback(AddReviewServiceModel addReviewServiceModel);

    Set<ReviewViewModel> findAllForDepartments(MedicalBranchesEnum medicalBranchesEnum);

    AllReviewStartView getAllReviewsRatingForDepartment(MedicalBranchesEnum name);

    Set<ReviewViewModel> findAllByDoctorId(long id);

    AllReviewStartView getAllReviewsForDoctor(long id);

    Set<ReviewViewModel> findAllByPatientId(long id);

    int countAll();

    void deleteReview(long id);

   Set <ReviewViewModel> findAll();
}
