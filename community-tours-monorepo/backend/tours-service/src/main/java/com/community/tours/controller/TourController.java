package com.community.tours.controller;

import com.community.tours.model.CreateTourRequest;
import com.community.tours.model.Tour;
import com.community.tours.service.TourService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import java.util.List;
import java.util.Map;

@ExecuteOn(TaskExecutors.BLOCKING)
@Controller("/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Tour> listTours() {
        return tourService.listTours();
    }

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> createTour(@Body CreateTourRequest request) {
        try {
            Tour createdTour = tourService.createTour(request);
            return HttpResponse.created(createdTour);
        } catch (IllegalArgumentException exception) {
            return HttpResponse.badRequest(Map.of("message", exception.getMessage()));
        }
    }
}