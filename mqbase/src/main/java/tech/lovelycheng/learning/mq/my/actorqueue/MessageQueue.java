package tech.lovelycheng.learning.mq.my.actorqueue;

import lombok.Data;
import sun.misc.Unsafe;
import tech.lovelycheng.learning.mq.my.methodrequest.MessageRequest;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author chengtong
 * @date 2020/1/15 14:27
 * 一个无锁的消息队列，put时cas往tail 赋值，get 时从head取。 cas操作，没有取消。
 * 默认就是公平的方式，没有非公平的实现。没有有限级的实现。
 *
 */
@Data
public class MessageQueue {

    private volatile Node head;

    private volatile Node tail;

    // no use ,for thread schedule
    ReentrantLock reentrantLock = new ReentrantLock();

    Condition notEmpty = reentrantLock.newCondition();

    //Condition notFull = reentrantLock.newCondition();


    private static long HEAD_OFFSET;
    private static long TAIL_OFFSET;

    private static Unsafe unsafe = Unsafe.getUnsafe();

    static {
        try {
            HEAD_OFFSET = unsafe.objectFieldOffset(MessageQueue.class.getDeclaredField("head"));
            TAIL_OFFSET = unsafe.objectFieldOffset(MessageQueue.class.getDeclaredField("tail"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Data
    public static class Node {
        MessageRequest messageRequest;
        int transactionId; //事务
        int type; // 1 点对点  0 广播
        //   TODO: 2020/1/15 binding

        Node next;

        public Node(MessageRequest messageRequest, int transactionId, int type) {
            this.messageRequest = messageRequest;
            this.transactionId = transactionId;
            this.type = type;
        }
    }

    public boolean putNode(MessageRequest messageRequest, int type, int transactionId) {
        Node node = new Node(messageRequest, transactionId, type);

        if (head == null) {

            if (unsafe.compareAndSwapObject(this, HEAD_OFFSET, null, node)) {
                tail = node;
                return true;
            }

        }

        Node t = tail;

        if (tail != null) {

            while (!unsafe.compareAndSwapObject(this, TAIL_OFFSET, t, node)) {
                t = tail;
            }
            t.next = node;
        }

        notEmpty.signal();

        return true;
    }

    public Node getNode() throws InterruptedException {

        Node h = head;
        // guard block
        while (h == null) {
            notEmpty.await();
            h = head;
        }

        Node n = h.next;

        while (!unsafe.compareAndSwapObject(this, HEAD_OFFSET, h, n)) {
            h = head;
            n = h.next;
        }

        if (head == null) {
            tail = null;
        }
        h.next = null;

        return h;
    }


}
