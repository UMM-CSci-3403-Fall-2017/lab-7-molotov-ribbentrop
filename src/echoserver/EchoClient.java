package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.*;

public class EchoClient implements Runnable {
        public static final int PORT_NUMBER = 6013;
        private Socket socket;
        private InputStream socketInputStream;
        private OutputStream socketOutputStream;

        public EchoClient() throws IOException{
                socket = new Socket("localhost", PORT_NUMBER);
                socketInputStream = socket.getInputStream();
                socketOutputStream = socket.getOutputStream();
        }

        public static void main(String[] args) throws IOException {
                EchoClient client = new EchoClient();
                Thread serverListener = new Thread(client, "Client Listener");
                try{
                        client.start();
                        serverListener.start();
                } catch(SocketException ex){
                        System.out.println("The connection from the server was lost.");
                }
        }

        private void start() throws IOException {
                int readByte;
                while ((readByte = System.in.read()) != -1) {
                        socketOutputStream.write(readByte);
                }
                socketOutputStream.flush();
                socket.shutdownOutput();
        }

        public void run(){
                try{
                        int readByte = -1;
                        while((readByte = socketInputStream.read()) != -1){
                                System.out.write(readByte);
                        }
                  System.out.flush();
                } catch(Exception ex){}
        }
}


