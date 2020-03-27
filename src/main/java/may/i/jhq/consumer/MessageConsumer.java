package may.i.jhq.consumer;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Set;

import static may.i.jhq.producer.MessageProducer.MESSAGE_QUEUE_KEY;

/**
 *  Message Consumer
 *
 *  @author     jin_huaquan
 *  @date      2020/3/27 11:53
 *  @version   1.0
 */
@Component
@AllArgsConstructor
public class MessageConsumer {

	private static final Logger LOGGER = LoggerFactory.getLogger(MessageConsumer.class);

	private final StringRedisTemplate redisTemplate;

	@Scheduled(fixedDelay = 1000)
	public void consume() {
		Set<String> messageSet = redisTemplate.boundZSetOps(MESSAGE_QUEUE_KEY).range(0, 0);
		if (messageSet.isEmpty()) {
			return;
		}

		String message = messageSet.iterator().next();
		LOGGER.info("Consume Message: {}", message);
		redisTemplate.boundZSetOps(MESSAGE_QUEUE_KEY).removeRange(0, 0);
	}
}
