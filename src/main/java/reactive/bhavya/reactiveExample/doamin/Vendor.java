package reactive.bhavya.reactiveExample.doamin;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
}
