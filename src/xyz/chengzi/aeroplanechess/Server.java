package xyz.chengzi.aeroplanechess;//视情况可以删掉这一句

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
    public static void main(String args[]) throws Exception{
        int myport=10030;//本机端口，需要与Client类的端口保持一致
        int yourport=10020;//接收方客户端的端口
        DatagramSocket ds=new DatagramSocket(myport);
        String str="HelloWorld!";//这里应该发送实时的对局信息
        DatagramPacket dp=new DatagramPacket(str.getBytes(),str.length(), InetAddress.getByName("这里是接收方的ip地址"),yourport);//目标客户端端口
        System.out.println("信息已发送");
        ds.send(dp);
        ds.close();

    }

}
