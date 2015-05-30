package jsug.domain.service.order;

import jsug.domain.model.Account;
import jsug.domain.model.Cart;
import jsug.domain.model.Order;
import jsug.domain.model.OrderLines;
import jsug.domain.repository.order.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.SerializationUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Objects;

@Service
@Transactional
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    public String calcSignature(Cart cart) {
        byte[] serialized = SerializationUtils.serialize(cart.getOrderLines());
        byte[] signature = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            signature = messageDigest.digest(serialized);
        } catch (NoSuchAlgorithmException ignored) {
        }
        return Base64.getEncoder().encodeToString(signature);
    }

    public Order purchase(Account account, Cart cart, String signature) {
        if (cart.isEmpty()) {
            throw new EmptyCartOrderException("買い物カゴが空です");
        }
        if (!Objects.equals(calcSignature(cart), signature)) {
            throw new InvalidCartOrderException("買い物カゴの状態が変わっています");
        }

        // 買い物カゴから削除するため、ディープコピーしておく
        OrderLines orderLines = (OrderLines) SerializationUtils.deserialize(
                SerializationUtils.serialize(cart.getOrderLines()));

        Order order = Order.builder()
                .email(account.getEmail())
                .orderDate(LocalDate.now())
                .orderLines(orderLines)
                .build();
        Order ordered = orderRepository.create(order);
        cart.clear();
        return ordered;
    }
}
