package jsug.domain.service.goods;

import jsug.domain.TestConfig;
import jsug.domain.model.Goods;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Sql(scripts = {
        "/sql/drop-tables.sql",
        "/db/migration/V1__create-schema.sql",
        "/sql/insert-goods.sql"
}, config = @SqlConfig(encoding = "UTF-8"))
public class GoodsServiceTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Autowired
    GoodsService goodsService;

    @Test
    public void testFindOne() throws Exception {
        Goods goods = goodsService.findOne(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76840"));
        assertThat(goods.getGoodsId(), is(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76840")));
        assertThat(goods.getGoodsName(), is("こころ"));
        assertThat(goods.getDescription(), is("夏目 漱石の本です"));
        assertThat(goods.getPrice(), is(900));
        assertThat(goods.getCategory().getCategoryName(), is("本"));
    }

    @Test
    public void testFindOne_NotFound() throws Exception {
        expectedException.expect(GoodsNotFoundException.class);
        goodsService.findOne(UUID.randomUUID());
    }
}