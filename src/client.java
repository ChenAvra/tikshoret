import sun.security.x509.IPAddressName;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeoutException;

public class client {

    public String hash;
    public char length;
    public String teamName;
    public HashSet<InetAddress> listServer;
    public DatagramSocket data;

    public client(String teamName) {
        this.teamName=teamName;
        this.listServer=new HashSet<>();

    }



    public  void discoverAndOffer (String sentence, String length) {
        try {
            //discover message to the server
            char a = '1';
            byte[] typeArray = new byte[1];
            typeArray[0] = (byte) a;
            message1 m = new message1(teamName.getBytes(), typeArray, hash.getBytes(), length.getBytes(), null, null);
            DatagramSocket clientSocket = null;

            clientSocket = new DatagramSocket();

            InetAddress IPAddress = InetAddress.getByName("255.255.255.255");
            byte[] sendData = new byte[1024];
            sendData = m.toByteArray();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 3117);
            clientSocket.send(sendPacket);
            //   System.out.println("client: i sent you discover");


            //recieve offer to client

            clientSocket.setSoTimeout(2000);

            boolean isTimeOut = false;
            while (true) {
                try {
                    byte[] receiveData = new byte[1024];
                    DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                    clientSocket.receive(receivePacket);
                    //  System.out.println("client: i recivrd your offer");
                    message mFromServer = new message(new String(receivePacket.getData()));
                    //  System.out.println("client: your offer message is: type-"+mFromServer.getType());
                    if (mFromServer.getType() == '2') {
                        listServer.add(receivePacket.getAddress());
                        //   System.out.println("client: i added you to list");
                    }

                } catch (SocketTimeoutException te) {
                    isTimeOut = true;
                    System.out.println("1 sec passed for discovered message");
                    break;
                }

            }
            //sendRequest(clientSocket);
            String[] ranges = divideToDomains(Integer.parseInt(length), listServer.size());
            //  System.out.println("client: i'm in send request function");
            int i = 0;
            for (InetAddress ip1 : listServer) {
                if (i < ranges.length - 1) {
                    char b = '3';
                    byte[] type = new byte[1];
                    type[0] = (byte) b;
                    message1 m1 = new message1(teamName.getBytes(), type, hash.getBytes(), length.getBytes(), ranges[i].getBytes(), ranges[i + 1].getBytes());
                    i++;
                    byte[] sendData1 = new byte[1024];
                    sendData1 = m1.toByteArray();
                    DatagramPacket sendPacket1 = new DatagramPacket(sendData1, sendData1.length, ip1, 3117);
                    clientSocket.send(sendPacket1);
                    //   System.out.println("client:sending request...");


                    clientSocket.setSoTimeout(15000);
                    boolean isTimeOut1 = false;
                    boolean found = false;
                    String returnToClient = "";
                    while (!isTimeOut1 && !found) {
                        try {
                            byte[] receiveData = new byte[1024];
                            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                            clientSocket.receive(receivePacket);
                            message mFromServer = new message(new String(receivePacket.getData()));
                            if (mFromServer.getType() == '4') {
                                String answer = new String(mFromServer.getOriginalStringStart());
                                //   System.out.println(answer);
                                returnToClient = answer;
                                found = true;
                            }
                        } catch (SocketTimeoutException te) {
                            isTimeOut = true;
                            System.out.println("15 sec passed");


                        }


                    }

                    clientSocket.close();

                    if (!found) {
                        System.out.println("there is no string for hash");
                    } else {
                        System.out.println("i found your input:" + returnToClient);
                    }
                }
            }

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public  void  sendRequest(DatagramSocket clientSocket) throws IOException {
//        String[] ranges = divideToDomains(Character.getNumericValue(length), listServer.size());
//        System.out.println("client: i'm in send request function");
//        int i = 0;
//        String answer="";
//        for (InetAddress ip1 : listServer) {
//            if (i < ranges.length - 1) {
//                message m = new message(teamName.toCharArray(), '3', hash.toCharArray(),  length, ranges[i].toCharArray(), ranges[i + 1].toCharArray());
//                i++;
//                byte[] sendData = new byte[1024];
//                sendData = m.getFullString().getBytes();
//                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ip1, 3117);
//                clientSocket.send(sendPacket);
//                System.out.println("client:sending request...");
//
//
//                clientSocket.setSoTimeout(1000*15000);
//                boolean isTimeOut = false;
//                boolean found=false;
//                while (!isTimeOut && !found) {
//                    try {
//                        byte[] receiveData = new byte[1024];
//                        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
//                        clientSocket.receive(receivePacket);
//                        message mFromServer = new message(new String(receivePacket.getData()));
//                        String tempAns =new String(mFromServer.getOriginalStringStart());
//                        System.out.println("your answer is:"+tempAns);
//                        if (mFromServer.getType() == '4') {
//                                answer=new String(mFromServer.getOriginalStringStart());
//                             //   System.out.println(answer);
//                                found=true;
//                        }
//                    } catch (SocketTimeoutException te) {
//                        isTimeOut = true;
//                        System.out.println("15 sec passed");
//
//
//                    }
//
//
//                }
//
//                clientSocket.close();
//                if(!found){
//                    System.out.println("there is no string for hash");
//                }
//                else{
//                    System.out.println("i found your input:"+answer);
//                }
//
//            }
//        }
//    }

        public void start () throws IOException {
            System.out.println("welcome to " + teamName + ". Please enter the hash");
            BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
            String sentence = inFromUser.readLine();
            while(sentence.length()>40){
                System.out.println("wrong hash input!");
                sentence = inFromUser.readLine();
            }
            this.hash = sentence;
            System.out.println("Please enter the input string length");
            BufferedReader inFromUser2 = new BufferedReader(new InputStreamReader(System.in));
            String len =inFromUser2.readLine();
            while(sentence.length()>255){
                System.out.println("wrong length input!");
                len = inFromUser.readLine();
            }
            discoverAndOffer(sentence,len);

        }


        private String converxtIntToString ( int toConvert, int length){
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


    public  String [] divideToDomains (int stringLength, int numOfServers){
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


        private int convertStringToInt (String toConvert){
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










