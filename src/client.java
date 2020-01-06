import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.concurrent.TimeoutException;

public class client {

    public String hash;
    public int length;
    public String teamName;
    public HashMap<InetAddress, String >listServer;
    public DatagramSocket data;

    public client(String teamName) {
        this.teamName=teamName;
        this.listServer=new HashMap<>();

    }



    public  void discoverAndOffer (String sentence, int length) throws IOException {

        //discover message to the server
        message m = new message(teamName.toCharArray(), '1', hash.toCharArray(), (char) length, null, null);
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
        byte[] sendData = new byte[1024];
        sendData = m.getFullString().getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 3117);
        clientSocket.send(sendPacket);


        //recieve offer to client

        clientSocket.setSoTimeout(1000);
        boolean isTimeOut = false;
        while (!isTimeOut) {
            try {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                clientSocket.receive(receivePacket);
            } catch (SocketTimeoutException te) {
                isTimeOut = true;


            }
            clientSocket.close();
        }
    }

    public void start() throws IOException {
        System.out.println("welcome to "+teamName+". Please enter the hash");
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
        String sentence = inFromUser.readLine();
        this.hash=sentence;
        System.out.println("Please enter the input string length");
        BufferedReader inFromUser2= new BufferedReader(new InputStreamReader(System.in));
        int   length  = Integer.parseInt(inFromUser2.readLine());
        this.length=length;
        discoverAndOffer(sentence,length);

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

    private String [] divideToDomains (int stringLength, int numOfServers){
        String [] domains = new String[numOfServers * 2];

        StringBuilder first = new StringBuilder(); //aaa
        StringBuilder last = new StringBuilder(); //zzz

        for(int i = 0; i < stringLength; i++){
            first.append("a"); //aaa
            last.append("z"); //zzz
        }

        int total = convertStringToInt(last.toString());
        int perServer = (int) Math.floor (((double)total) /  ((double)numOfServers));

        domains[0] = first.toString(); //aaa
        domains[domains.length -1 ] = last.toString(); //zzz
        int summer = 0;

        for(int i = 1; i <= domains.length -2; i += 2){
            summer += perServer;
            domains[i] = converxtIntToString(summer, stringLength); //end domain of server
            summer++;
            domains[i + 1] = converxtIntToString(summer, stringLength); //start domain of next server
        }

        return domains;
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

}









