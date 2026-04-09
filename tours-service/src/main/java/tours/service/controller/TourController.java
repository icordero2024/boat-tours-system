package tours.service.controller;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;
import tours.service.controller.dto.TourRequest;
import tours.service.model.Tour;
import tours.service.service.TourService;

import java.util.List;

/**
 * Controller layer for Tour endpoints.
 *
 * This class ONLY handles HTTP requests and responses.
 * It delegates all business logic to the service layer.
 */
@Controller("/tours")
public class TourController {

    private final TourService tourService;

    public TourController(TourService tourService) {
        this.tourService = tourService;
    }

    /**
     * POST /tours
     *
     * Creates a new tour.
     * Receives JSON body and passes data to the service.
     */
    @Post
    public HttpResponse<?> createTour(@Body TourRequest request) {
        try {
            Tour created = tourService.createTour(
                request.getName(),
                request.getLocation(),
                request.getPrice()
            );

            return HttpResponse.created(created);

        } catch (IllegalArgumentException e) {
            return HttpResponse.badRequest(e.getMessage());
        }
    }

    /**
     * GET /tours
     *
     * Returns all tours.
     */
    @Get
    public HttpResponse<List<Tour>> getAllTours() {
        List<Tour> tours = tourService.getAllTours();
        return HttpResponse.ok(tours);
    }
}