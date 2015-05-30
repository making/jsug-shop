package jsug.domain.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
public class Order implements Serializable {
    private UUID orderId;
    private String email;
    private OrderLines orderLines;
    private LocalDate orderDate;
}
