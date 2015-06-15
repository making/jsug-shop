package jsug.domain.repository.order;

import jsug.domain.model.Goods;
import jsug.domain.model.Order;
import jsug.domain.model.OrderLine;
import jsug.domain.model.OrderLines;
import jsug.domain.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = {
        "/sql/drop-tables.sql",
        "/db/migration/V1__create-schema.sql",
        "/sql/insert-orders.sql"
}, config = @SqlConfig(encoding = "UTF-8"))
public class OrderRepositoryTest {
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void testCreate() throws Exception {
        Goods goods1 = Goods.builder()
                .goodsId(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76840"))
                .build();
        Goods goods2 = Goods.builder()
                .goodsId(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76841"))
                .build();

        Order order = Order.builder()
                .email("demo1@example.com")
                .orderDate(LocalDate.of(2015, 4, 20))
                .orderLines(new OrderLines(Arrays.asList(
                        OrderLine.builder()
                                .goods(goods1)
                                .quantity(2)
                                .build(),
                        OrderLine.builder()
                                .goods(goods2)
                                .quantity(1)
                                .build())))
                .build();

        Order ordered = orderRepository.create(order);
        UUID orderId = ordered.getOrderId();

        assertThat(ordered.getOrderId(), is(notNullValue()));
        assertThat(ordered.getEmail(), is("demo1@example.com"));
        assertThat(ordered.getOrderDate(), is(LocalDate.of(2015, 4, 20)));
        assertThat(ordered.getOrderLines().getList(), hasSize(2));
        assertThat(ordered.getOrderLines().getList().get(0).getGoods().getGoodsId(), is(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76840")));
        assertThat(ordered.getOrderLines().getList().get(0).getOrderId(), is(orderId));
        assertThat(ordered.getOrderLines().getList().get(1).getGoods().getGoodsId(), is(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76841")));
        assertThat(ordered.getOrderLines().getList().get(1).getOrderId(), is(orderId));
    }
}