package jsug.domain.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
public class Goods implements Serializable {
    private UUID goodsId;
    private String goodsName;
    private String description;
    private Category category;
    private int price;
}
