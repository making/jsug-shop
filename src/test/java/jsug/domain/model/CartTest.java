package jsug.domain.model;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class CartTest {

    @Test
    public void testRemove() throws Exception {
        Cart cart = new Cart();
        OrderLine ol1 = OrderLine.builder().quantity(1).goods(Goods.builder().build()).build();
        OrderLine ol2 = OrderLine.builder().quantity(2).goods(Goods.builder().build()).build();
        OrderLine ol3 = OrderLine.builder().quantity(3).goods(Goods.builder().build()).build();
        OrderLine ol4 = OrderLine.builder().quantity(4).goods(Goods.builder().build()).build();
        cart.add(ol1).add(ol2).add(ol3).add(ol4);

        Set<Integer> lineNo = new HashSet<>();
        lineNo.add(1);
        lineNo.add(2);

        cart.remove(lineNo);

        assertThat(cart, is(new Cart().add(ol1).add(ol4)));
    }
}