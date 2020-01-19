package tech.lovelycheng.learning.mq.my.servant;

import tech.lovelycheng.learning.mq.my.methodrequest.MessageRequest;

import javax.jms.JMSConsumer;

/**
 * @author chengtong
 * @date 2020/1/15 15:03
 */
public class Servant {

    int currentTranXid;

    public void process(MessageRequest messageRequest){

        System.err.println(messageRequest.getContent());

    }




}
