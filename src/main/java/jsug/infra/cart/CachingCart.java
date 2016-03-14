package jsug.infra.cart;

import jsug.domain.model.Cart;
import jsug.domain.model.OrderLine;
import jsug.domain.model.OrderLines;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Slf4j
public class CachingCart extends Cart {
    public CachingCart() {
        super();
        loadCache();
    }

    @Override
    public OrderLines getOrderLines() {
        loadCache();
        return super.getOrderLines();
    }

    @Override
    public Cart add(OrderLine orderLine) {
        withSyncCache(() -> super.add(orderLine), true);
        return this;
    }

    @Override
    public Cart remove(Set<Integer> lineNo) {
        withSyncCache(() -> super.remove(lineNo), true);
        return this;
    }

    @Override
    public Cart clear() {
        withSyncCache(super::clear, true);
        return this;
    }

    Cache getCache() {
        // 本当はCacheManagerをDIしたかったが、
        // CacheManagerはSerializableじゃないで、SessionスコープであるCartのフィールドに入れられない
        // そのため、リクエストコンテキストから毎度ApplicationContextを取得して、
        // CacheManagerを取得している。
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        ApplicationContext context = RequestContextUtils.findWebApplicationContext(attributes.getRequest());
        CacheManager cacheManager = context.getBean(CacheManager.class);
        Cache cache = cacheManager.getCache("orderLines");
        return cache;
    }

    /**
     * OrderLinesをキャッシュと同期しながら処理を実行する
     *
     * @param action 主処理
     * @param save   処理後、キャッシュに保存するかどうか
     */
    void withSyncCache(Runnable action, boolean save) {
        Cache cache = getCache();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        OrderLines orderLines = cache.get(username, OrderLines.class);
        if (orderLines != null) {
            // キャッシュから読み込み
            log.debug("load {} -> {}", username, orderLines);
            List<OrderLine> lines = new ArrayList<>(orderLines.getList()); // copy
            super.getOrderLines().getList().clear();
            super.getOrderLines().getList().addAll(lines);
        }
        // 処理
        action.run();
        if (save) {
            // キャッシュに保存
            if (log.isDebugEnabled()) {
                log.debug("save {} -> {}", username, super.getOrderLines());
            }
            cache.put(username, super.getOrderLines());
        }
    }

    void loadCache() {
        withSyncCache(() -> {
        }, false);
    }
}
