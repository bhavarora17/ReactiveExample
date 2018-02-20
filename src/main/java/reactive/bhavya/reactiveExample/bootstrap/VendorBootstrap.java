package reactive.bhavya.reactiveExample.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactive.bhavya.reactiveExample.doamin.Category;
import reactive.bhavya.reactiveExample.doamin.Vendor;
import reactive.bhavya.reactiveExample.repositories.CategoryRepository;
import reactive.bhavya.reactiveExample.repositories.VendorRepository;

@Component
public class VendorBootstrap implements CommandLineRunner {

    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    @Autowired
    public VendorBootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public void run(String...args) throws Exception {

        if(categoryRepository.count().block() == 0){
            //load data
            System.out.println("##### LOADING DATA ON BOOTSTRAP");
            categoryRepository.save(Category.builder()
                    .description("Fruits").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Nuts").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Breads").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Meats").build()).block();

            categoryRepository.save(Category.builder()
                    .description("Eggs").build()).block();

            System.out.println("Loaded Categoris: " + categoryRepository.count().block());

            vendorRepository.save(Vendor.builder()
                    .firstName("Joe")
                    .lastName("Buck")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Michael")
                    .lastName("Weston")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Bhavya")
                    .lastName("Arora")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Srini")
                    .lastName("Nutalapati")
                    .build()).block();

            vendorRepository.save(Vendor.builder()
                    .firstName("Pavan")
                    .lastName("Marella")
                    .build()).block();


        }
    }
}
