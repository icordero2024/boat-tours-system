package com.community.guides.service;

import com.community.guides.model.CreateGuideRequest;
import com.community.guides.model.Guide;
import com.community.guides.repository.GuideRepository;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class GuideService {

    private final GuideRepository guideRepository;

    public GuideService(GuideRepository guideRepository) {
        this.guideRepository = guideRepository;
    }

    public Guide createGuide(CreateGuideRequest request) {
        validate(request);
        return guideRepository.save(request);
    }

    public List<Guide> listGuides() {
        return guideRepository.findAll();
    }

    private void validate(CreateGuideRequest request) {
        if (request == null) {
            throw new IllegalArgumentException("El body del guía es obligatorio");
        }

        if (isBlank(request.getName())) {
            throw new IllegalArgumentException("El nombre del guía es obligatorio");
        }

        if (isBlank(request.getCommunity())) {
            throw new IllegalArgumentException("La comunidad es obligatoria");
        }

        if (isBlank(request.getBoatName())) {
            throw new IllegalArgumentException("El nombre de la lancha es obligatorio");
        }
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
