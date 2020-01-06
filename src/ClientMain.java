import com.sun.security.ntlm.Client;

import java.io.IOException;

public class ClientMain {
    public static void main(String[] args) {


        char [] teamName = new char[32];
        for(int i=0; i<teamName.length; i++){
            teamName[i]='9';
        }
        client client1= new client(new String(teamName));
        try {
            client1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
