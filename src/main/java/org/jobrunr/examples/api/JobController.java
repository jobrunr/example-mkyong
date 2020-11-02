package org.jobrunr.examples.api;

import org.jobrunr.examples.services.SampleJobService;
import org.jobrunr.scheduling.JobScheduler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

import static java.time.temporal.ChronoUnit.HOURS;

@RestController
public class JobController {

    private final JobScheduler jobScheduler;
    private final SampleJobService sampleService;

    public JobController(JobScheduler jobScheduler, SampleJobService sampleService) {
        this.jobScheduler = jobScheduler;
        this.sampleService = sampleService;
    }

    @GetMapping("/enqueue-example-job")
    public String enqueueExampleJob(@RequestParam(value = "name", defaultValue = "World") String name) {
        jobScheduler.enqueue(() -> sampleService.executeSampleJob("Hello " + name));
        return "Job Enqueued";
    }

    @GetMapping("/schedule-example-job")
    public String scheduleExampleJob(@RequestParam(value = "name", defaultValue = "World") String name) {
        jobScheduler.schedule(() -> sampleService.executeSampleJob("Hello " + name), Instant.now().plus(3, HOURS));
        return "Job Enqueued";
    }
}
