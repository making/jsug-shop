package jsug.domain.repository.order;

import jsug.domain.model.Order;
import jsug.domain.repository.SqlFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.UUID;

@Repository
@Transactional
public class OrderRepository {
    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    SqlFinder sql;

    public Order create(Order order) {
        UUID orderId = UUID.randomUUID();
        order.setOrderId(orderId);
        order.getOrderLines().orderId(orderId);

        SqlParameterSource source = new MapSqlParameterSource()
                .addValue("orderId", orderId.toString())
                .addValue("email", order.getEmail())
                .addValue("orderDate", Date.valueOf(order.getOrderDate()));

        jdbcTemplate.update(sql.get("sql/order/create.sql"), source);
        jdbcTemplate.batchUpdate(sql.get("sql/orderLine/create.sql"),
                order.getOrderLines().stream()
                        .map(orderLine -> new MapSqlParameterSource()
                                .addValue("orderId", orderLine.getOrderId().toString())
                                .addValue("lineNo", orderLine.getLineNo())
                                .addValue("goodsId", orderLine.getGoods().getGoodsId().toString())
                                .addValue("quantity", orderLine.getQuantity()))
                        .toArray(SqlParameterSource[]::new));
        return order;
    }
}
