package arxiusserver;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import tractamentarxius.arxius;


public class ArxiusServer {

    public static final int PORT = 5000;
    private static final int MAXNUM = 999999;
    public static final String PATH = "arxius";
    
    public static void main(String[] args) {        
        try
        {
            ServerSocket server = new ServerSocket(PORT);
            System.out.println("Socket del servidor obert, esperant connexions...\nServidor connectat");
            Boolean run = true;
            
            while(run)
            {
                Socket client = server.accept();
                System.out.println("\n\nNova conexió\nIP del client: " 
                        + client.getInetAddress().getHostAddress());
                
                switch(arxius.RebreByte(client))
                {
                    case 1: //Client envia
                        System.out.println("Rebent arxiu");
                        Rebre(client);
                        break;
                        
                    case 2://Client retira
                        System.out.println("Enviant arxiu");
                        Enviar(client);
                        break;
                        
                    case 0:
                        run = false;
                        break;
                        
                    default:
                        System.out.println("ඞ");
                        break;
                }
                
                client.close();
            }
            
            System.out.println("Servidor tancat");
            server.close();
            
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public static void Rebre(Socket socket)
    {
        String nomarxiu;
        
        if(arxius.RebreBool(socket))
        {
            nomarxiu = arxius.RebreString(socket);
            arxius.EscriureArxiu(PATH + "\\" + nomarxiu, socket);
            
            System.out.println("Arxiu rebut: " + nomarxiu);
        }
    }
    
    public static void Enviar (Socket socket)
    {
        ArrayList<String> files = new ArrayList<>();
        String nomarxiu;

        files = arxius.LlistaArxius(PATH);
        
        for(int i = 0; i < files.size(); i++)
        {
            String a = files.get(i);
            arxius.EnviarString(socket, a);
            
            if(i == (files.size() - 1))
            {
                arxius.EnviarBool(socket, false);
            }
            else
            {
                arxius.EnviarBool(socket, true);
            }
        }
        
        if(arxius.RebreBool(socket))
        {
            nomarxiu = arxius.RebreString(socket);
            arxius.LlegirArxiu(PATH + "\\" + nomarxiu, socket);
            System.out.println("Arxiu enviat: " + nomarxiu);
        }
       
    }
}
