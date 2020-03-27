package may.i.jhq.producer;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.BoundZSetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.Instant;

/**
 *  Message Producer
 *
 *  @author     jin_huaquan
 *  @date      2020/3/27 11:48
 *  @version   1.0
 */
@Component
@AllArgsConstructor
public class MessageProducer {

	public static final String MESSAGE_QUEUE_KEY = "message:queue";

	private final StringRedisTemplate redisTemplate;

	@Scheduled(fixedDelay = 1000)
	public void produce() {
		BoundZSetOperations<String, String> zSetOperations = redisTemplate.boundZSetOps(MESSAGE_QUEUE_KEY);
		zSetOperations.add("Message: " + Instant.now().getEpochSecond(), Instant.now().getEpochSecond());
	}
}
