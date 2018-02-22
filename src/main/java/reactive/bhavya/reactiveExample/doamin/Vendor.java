package reactive.bhavya.reactiveExample.doamin;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Comparator;

@Data
@Document
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vendor {

    @Id
    private String Id;

    private String firstName;

    private String lastName;

    public int compareTo(Vendor v){
        return  Comparator.comparing(Vendor::getFirstName)
                .thenComparing(Vendor::getLastName)
                .compare(this, v);

    }
}
