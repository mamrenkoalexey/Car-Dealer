package wit.mamrenko.carDealer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import wit.mamrenko.carDealer.repository.RepositoryCarModel;
import wit.mamrenko.carDealer.repository.RepositoryOffer;
import wit.mamrenko.carDealer.repository.RepositoryProducer;

@SpringBootApplication
public class CarDealerApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(CarDealerApplication.class, args);
    }
    @Autowired
    private RepositoryCarModel repositoryCarModel;

    @Autowired
    private RepositoryProducer repositoryProducer;

    @Autowired
    private RepositoryOffer repositoryOffer;

    @Override
    public void run(String... args) throws Exception {
    }

    @Bean
    public CommonsRequestLoggingFilter requestLoggingFilter() {
        CommonsRequestLoggingFilter loggingFilter = new CommonsRequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setIncludeHeaders(false);
        return loggingFilter;
    }


}
