package reactive.bhavya.reactiveExample.controllers;

import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactive.bhavya.reactiveExample.doamin.Vendor;
import reactive.bhavya.reactiveExample.repositories.VendorRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;

@RestController
public class VendorController {

    private final VendorRepository vendorRepository;

    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping("/api/v1/vendors")
    Flux<Vendor> list(){
        return vendorRepository.findAll();
    }

    @GetMapping("/api/v1/vendors/{id}")
    Mono<Vendor> getById(@PathVariable String id){
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/v1/vendors")
    public Mono<Void> create(@RequestBody Publisher<Vendor> vendorStream){
        return vendorRepository.saveAll(vendorStream).then();
    }

    @PutMapping("/api/v1/vendors/{id}")
    Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor){
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/api/v1/vendors/{id}")
    Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor vendor1 = vendorRepository.findById(id).block();
        int change = 0; // not required , but done for testing
        if (vendor.getFirstName() != vendor1.getFirstName() &&
                vendor.getFirstName() != null) {
            vendor1.setFirstName(vendor.getFirstName());
            change = 1;
        }
        if (vendor.getLastName() != vendor1.getLastName() &&
                vendor.getLastName() != null) {
            vendor1.setLastName(vendor.getLastName());
            change = 1;
        }

        if(change == 1)
            return vendorRepository.save(vendor1);
        else
            return Mono.just(vendor1);
    }


}
