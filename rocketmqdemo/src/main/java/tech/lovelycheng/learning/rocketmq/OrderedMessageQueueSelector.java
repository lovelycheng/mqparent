package tech.lovelycheng.learning.rocketmq;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

/**
 * @author chengtong
 * @date 2020/1/19 08:48
 */
@Slf4j
public class OrderedMessageQueueSelector implements MessageQueueSelector {
    @Override
    public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {

        Integer id = (int)arg;

        log.info(JSON.toJSONString(mqs.get(0)));

        return mqs.get(0);
    }
}
