import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {


    Socket socket;
    BufferedReader br;
    PrintWriter out;

    public Client(){
        try {
            System.out.println("requesting server");
            socket = new Socket("127.0.0.1",7777);
            System.out.println("connection done");

            
            br =  new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());


            startReading();
            startWritting();





        } catch (Exception e) {
            // TODO: handle exception
        }
    }


    public void startReading() {

        Runnable r1 =()->{

            System.out.println("reader started");

            try{

            while(true){

               

                String mssg = br.readLine();
                if(mssg.equals("exit")){
                    System.out.println("server has stopped");
                    socket.close();
                    break;
                }
                System.out.println("server : "+mssg);
                    
                
                
            }
        }catch(Exception e){
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
                    if(content.equals("exit")){
                        socket.close();
                    }
                    
               
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        };

        new Thread(r2).start();
    }






    public static void main(String[] args) {
        System.out.println("client starting.......");
        new Client();
    }
}
