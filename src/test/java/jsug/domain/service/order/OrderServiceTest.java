package jsug.domain.service.order;

import jsug.domain.TestConfig;
import jsug.domain.model.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


import java.time.LocalDate;
import java.util.Collections;
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
public class OrderServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Autowired
    OrderService orderService;

    @Test
    public void testPurchase() throws Exception {
        Cart cart = new Cart();
        OrderLine ol1 = OrderLine.builder()
                .quantity(1)
                .goods(Goods.builder().goodsId(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76840")).build())
                .build();
        OrderLine ol2 = OrderLine.builder()
                .quantity(2)
                .goods(Goods.builder().goodsId(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76841")).build())
                .build();
        cart.add(ol1).add(ol2);

        Account account = Account.builder().email("demo1@example.com").build();
        String signature = orderService.calcSignature(cart);

        Order ordered = orderService.purchase(account, cart, signature);

        assertThat(cart.isEmpty(), is(true));
        assertThat(ordered.getOrderId(), is(notNullValue()));
        assertThat(ordered.getOrderDate(), is(LocalDate.now()));
        assertThat(ordered.getEmail(), is("demo1@example.com"));
        assertThat(ordered.getOrderLines().getList(), hasSize(2));
    }

    @Test
    public void testPurchase_CartIsEmpty() throws Exception {
        expectedException.expect(EmptyCartOrderException.class);
        expectedException.expect(hasProperty("message", is("買い物カゴが空です")));

        Cart cart = new Cart();

        Account account = Account.builder().email("demo1@example.com").build();
        String signature = orderService.calcSignature(cart);

        // change cart
        cart.remove(Collections.singleton(0));

        orderService.purchase(account, cart, signature);
    }

    @Test
    public void testPurchase_CartHasBeenChanged() throws Exception {
        expectedException.expect(InvalidCartOrderException.class);
        expectedException.expect(hasProperty("message", is("買い物カゴの状態が変わっています")));

        Cart cart = new Cart();
        OrderLine ol1 = OrderLine.builder()
                .quantity(1)
                .goods(Goods.builder().goodsId(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76840")).build())
                .build();
        OrderLine ol2 = OrderLine.builder()
                .quantity(2)
                .goods(Goods.builder().goodsId(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76841")).build())
                .build();
        cart.add(ol1).add(ol2);

        Account account = Account.builder().email("demo1@example.com").build();
        String signature = orderService.calcSignature(cart);

        // change cart
        cart.remove(Collections.singleton(0));

        orderService.purchase(account, cart, signature);
    }
}