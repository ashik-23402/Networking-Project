import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    ServerSocket serverSocket;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Server(){

        try {
            serverSocket = new ServerSocket(7777);
            System.out.println("Server is ready to accept connection");
            System.out.println("server waiting......");
            socket=serverSocket.accept();

            br =  new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());

            startReading();
            startWritting();



        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public void startReading() {

        Runnable r1 =()->{

            System.out.println("reader started");

            try{

            while(true){

              

                String mssg = br.readLine();
                if(mssg.equals("exit")){
                    System.out.println("clien has stopped");
                    break;
                }
                System.out.println("client : "+mssg);
                    
               
                
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }


        };

        new Thread(r1).start();
        
    }

    public void startWritting() {
        Runnable r2 = ()->{

            try{

            while( !socket.isClosed()){

                System.out.println("writting started.......");

                
                    BufferedReader br1 = new BufferedReader(new InputStreamReader(System.in));

                    String content = br1.readLine();
                    out.println(content);
                    out.flush();
                    
                
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        };

        new Thread(r2).start();
    }



    public static void main(String[] args) {
        System.out.println("going to start server...." );
        new Server();
    }
}
