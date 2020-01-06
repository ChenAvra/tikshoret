import com.sun.security.ntlm.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        client client1= new client("Iris and Chen");
        try {
            client1.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
