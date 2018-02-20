package reactive.bhavya.reactiveExample.repositories;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactive.bhavya.reactiveExample.doamin.Category;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
