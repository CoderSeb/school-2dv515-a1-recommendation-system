package lnu.sa224ny.backendassignment1;

import lnu.sa224ny.backendassignment1.repositories.MovieRepository;
import lnu.sa224ny.backendassignment1.repositories.RatingRepository;
import lnu.sa224ny.backendassignment1.repositories.UserRepository;
import lnu.sa224ny.backendassignment1.utils.CSVHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@PropertySource({"classpath:application-${spring.profiles.active}.properties"})
public class BackendAssignment1Application {
    private static final Logger logger = LoggerFactory.getLogger(BackendAssignment1Application.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RatingRepository ratingRepository;

    public static void main(String[] args) {
        SpringApplication.run(BackendAssignment1Application.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() throws Exception {
        logger.info("Loading CSV into DB...");
        try {
            CSVHandler csvHandler = new CSVHandler("src/files/movies_example", userRepository, movieRepository, ratingRepository);
            csvHandler.loadCSVsIntoDb();
            logger.info("Finished...");
        } catch (Exception e) {
            logger.warn(e.getMessage());
        }


    }

}
