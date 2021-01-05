package medical.MedicalProject.Services.Service.LocationService;

import medical.MedicalProject.Repositories.LocationRepository;
import medical.MedicalProject.Services.SeedDto.SeedLocation.LocationSeedDto;
import medical.MedicalProject.entities.Location;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;

@Service
public class LocationServiceImpl implements LocationService {


    private LocationRepository locationRepository;
    private ModelMapper modelMapper;

    @Autowired
    public LocationServiceImpl(LocationRepository locationRepository, ModelMapper modelMapper) {
        this.locationRepository = locationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void addLocation(LocationSeedDto location) {
        for (Location locationClass : getAllLocations()
        ) {
            if (locationClass.getPartOfTheBody().equals(location.getPartOfTheBody())) {
                // trhrow Error
                System.out.println("already exist");
                return;
            }

        }
        Location locationForDb = this.modelMapper.map(location, Location.class);
        this.locationRepository.saveAndFlush(locationForDb);
    }

    @Override
    public ArrayList<Location> getAllLocations() {
        return (ArrayList<Location>) this.locationRepository.findAll();
    }

    @Override
    public void addLocationsFromFile(LocationSeedDto[] locationSeedDtos) {
        Arrays.stream(locationSeedDtos).forEach(this::addLocation);
    }

    @Override
    public Location getLocationByName(String locationsPart) {
        return this.locationRepository.findByPartOfTheBody(locationsPart);
    }
}
