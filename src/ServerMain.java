public class ServerMain {
    public static void main(String args[]) throws Exception
    {
        server s = new server();

        new Thread(() -> {
            s.start();
            ;
        }).start();
    }
}
