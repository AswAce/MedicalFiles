package medical.medical.files.service.impl;

import medical.medical.files.config.currentUser.IAuthenticationFacade;
import medical.medical.files.exeptions.PatientNoFoundException;
import medical.medical.files.model.enteties.PatientEntity;
import medical.medical.files.model.enteties.ReviewsEntity;
import medical.medical.files.model.enteties.UserEntity;
import medical.medical.files.model.enums.MedicalBranchesEnum;
import medical.medical.files.model.serviceModels.AddReviewServiceModel;
import medical.medical.files.model.viewModels.AllReviewStartView;
import medical.medical.files.model.viewModels.ReviewViewModel;
import medical.medical.files.repositorie.ReviewRepository;
import medical.medical.files.service.DoctorService;
import medical.medical.files.service.ReviewService;
import medical.medical.files.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final ModelMapper modelMapper;
    private final ReviewRepository reviewRepository;
    private final DoctorService doctorService;
    private final IAuthenticationFacade authenticationFacade;
    private final UserService userService;

    public ReviewServiceImpl(ModelMapper modelMapper, ReviewRepository reviewRepository, DoctorService doctorService, IAuthenticationFacade authenticationFacade, UserService userService) {
        this.modelMapper = modelMapper;
        this.reviewRepository = reviewRepository;

        this.doctorService = doctorService;
        this.authenticationFacade = authenticationFacade;

        this.userService = userService;
    }

    @Override
    public void addFeedback(AddReviewServiceModel addReviewServiceModel) {

        ReviewsEntity reviewsEntity = this.modelMapper.map(addReviewServiceModel, ReviewsEntity.class);
        reviewsEntity.
                setDoctor(this.doctorService.
                        findByName(addReviewServiceModel.getDoctorName(),addReviewServiceModel.getDepartment()));
        reviewsEntity.setPatient(isUSerPatientReturn());
        reviewsEntity.setLocalDateTime(LocalDateTime.now());
        if (reviewsEntity.getPatient() == null) {
            throw new PatientNoFoundException("Patient not found you can't add review");
        }
        this.reviewRepository.save(reviewsEntity);
    }

    @Override
    public Set<ReviewViewModel> findAllForDepartments(MedicalBranchesEnum medicalBranchesEnum) {
        Set<ReviewsEntity> allByDepartment = this.reviewRepository.findAllByDepartmentOrderByLocalDateTimeAsc(medicalBranchesEnum);
        Set<ReviewViewModel> reviewViewModels = allByDepartment.stream().
                map(reviewsEntity -> {
                    ReviewViewModel map = this.modelMapper.map(reviewsEntity, ReviewViewModel.class);

                    String format = reviewsEntity.getLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));
                    map.setLocalDateTime(format);
                    map.setLeftRating(5 - map.getRating());
                    return map;
                }).collect(Collectors.toSet());

        return reviewViewModels;
    }

    @Override
    public AllReviewStartView getAllReviewsRatingForDepartment(MedicalBranchesEnum name) {
        AllReviewStartView allReviewStartView = new AllReviewStartView();
        Set<ReviewsEntity> allByDepartmentOrderByLocalDateTimeAsc = this.reviewRepository.findAllByDepartmentOrderByLocalDateTimeAsc(name);
        if (allByDepartmentOrderByLocalDateTimeAsc != null && allByDepartmentOrderByLocalDateTimeAsc.size() > 0) {
            int sum = allByDepartmentOrderByLocalDateTimeAsc.stream().mapToInt(reviewsEntity -> reviewsEntity.getRating()).sum();

            allReviewStartView.setRating(sum / allByDepartmentOrderByLocalDateTimeAsc.size());
            allReviewStartView.setLeftField(5 - allReviewStartView.getRating());
        } else {
            allReviewStartView.setRating(5);

        }

        return allReviewStartView;
    }

    @Override
    public Set<ReviewViewModel> findAllByDoctorId(long id) {
        Set<ReviewsEntity> byDoctorId = this.reviewRepository.findByDoctorId(id);
        Set<ReviewViewModel> reviewViewModelStream = byDoctorId.stream().
                map(reviewsEntity -> {
                    ReviewViewModel map = modelMapper.map(reviewsEntity, ReviewViewModel.class);
                    map.setLeftRating(5 - map.getRating());
                    return map;
                })
                .collect(Collectors.toSet());

        return reviewViewModelStream;
    }

    @Override
    public AllReviewStartView getAllReviewsForDoctor(long id) {
        Set<ReviewViewModel> allByDoctorId = findAllByDoctorId(id);
        int sum = allByDoctorId.stream().mapToInt(ReviewViewModel::getRating).sum();
        AllReviewStartView allReviewStartView = new AllReviewStartView();
        if (allByDoctorId.size() > 0) {
            allReviewStartView.setRating(sum / allByDoctorId.size());
            allReviewStartView.setLeftField(5 - allReviewStartView.getRating());
        }
        allReviewStartView.setRating(5);
        allReviewStartView.setLeftField(0);
        return allReviewStartView;
    }

    @Override
    public Set<ReviewViewModel> findAllByPatientId(long id) {
        Set<ReviewsEntity> byPatientId = this.reviewRepository.findByPatientId(id);
        return byPatientId.stream().
                map(reviewsEntity -> {
                    ReviewViewModel map = modelMapper.map(reviewsEntity, ReviewViewModel.class);
                    map.setLeftRating(5 - map.getRating());
                    return map;
                })
                .collect(Collectors.toSet());

    }

    @Override
    public int countAll() {
        return (int) this.reviewRepository.count();
    }

    @Override
    public void deleteReview(long id) {
        this.reviewRepository.deleteById(id);
    }

    @Override
    public Set<ReviewViewModel> findAll() {
        List<ReviewsEntity> all = this.reviewRepository.
                findAll();
        return all.
                stream().
                map(reviewsEntity -> modelMapper.
                        map(reviewsEntity, ReviewViewModel.class)).sorted(Comparator.comparing(ReviewViewModel::getLocalDateTime)).
                collect(Collectors.toCollection(LinkedHashSet::new));

    }

    private PatientEntity isUSerPatientReturn() {

        UserEntity byUserName = this.userService.findByUserName(this.authenticationFacade.getAuthentication().getName());
        if (byUserName.getPatientEntity() != null) {
            return byUserName.getPatientEntity();
        }
        return null;

    }
}

