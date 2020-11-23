package xyz.chengzi.aeroplanechess;//视情况可以删掉这一句

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
    public static void main(String args[]) throws Exception {
        byte[] b=new byte[1024];
        int myport=10030;//本机的端口，可随意设置
        DatagramSocket ds=new DatagramSocket(myport);
        DatagramPacket dp=new DatagramPacket(b,1024,InetAddress.getByName( "这里填写发送方的IP地址"),myport);

        while (true) {
            System.out.println("等待接收数据");
            ds.receive(dp);
            String str = new String(dp.getData(), 0, dp.getLength()) + "  from"+ dp.getAddress().getHostAddress() + "  port:" + dp.getPort();
            System.out.println(str);
        }
    }
}
