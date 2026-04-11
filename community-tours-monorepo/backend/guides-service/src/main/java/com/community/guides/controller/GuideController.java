package com.community.guides.controller;

import com.community.guides.model.CreateGuideRequest;
import com.community.guides.model.Guide;
import com.community.guides.service.GuideService;
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
@Controller("/guides")
public class GuideController {

    private final GuideService guideService;

    public GuideController(GuideService guideService) {
        this.guideService = guideService;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public List<Guide> listGuides() {
        return guideService.listGuides();
    }

    @Post(consumes = MediaType.APPLICATION_JSON, produces = MediaType.APPLICATION_JSON)
    public HttpResponse<?> createGuide(@Body CreateGuideRequest request) {
        try {
            Guide createdGuide = guideService.createGuide(request);
            return HttpResponse.created(createdGuide);
        } catch (IllegalArgumentException exception) {
            return HttpResponse.badRequest(Map.of("message", exception.getMessage()));
        }
    }
}