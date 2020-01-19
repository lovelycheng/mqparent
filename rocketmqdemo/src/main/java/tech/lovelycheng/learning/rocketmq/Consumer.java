package tech.lovelycheng.learning.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.List;

/**
 * @author chengtong
 * @date 2020/1/16 14:06
 */
public class Consumer {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer pushConsumer = new DefaultMQPushConsumer("consumer_group");

        pushConsumer.setNamesrvAddr("localhost:9876");

        pushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        pushConsumer.subscribe("topic","*");

        pushConsumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            if (msgs != null) {
                for (MessageExt ext : msgs) {
                    try {
                        System.err.println(new Date() + new String(ext.getBody(), "UTF-8"));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();

                    }

                }
            }
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        pushConsumer.start();

        System.err.println("消费者已经启动");

    }

}
