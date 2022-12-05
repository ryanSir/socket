package org.example.c3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.UUID;

/**
 * UPD 多播
 *
 * @author ryan
 * @version id: UDPProvider, v 0.1 2022/12/5 10:31 yang.zhang Exp $
 */
public class UDPProvider2 {

    public static void main(String[] args) throws IOException {
        // 生成一份唯一标识
        String sn = UUID.randomUUID().toString();
        Provider provider = new Provider(sn);
        provider.start();

        System.in.read();
        provider.exit();

    }

    public static class Provider extends Thread {
        private final String   sn;
        private boolean        done = false;
        private DatagramSocket ds   = null;

        public Provider(String sn) {
            super();
            this.sn = sn;
        }

        public void run() {
            super.run();
            System.out.println("UDPProvider Started.");
            try {
                // 监听20000端口
                ds = new DatagramSocket(20000);
                while (!done) {
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

                    // 解析端口号
                    int responsePort = MessageCreator.parsePort(data);
                    if (responsePort != -1) {
                        // 构建一份回送数据
                        String responseData = MessageCreator.buildWithSn(sn);
                        byte[] responseDataBytes = responseData.getBytes();
                        // 直接根据发送者构建一份回送信息
                        DatagramPacket responsePack = new DatagramPacket(responseDataBytes, responseDataBytes.length, receivePack.getAddress(), responsePort);
                        ds.send(responsePack);
                    }
                }

            } catch (Exception e) {

            } finally {
                close();
                System.out.println("子线程已结束");
            }

            // 完成
            System.out.println("UDPProvider Finished.");
        }

        private void close() {
            if (ds != null) {
                ds.close();
                ds = null;
            }
        }

        void exit() {
            done = true;
            close();
        }
    }
}
