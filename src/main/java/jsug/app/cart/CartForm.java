package jsug.app.cart;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;

@Data
public class CartForm implements Serializable {
    @NotEmpty
    @NotNull
    private Set<Integer> lineNo;
}
