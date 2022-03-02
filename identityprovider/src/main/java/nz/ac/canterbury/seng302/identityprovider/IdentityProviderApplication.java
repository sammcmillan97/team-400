package nz.ac.canterbury.seng302.identityprovider;

import nz.ac.canterbury.seng302.identityprovider.entity.User;
import nz.ac.canterbury.seng302.identityprovider.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.slf4j.Logger;

@SpringBootApplication
public class IdentityProviderApplication {

    private static final Logger log = LoggerFactory.getLogger(IdentityProviderApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(IdentityProviderApplication.class, args);
    }

    @Bean
    public CommandLineRunner demo(UserRepository repository) {
        return (args) -> {
            // save a few users
            repository.save(new User("bauerjac","Jack", "Bauer","Jack-Jack", "howdy", "HE/HIM", "jack@gmail.com", "password"));
            repository.save(new User("obrianchl","Chloe", "OBrian", "Coco", "hello", "SHE/HER", "coco@gmail.com", "password"));
            repository.save(new User("bauerkim","Kim", "Bauer", "Kiki", "heyy", "SHE/HER", "kiki@gmail.com", "password"));
            repository.save(new User("palmerdav","David", "Palmer", "Davo", "gidday", "THEY/THEM", "davo@gmail.com", "password"));
            repository.save(new User("desslermic","Michelle", "Dessler", "Shelly", "hi", "HE/HIM", "shelly@gmail.com", "password"));

            // fetch all users
            log.info("Users found with findAll():");
            log.info("-------------------------------");
            for (User user : repository.findAll()) {
                log.info(user.toString());
            }
            log.info("");

            // fetch an individual customer by ID
            User user = repository.findByUserId(1L);
            log.info("user found with findById(1L):");
            log.info("--------------------------------");
            log.info(user.toString());
            log.info("");

            // fetch customers by last name
            log.info("User found with findByUsername('palmerdav'):");
            log.info("--------------------------------------------");
            repository.findByUsername("palmerdav").toString();

            log.info("");
        };
    }
}
