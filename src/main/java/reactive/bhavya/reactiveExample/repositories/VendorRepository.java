package reactive.bhavya.reactiveExample.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactive.bhavya.reactiveExample.doamin.Vendor;

public interface VendorRepository extends ReactiveMongoRepository<Vendor, String> {
}
