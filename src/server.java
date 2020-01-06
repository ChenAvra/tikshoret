import java.io.IOException;
import java.math.BigInteger;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class server {
    ExecutorService pool = Executors.newFixedThreadPool(2);


    public void start() {

        pool.execute(new Thread(() -> {
            try {
                serverWorking();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
//
    }


    public synchronized void serverWorking() throws IOException {
        DatagramSocket serverSocket = new DatagramSocket(3117);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        serverSocket.setSoTimeout(1000000000);
        boolean isTimeOut=false;
        while(true)
        {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            serverSocket.receive(receivePacket);
            System.out.println("Server:recived a message");
            String sentence = new String( receivePacket.getData());


            System.out.println("server:RECEIVED: " + sentence);

            message m = new message(sentence);

            String answer="";

            String start = new String(m.getOriginalStringStart());
            String end = new String (m.getOrginalStringEnd());
            String hash = new String (m.getHash());

            if(m.getType()=='1'){
                message messageToReturn = new message(m.getTeamName(), '2',m.getHash(),m.getOriginalLengh(),m.getOriginalStringStart(),m.getOrginalStringEnd());
                InetAddress IPAddress = receivePacket.getAddress();
                int port = receivePacket.getPort();
                String offerAnswer = messageToReturn.getFullString();
                System.out.println("send you an answer:"+offerAnswer);
                sendData = offerAnswer.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                serverSocket.send(sendPacket);
            }

           else if(m.getType()=='3'){
                System.out.println("server: this is a request, i will try to find you an answer");
                answer=this.tryDeHash(start, end, hash);
                if(answer!=null){
                    message messageToReturn = new message(m.getTeamName(), '4',m.getHash(),m.getOriginalLengh(),answer.toCharArray(),m.getOrginalStringEnd());
                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();
                    String goodAnswer = messageToReturn.getFullString();
                    sendData = goodAnswer.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    serverSocket.send(sendPacket);
                }
                else{
                    message messageToReturn = new message(m.getTeamName(), '5',m.getHash(),m.getOriginalLengh(),m.getOriginalStringStart(),m.getOrginalStringEnd());
                    InetAddress IPAddress = receivePacket.getAddress();
                    int port = receivePacket.getPort();
                    String badAnswer = messageToReturn.getFullString();
                    sendData = badAnswer.getBytes();
                    DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
                    serverSocket.send(sendPacket);
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
        int start = convertStringToInt(startRange);
        int end = convertStringToInt(endRange);
        int length = startRange.length();
        for(int i = start; i <= end; i++){
            String currentString = converxtIntToString(i, length);
            String hash = hash(currentString);
            if(originalHash.equals(hash)){
                return currentString;
            }
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
