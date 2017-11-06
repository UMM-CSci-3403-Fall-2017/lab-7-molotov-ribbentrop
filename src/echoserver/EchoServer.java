package echoserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;

public class EchoServer implements Runnable{

        public static final int PORT_NUMBER = 6013;
        private ServerSocket serverSocket;
        private Socket currentSocket;

        public static void main(String[] args) throws IOException, InterruptedException, Exception {
                EchoServer server = new EchoServer();
                Thread serverInputThread = new Thread(server, "Server Running Thread");

                server.acceptClients();
        }

        private void acceptClients() throws Exception{
                ServerSocket serverSocket = new ServerSocket(PORT_NUMBER);

                while(true){
                        currentSocket = serverSocket.accept();

                        Thread echoToClientThread = new Thread(this, "Client");

                        echoToClientThread.start();
                }
        }

        public void run(){
                try{
                        Socket acceptSocket = currentSocket;
                        OutputStream toClient = acceptSocket.getOutputStream();
                        InputStream fromClient = acceptSocket.getInputStream();

                        int readByte = -1;

                        while ((readByte = fromClient.read()) != -1)
                                toClient.write(readByte);

                        acceptSocket.close();

                } catch(Exception ex){}
        }
}

