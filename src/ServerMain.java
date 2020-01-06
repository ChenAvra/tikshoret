import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ServerMain {
    public static void main(String args[]) throws Exception
    {
        server s = new server();

        new Thread(() -> {
            s.start();
            ;
        }).start();

//        DatagramSocket clientSocket = new DatagramSocket();
//        InetAddress IPAddress = InetAddress.getByName("localhost");
//        byte[] sendData = new byte[1024];
//        byte[] receiveData = new byte[1024];
//
//        char [] teamName = new char[32];
//        for(int i=0; i<teamName.length; i++){
//            teamName[i]='9';
//        }
//        char Type = '1';
//        char[] hash = new char[40];
//        for(int i=0;i<hash.length; i++){
//            hash[i]='7';
//        }
//        char originalLengh = '3';
//        char [] start = new char[3];
//        start[0]='a';
//        start[1]='a';
//        start[2]='a';
//
//        char[] end = new char[3];
//        end[0]='b';
//        end[1]='b';
//        end[2]='b';
//
//
//
//
//        message m = new message(teamName,Type,hash,originalLengh,start,end);
//        String sentence = m.getFullString();
//        sendData = sentence.getBytes();
//        System.out.println("client:sending you a message");
//        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 3117);
//        clientSocket.send(sendPacket);
//        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//        clientSocket.receive(receivePacket);
//        String modifiedSentence = new String(receivePacket.getData());
//        System.out.println("client:FROM SERVER:" + modifiedSentence);
//        clientSocket.close();
//
//////////////////////////////////////////////////////////////////////////////////////////
//        DatagramSocket clientSocket1 = new DatagramSocket();
//        InetAddress IPAddress1 = InetAddress.getByName("localhost");
//        byte[] sendData1 = new byte[1024];
//        byte[] receiveData1 = new byte[1024];
//
//        char [] teamName1 = new char[32];
//        for(int i=0; i<teamName1.length; i++){
//            teamName1[i]='2';
//        }
//        char Type1 = '3';
//        char[] hash1 = ("422ab519eac585ef4ab0769be5c019754f95e8dc").toCharArray();
//
//        char originalLengh1 = '6';
//        char [] start1 = new char[6];
//        start1= ("tashaf").toCharArray();
//
//
//        char[] end1 = new char[6];
//        end1=("zzzzzz").toCharArray();
//
//
//
//
//        message mm = new message(teamName1,Type1,hash1,originalLengh1,start1,end1);
//        String sentence1 = mm.getFullString();
//        sendData1 = sentence1.getBytes();
//        System.out.println("client:sending you a message");
//        DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, IPAddress1, 3117);
//        clientSocket1.send(sendPacket1);
//        DatagramPacket receivePacket1 = new DatagramPacket(receiveData1, receiveData1.length);
//        clientSocket1.receive(receivePacket1);
//        String modifiedSentence1 = new String(receivePacket1.getData());
//        System.out.println("client:FROM SERVER:" + modifiedSentence1);
//        clientSocket1.close();
    }
}
