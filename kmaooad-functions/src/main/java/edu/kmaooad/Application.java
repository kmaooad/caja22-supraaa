package edu.kmaooad;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories("edu.kmaooad.repositories")
public class Application implements CommandLineRunner {

//    @Autowired
//    private CommandService commandService;
//
//    @Autowired
//    private ResourceService resourceService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }


    @Override
    public void run(String... args) {
//        commandService.createCommand(0L, "createAccessRule", "");
//        resourceService.createResource(0L, 222222L, ResourceType.USER);
    }
}
