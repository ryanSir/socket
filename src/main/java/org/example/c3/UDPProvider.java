package org.example.c3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * UPD 单播
 *
 * @author ryan
 * @version id: UDPProvider, v 0.1 2022/12/5 10:31 yang.zhang Exp $
 */
public class UDPProvider {

    public static void main(String[] args) throws IOException {
        System.out.println("UDPProvider Started.");

        // 作为接收者，指定端口，用于数据接收
        DatagramSocket ds = new DatagramSocket(20000);

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
        System.out.println("UDPProvider receive from ip:" + ip + "\tport:" + port + "\tdata:" + data);

        // 构建一份回送数据
        String responseData = "Receive data with len:" + dataLen;
        byte[] responseDataBytes = responseData.getBytes();
        // 直接根据发送者构建一份回送信息
        DatagramPacket responsePack = new DatagramPacket(responseDataBytes, responseDataBytes.length, receivePack.getAddress(), receivePack.getPort());
        ds.send(responsePack);

        // 完成
        System.out.println("UDPProvider Finished.");
        ds.close();
    }
}
