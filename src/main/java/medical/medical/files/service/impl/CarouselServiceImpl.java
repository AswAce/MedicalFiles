package medical.medical.files.service.impl;

import medical.medical.files.model.enteties.DoctorEntity;
import medical.medical.files.model.viewModels.DoctorSetViewModel;
import medical.medical.files.service.CarouselService;
import medical.medical.files.service.DoctorService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CarouselServiceImpl implements CarouselService {
    private Logger LOGGER = LoggerFactory.getLogger(CarouselServiceImpl.class);
    private final static String CRON_TIME = "*/5 * * * * *";
    private final Random random = new Random();
    private List<DoctorSetViewModel> doctorsCarousel = new ArrayList<>();
    private final ModelMapper modelMapper;


    private final DoctorService doctorService;

    public CarouselServiceImpl(ModelMapper modelMapper, DoctorService doctorService) {
        this.modelMapper = modelMapper;
        this.doctorService = doctorService;
    }

//    @PostConstruct
//    public void afterInitialize() {
//        if (doctorsCarousel.size() < 3) {
//            throw new IllegalArgumentException("Sorry, but you must have at least 3 doctors...");
//        }
//    }

    @Scheduled(cron = CRON_TIME)
    public void refresh() {
        int count = (int) this.doctorService.getCount();
        if (count > 0) {


            doctorsCarousel.clear();
            for (int i = 0; i < 3; i++) {

                int random = checkRandomNumber(getRandomNumber());
                DoctorEntity byIdSetViewDoctor1 = doctorService.findByIdSetViewDoctor(random);
                if (byIdSetViewDoctor1 != null) {
                    DoctorSetViewModel byIdSetViewDoctor = this.modelMapper.
                            map(byIdSetViewDoctor1, DoctorSetViewModel.class);
                    doctorsCarousel.add(byIdSetViewDoctor);
                }


            }
        }
    }


    private int getRandomNumber() {

        return random.nextInt((int) (doctorService.getCount() - 1 + 1)) + 1;


    }

    @Override
    public List<DoctorSetViewModel> getCarouselDoctors() {
        return this.doctorsCarousel;
    }

    private int checkRandomNumber(int random) {
        DoctorEntity viewDoctor = doctorService.findByIdSetViewDoctor(random);

        if (viewDoctor == null) {
            random = checkRandomNumber(getRandomNumber());

        }

//        if (doctorsCarousel.size() > 1) {
//
//            for (DoctorSetViewModel carouselDoctor : getCarouselDoctors()) {
//                if (carouselDoctor.getId() == random) {
//                    random = checkRandomNumber(getRandomNumber());
//
//                }
//            }
//        }
////        if (doctorsCarousel.size() == 1 && viewDoctor != null) {
//
//            if (getCarouselDoctors().get(0).getId() == viewDoctor.getId()) {
//                random = checkRandomNumber(getRandomNumber());
//            }
//
//
//        } else if (doctorsCarousel.isEmpty() && viewDoctor == null) {
//            random = checkRandomNumber(getRandomNumber());
//        }

        return random;
    }
}
