package jsug.domain.model;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class Category implements Serializable {
    private int categoryId;
    private String categoryName;
}
