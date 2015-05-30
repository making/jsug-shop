package jsug.domain.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class OrderLine implements Serializable {
    private Goods goods;
    private int quantity;
    private UUID orderId;
    private int lineNo;

    public long getSubtotal() {
        return quantity * goods.getPrice();
    }
}
