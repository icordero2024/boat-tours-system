package tours.service.service;

import jakarta.inject.Singleton;
import tours.service.model.Tour;
import tours.service.repository.TourRepository;

import java.util.List;

/**
 * Service layer for Tour.
 *
 * This class contains the business logic of the application.
 * It acts as an intermediary between the controller and the repository.
 */
@Singleton
public class TourService {

    private final TourRepository tourRepository;

    /**
     * Constructor injection (recommended way in Micronaut).
     */
    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    /**
     * Creates a new Tour after validating input data.
     *
     * @param name Tour name
     * @param location Tour location
     * @param price Tour price
     * @return Saved Tour
     */
    public Tour createTour(String name, String location, double price) {

        // 🔹 Validations (business rules)
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Tour name must not be empty");
        }

        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Tour location must not be empty");
        }

        if (price <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }

        // 🔹 Create entity
        Tour tour = new Tour();
        tour.setName(name);
        tour.setLocation(location);
        tour.setPrice(price);

        // 🔹 Save using repository
        return tourRepository.save(tour);
    }

    /**
     * Returns all tours from the database.
     *
     * @return list of tours
     */
    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }
}