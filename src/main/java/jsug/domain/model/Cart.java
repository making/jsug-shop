package jsug.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Data
@AllArgsConstructor
public class Cart implements Serializable {
    private final OrderLines orderLines;

    public Cart() {
        this(new OrderLines());
    }

    public Cart add(OrderLine orderLine) {
        // 対象の商品が既に買い物カゴに入っているか確認
        Optional<OrderLine> opt = orderLines.stream().filter(x ->
                Objects.equals(x.getGoods().getGoodsId(), orderLine.getGoods().getGoodsId()))
                .findFirst();
        if (opt.isPresent()) {
            // 入っていたら数量を増やす
            OrderLine line = opt.get();
            line.setQuantity(line.getQuantity() + orderLine.getQuantity());
        } else {
            orderLines.list.add(orderLine);
        }
        return this;
    }

    public Cart clear() {
        orderLines.list.clear();
        return this;
    }

    public Cart remove(Set<Integer> lineNo) {
        Iterator<OrderLine> iterator = getOrderLines().getList().iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            iterator.next();
            if (lineNo.contains(i)) {
                iterator.remove();
            }
        }
        return this;
    }

    public boolean isEmpty() {
        return orderLines.list.isEmpty();
    }
}
