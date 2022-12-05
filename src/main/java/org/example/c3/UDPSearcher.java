package org.example.c3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * UPD 单播
 *
 * @author ryan
 * @version id: UDPSearcher, v 0.1 2022/12/5 10:31 yang.zhang Exp $
 */
public class UDPSearcher {

    public static void main(String[] args) throws IOException {
        System.out.println("UDPSearcher Started.");

        // 作为搜索方，让系统自动分配端口
        DatagramSocket ds = new DatagramSocket();

        // 构建一份请求数据
        String requestData = "Hello world";
        byte[] requestDataBytes = requestData.getBytes();
        // 直接根据发送者构建一份回送信息
        DatagramPacket responsePack = new DatagramPacket(requestDataBytes,requestDataBytes.length);
        responsePack.setAddress(InetAddress.getLocalHost());
        responsePack.setPort(20000);
        // 发送
        ds.send(responsePack);

        // 构建接收实体
        final byte[] buf = new byte[512];
        DatagramPacket receivePack = new DatagramPacket(buf, buf.length);
        // 接收
        ds.receive(receivePack);

        // 打印接收到的信息与发送者的信息
        String ip = receivePack.getAddress().getHostAddress();
        int port = receivePack.getPort();
        int dataLen = receivePack.getLength();
        String data = new String(receivePack.getData(), 0, dataLen);
        System.out.println("UDPSearcher receive from ip:" + ip + "\tport:" + port + "\tdata:" + data);

        // 完成
        System.out.println("UDPSearcher Finished.");
        ds.close();
    }
}
