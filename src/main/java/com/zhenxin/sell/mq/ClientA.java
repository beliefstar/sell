package com.zhenxin.sell.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ClientA {

    static String QUEUENAME = "hello";

    //发送消息
    public static void main(String[] args) throws IOException, TimeoutException {

        //建立连接
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("zhenxin");
        factory.setPassword("1234");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //声明Queue
        channel.queueDeclare(QUEUENAME, true, false, false, null);

        //发送消息
        String msg = "你好";
        channel.basicPublish("", QUEUENAME, null, msg.getBytes("UTF-8"));

        channel.close();
    }
}
