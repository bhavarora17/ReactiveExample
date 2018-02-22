package reactive.bhavya.reactiveExample.CategoryControllerTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactive.bhavya.reactiveExample.controllers.CategoryController;
import reactive.bhavya.reactiveExample.doamin.Category;
import reactive.bhavya.reactiveExample.repositories.CategoryRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;

public class CategoryControllerTest {

    WebTestClient webTestClient;
    CategoryRepository categoryRepository;
    CategoryController categoryController;

    @Before
    public void setUp() throws Exception{
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();
    }

    @Test
    public void list(){
        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(Category.builder().description("Cat1").build(),
                         Category.builder().description("Cat2").build()));

        webTestClient
                .get()
                .uri("/api/v1/categories")
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    public void getById(){
        BDDMockito.given(categoryRepository.findById("sameId"))
                .willReturn(Mono.just(Category.builder().description("Catt1").build()));

        webTestClient
                .get()
                .uri("/api/v1/category/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    public void createList(){
        BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Category.builder().build()));

        Mono<Category> categoryToMono = Mono.just(Category.builder()
                .description("description here").build());

        webTestClient.post()
                .uri("/api/v1/category")
                .body(categoryToMono, Category.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void updateList(){
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(Category.builder().description("bahvya").build()));

        Mono<Category> catToMono = Mono.just(Category.builder().description("some desc").build());

        webTestClient.put()
                .uri("/api/v1/categories/someId")
                .body(catToMono, Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }


}
