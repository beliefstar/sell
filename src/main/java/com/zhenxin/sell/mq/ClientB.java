package com.zhenxin.sell.mq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ClientB {
    static String QUEUENAME = "hello";

    //接收消息
    public static void main(String[] args) throws IOException, TimeoutException {

        //建立连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("zhenxin");
        factory.setPassword("1234");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUENAME, true, false, false, null);

        //创建consumer
        channel.basicConsume(QUEUENAME, new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println(new String(body, "UTF-8"));
            }
        });

//        //接收消息
//        while (true) {
//            byte[] bys = new byte[1024];
//            consumer.handleDelivery(null, null, null, bys);
//            System.out.println(new String(bys, 0, bys.length, "UTF-8"));
//        }
    }
}
