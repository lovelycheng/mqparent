package tech.lovelycheng.learning.mq.my.actorscheduler;

import tech.lovelycheng.learning.mq.my.actorqueue.MessageQueue;
import tech.lovelycheng.learning.mq.my.methodrequest.MessageRequest;

/**
 * @author chengtong
 * @date 2020/1/15 14:51
 * 一个消息推送的调度器。理论上是单跟线程的for循环。
 */
public class MessageScheduler {

    MessageQueue messageQueue;

    public void start(){

        messageQueue = new MessageQueue();

        MessageRequest messageRequest = null;

        do{
            try {
                messageRequest = messageQueue.getNode().getMessageRequest();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(messageRequest != null){

                // TODO: 2020/1/15 send to consumer

            }

        }while (true);




    }



}
