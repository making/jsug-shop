package jsug.app.goods;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class AddToCartForm {
    @NotNull
    private UUID goodsId;
    @Min(1)
    @Max(50)
    private int quantity;
    private int categoryId;
}
