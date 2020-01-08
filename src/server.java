import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {
    ExecutorService pool = Executors.newFixedThreadPool(2);
    private DatagramSocket serverSocket;

    public void start() {

        pool.execute(new Thread(() -> {
                serverWorking();

        }));
//
    }


    public synchronized void serverWorking()  {

        try {
            serverSocket = new DatagramSocket(3117);
            serverSocket.setBroadcast(true);
            serverSocket.setSoTimeout(10000*1000);



        boolean isTimeOut=false;
        while(true)
        {
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);
            System.out.println("Server:recived a message");
            cllasifierOfMessages(receivePacket);




        }
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cllasifierOfMessages(DatagramPacket receivePacket)  {
        byte[] sendData = receivePacket.getData();
    //    String sentence = new String( receivePacket.getData());


       //  System.out.println("server:RECEIVED: " + sentence);

        message1 m = new message1(sendData);

        String answer="";

        String start = new String(m.getOriginalStringStart());
        String end = new String (m.getOrginalStringEnd());
        String hash = new String (m.getHash());

        if((char)m.getType()[0]=='1'){
            char a = '2';
            byte [] type = new byte[1];
            type[0]=(byte)a;
            message1 messageToReturn = new message1(m.getTeamName(), type,m.getHash(),m.getOriginalLengh(),m.getOriginalStringStart(),m.getOrginalStringEnd());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            System.out.println("server: i have a discover message from client");
           // String offerAnswer = messageToReturn.getFullString();
           //   System.out.println("send you an answer:"+offerAnswer);
            sendData = messageToReturn.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            try {
                serverSocket.send(sendPacket);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if((char)m.getType()[0]=='3'){
              System.out.println("server: this is a request, i will try to find you an answer");
            System.out.println("i am activating hash function. start:"+start+",end:"+end+",hash:"+hash);
           // answer=this.tryDeHash("tashaf", end, hash);
            answer=this.tryDeHash(start, end, hash);
            System.out.println("your answer is:"+answer);
            if(answer!=null){
                char a = '4';
                byte [] type = new byte[1];
                type[0]=(byte)a;
                message1 messageToReturn = new message1(m.getTeamName(), type,m.getHash(),m.getOriginalLengh(),answer.getBytes(),m.getOrginalStringEnd());
                //  System.out.println("the original lentgh is:"+m.getOriginalLengh());
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
//                String goodAnswer = messageToReturn.getFullString();
                sendData = messageToReturn.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                try {
                    serverSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                char a = '5';
                byte [] type = new byte[1];
                type[0]=(byte)a;
                message1 messageToReturn = new message1(m.getTeamName(), type,m.getHash(),m.getOriginalLengh(),m.getOriginalStringStart(),m.getOrginalStringEnd());
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
//                String badAnswer = messageToReturn.getFullString();
                sendData = messageToReturn.toByteArray();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                try {
                    serverSocket.send(sendPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

//            InetAddress IPAddress = receivePacket.getAddress();
//            int port = receivePacket.getPort();
//            String capitalizedSentence = sentence.toUpperCase();
//            sendData = capitalizedSentence.getBytes();
//            DatagramPacket sendPacket =
//                    new DatagramPacket(sendData, sendData.length, IPAddress, port);
//            serverSocket.send(sendPacket);
        }
    }

    private String hash(String toHash) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(toHash.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32){
                hashText.insert(0, "0");
            }
            return hashText.toString();
        }
        catch (NoSuchAlgorithmException e){
            throw new RuntimeException(e);
        }
    }



    private String tryDeHash(String startRange, String endRange, String originalHash){
        try {
            int start = convertStringToInt(startRange);
            int end = convertStringToInt(endRange);
            int length = startRange.length();
            String hash;
            String currentString;
            byte[] original = originalHash.getBytes();
            byte[] hashArr;
            for (int i = start; i <= end; i++) {
                currentString = converxtIntToString(i, length);
                hash = hash(currentString);
                hashArr = hash.getBytes();
                if (Arrays.equals(original, hashArr)) {
                    return currentString;
                }
            }
        } catch (RuntimeException e) {
            System.out.println("problem in DeHash function!!!!!");
        }
        return null;
    }

    private int convertStringToInt(String toConvert) {
        char[] charArray = toConvert.toCharArray();
        int num = 0;
        for(char c : charArray){
            if(c < 'a' || c > 'z'){
                throw new RuntimeException();
            }
            num *= 26;
            num += c - 'a';
        }
        return num;
    }


    private String converxtIntToString(int toConvert, int length) {
        StringBuilder s = new StringBuilder(length);
        while (toConvert > 0 ){
            int c = toConvert % 26;
            s.insert(0, (char) (c + 'a'));
            toConvert /= 26;
            length --;
        }
        while (length > 0){
            s.insert(0, 'a');
            length--;
        }
        return s.toString();
    }
}
