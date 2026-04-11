package com.community.tours.service;

import com.community.tours.model.CreateTourRequest;
import com.community.tours.model.Tour;
import com.community.tours.repository.TourRepository;
import jakarta.inject.Singleton;

import java.math.BigDecimal;
import java.util.List;

@Singleton
public class TourService {

    private final TourRepository tourRepository;

    public TourService(TourRepository tourRepository) {
        this.tourRepository = tourRepository;
    }

    public Tour createTour(CreateTourRequest request) {
        validate(request);
        return tourRepository.save(request);
    }

    public List<Tour> listTours() {
        return tourRepository.findAll();
    }

    private void validate(CreateTourRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("El body del tour es obligatorio");
        }

        if (isBlank(request.getName())) {
            throw new IllegalArgumentException("El nombre del tour es obligatorio");
        }

        if (isBlank(request.getLocation())) {
            throw new IllegalArgumentException("La ubicación del tour es obligatoria");
        }

        BigDecimal price = request.getPrice();
        if (price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor que cero");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
