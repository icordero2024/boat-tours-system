package tours.service.repository;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.jpa.repository.JpaRepository;
import tours.service.model.Tour;

import java.util.List;

/**
 * Repository layer for Tour entity.
 *
 * This interface is responsible for all database operations related to Tour.
 * We DO NOT write SQL manually — Micronaut Data generates it automatically.
 */
@Repository
public interface TourRepository extends JpaRepository<Tour, Long> {

    /**
     * Returns all tours stored in the database.
     * 
     * This method is already provided by JpaRepository,
     * but we declare it here for clarity.
     */
    @Override
    List<Tour> findAll();

    /**
     * Saves a Tour into the database.
     * 
     * Also already provided by JpaRepository.
     */
    @Override
    <S extends Tour> S save(S entity);
}