package tech.lovelycheng.learning.rocketmq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

/**
 * @author chengtong
 * @date 2020/1/15 16:31
 */
public class Producer {

    public static void main(String[] args) throws InterruptedException, RemotingException, MQClientException, MQBrokerException {
        DefaultMQProducer producer = new  DefaultMQProducer("group");

        producer.setNamesrvAddr("localhost:9876");

        producer.start();

        for(int i=0;i<100;i++){
            Message message = new Message("topic","tags",(" hello java demo rocketMQ " + i).getBytes());

            SendResult sendResult = producer.send(message);

            System.err.printf("%s%n",sendResult);

        }

        producer.shutdown();

    }

}
