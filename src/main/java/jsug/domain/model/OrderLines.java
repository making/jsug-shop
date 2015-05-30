package jsug.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class OrderLines implements Serializable {
    final List<OrderLine> list;

    public OrderLines() {
        this(new ArrayList<>());
    }

    public OrderLines orderId(UUID orderId) {
        int i = 0;
        for (OrderLine orderLine : list) {
            orderLine.setLineNo(++i);
            orderLine.setOrderId(orderId);
        }
        return this;
    }

    public Stream<OrderLine> stream() {
        return list.stream();
    }

    public long getTotal() {
        return list.stream()
                .mapToLong(OrderLine::getSubtotal)
                .sum();
    }
}
