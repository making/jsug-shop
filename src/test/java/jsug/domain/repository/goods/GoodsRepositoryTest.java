package jsug.domain.repository.goods;

import jsug.domain.model.Goods;
import jsug.domain.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;
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
public class GoodsRepositoryTest {
    @Autowired
    GoodsRepository goodsRepository;

    @Test
    public void testFindOne() throws Exception {
        Optional<Goods> opt = goodsRepository.findOne(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76840"));
        assertThat(opt.isPresent(), is(true));
        Goods goods = opt.get();
        assertThat(goods.getGoodsId(), is(UUID.fromString("366cf3a4-68c5-4dae-a557-673769f76840")));
        assertThat(goods.getGoodsName(), is("こころ"));
        assertThat(goods.getDescription(), is("夏目 漱石の本です"));
        assertThat(goods.getPrice(), is(900));
        assertThat(goods.getCategory().getCategoryName(), is("本"));
    }

    @Test
    public void testFindOne_notFound() throws Exception {
        Optional<Goods> goods = goodsRepository.findOne(UUID.randomUUID());
        assertThat(goods.isPresent(), is(false));
    }

    @Test
    public void testFindByCategoryId_Page0() throws Exception {
        Page<Goods> goods = goodsRepository.findByCategoryId(1, new PageRequest(0, 3));
        assertThat(goods.getTotalElements(), is(5L));
        assertThat(goods.getTotalPages(), is(2));
        assertThat(goods.getNumberOfElements(), is(3));
        assertThat(goods.getContent().get(0).getGoodsName(), is("こころ"));
        assertThat(goods.getContent().get(1).getGoodsName(), is("〔雨ニモマケズ〕"));
        assertThat(goods.getContent().get(2).getGoodsName(), is("走れメロス"));
    }

    @Test
    public void testFindByCategoryId_Page1() throws Exception {
        Page<Goods> goods = goodsRepository.findByCategoryId(1, new PageRequest(1, 3));
        assertThat(goods.getTotalElements(), is(5L));
        assertThat(goods.getTotalPages(), is(2));
        assertThat(goods.getNumberOfElements(), is(2));
        assertThat(goods.getContent().get(0).getGoodsName(), is("吾輩は猫である"));
        assertThat(goods.getContent().get(1).getGoodsName(), is("人間失格"));
    }
}