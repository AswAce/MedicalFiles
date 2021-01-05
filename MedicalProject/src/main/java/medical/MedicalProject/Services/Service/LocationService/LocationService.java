package medical.MedicalProject.Services.Service.LocationService;

import medical.MedicalProject.Services.SeedDto.SeedLocation.LocationSeedDto;
import medical.MedicalProject.entities.Location;

import java.util.ArrayList;

public interface LocationService {

    void addLocation(LocationSeedDto location);

    ArrayList<Location> getAllLocations();
    void  addLocationsFromFile(LocationSeedDto[] locationSeedDtos);

    Location getLocationByName(String locationsPart);
}
