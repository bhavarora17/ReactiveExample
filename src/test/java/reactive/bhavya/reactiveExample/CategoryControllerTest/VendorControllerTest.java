package reactive.bhavya.reactiveExample.CategoryControllerTest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactive.bhavya.reactiveExample.controllers.VendorController;
import reactive.bhavya.reactiveExample.doamin.Vendor;
import reactive.bhavya.reactiveExample.repositories.VendorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

public class VendorControllerTest {

    WebTestClient webTestClient;
    VendorController vendorController;
    VendorRepository vendorRepository;

    @Before
    public void setUp() throws Exception {
        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);
        webTestClient = WebTestClient.bindToController(vendorController).build();

    }

    @Test
    public void list(){
        BDDMockito.given(vendorRepository.findAll())
                .willReturn(Flux.just(Vendor.builder().firstName("Bhavya").lastName("Arora").build(),
                        Vendor.builder().firstName("Jag").lastName("Narsi").build()));

        webTestClient
                .get()
                .uri("/api/v1/vendors")
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    public void getById(){
        BDDMockito.given(vendorRepository.findById("someId"))
                .willReturn(Mono.just(Vendor.builder().firstName("Bhavya").lastName("Arora").build()));

        webTestClient
                .get()
                .uri("/api/v1/vendors/someId")
                .exchange()
                .expectBody(Vendor.class);

    }

    @Test
    public void testCreateVendor(){
        BDDMockito.given(vendorRepository.saveAll(any(Publisher.class)))
                .willReturn(Flux.just(Vendor.builder().build()));

        Mono<Vendor> vendorToSaveMono = Mono.just(Vendor.builder().build());

        webTestClient
                .post()
                .uri("/api/v1/vendors/")
                .body(vendorToSaveMono, Vendor.class)
                .exchange()
                .expectStatus()
                .isCreated();
    }

    @Test
    public void testUpdateVendor(){

        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().firstName("Bhavya").build()));

        Mono<Vendor> vendorToUpdateVendor = Mono.just(Vendor.builder().firstName("Mike").lastName("Jackson").build());

        webTestClient.put()
                .uri("/api/v1/vendors/123")
                .body(vendorToUpdateVendor, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    public void testPatchVendor(){

        BDDMockito.given(vendorRepository.findById("123"))
                .willReturn(Mono.just(Vendor.builder().build()));

        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().firstName("Bhavya").build()));

        Mono<Vendor> vendorToUpdateVendor = Mono.just(Vendor.builder().firstName("Mike").lastName("Jackson").build());

        webTestClient.patch()
                .uri("/api/v1/vendors/123")
                .body(vendorToUpdateVendor, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(vendorRepository).save(any());
    }


    @Test
    public void testNotPatchVendor(){

        BDDMockito.given(vendorRepository.findById(anyString()))
                .willReturn(Mono.just(Vendor.builder().build()));

        BDDMockito.given(vendorRepository.save(any(Vendor.class)))
                .willReturn(Mono.just(Vendor.builder().build()));

        Mono<Vendor> vendorToUpdateVendor = Mono.just(Vendor.builder().build());

        webTestClient.patch()
                .uri("/api/v1/vendors/123")
                .body(vendorToUpdateVendor, Vendor.class)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(vendorRepository, Mockito.never()).save(any());
    }
}
