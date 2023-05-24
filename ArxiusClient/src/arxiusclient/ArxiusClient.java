package arxiusclient;

import java.net.Socket;
import java.util.ArrayList;
import tractamentarxius.arxius;


public class ArxiusClient {

    public static final String PATH = "arxius";
    
    public static void main(String[] args) {
        Boolean app = true;
        try
        {
            Socket client = new Socket("localhost", 5000);
            System.out.println("Conectant...\nEsperant resposta...\n");
            
            do
            {
                switch(Menu())
                {
                    case 1:
                        Enviar(client);
                        app = false;
                        break;

                    case 2:
                        Rebre(client);
                        app = false;
                        break;

                    case 0:
                        TancarServer(client);
                        app = false;
                        break;

                    default:
                        arxius.EnviarByte(client, -1);
                        System.out.println("No es pot realitzar aquesta opció");
                }
            }while(app);
            
            
            client.close();
            System.out.println("Desconectat");
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
    }
    
    public static int Menu()
    {
        int resposta = 0;
        
        System.out.println("INDICA L'ACCIÓ A REALITZAR");
        System.out.println(" 1. Enviar un arxiu");
        System.out.println(" 2. Rebre un arxiu");
        System.out.println(" 0. Tancar el servidor");
        System.out.print("\nResposta: ");
        resposta = Keyboard.readInt();
        
        return resposta;
    }
    
    public static void Enviar(Socket socket)
    {
        arxius.EnviarByte(socket, 1);
        
        ArrayList<String> files = new ArrayList<>();
        String resposta;
        Boolean existeix = false;
        
        System.out.println("\n\nENVIAR ARXIU\nIndica quin arxiu vols enviar\n");
        
        files = arxius.LlistaArxius(PATH);
        for(String s:files)
        {
            System.out.println("  " + s);
        }
        
        System.out.print("\nResposta: ");
        resposta = Keyboard.readString();
        
        for(String s:files)
        {
            if(s.equals(resposta))
            {
                existeix = true;
            }
        }
        
        if(existeix)
        {
            arxius.EnviarBool(socket, true);
            arxius.EnviarString(socket, resposta);
            arxius.LlegirArxiu(PATH + "\\" + resposta, socket);
            System.out.println("L'arxiu s'ha transferit correctament");

        }
        else
        {
            arxius.EnviarBool(socket, false);
            System.out.println("L'arxiu no existeix");
        }
    }
    
    public static void Rebre(Socket socket)
    {
        arxius.EnviarByte(socket, 2);
        
        ArrayList<String> files = new ArrayList<>();
        String resposta;
        Boolean existeix = false, afegir = true;
        
        System.out.println("\n\nREBRER ARXIU\nIndica quin arxiu vols rebre\n");
        
        while(afegir)
        {
            resposta = arxius.RebreString(socket);
            files.add(resposta);
            System.out.println("  " + resposta);
            afegir = arxius.RebreBool(socket);
        }
        
        System.out.print("\nResposta: ");
        resposta = Keyboard.readString();
        
        for(String s:files)
        {
            if(s.equals(resposta))
            {
                existeix = true;
            }
        }
        
        if(existeix)
        {
            arxius.EnviarBool(socket, true);
            arxius.EnviarString(socket, resposta);
            arxius.EscriureArxiu(PATH + "\\" + resposta, socket);
            System.out.println("L'arxiu s'ha transferit correctament");

        }
        else
        {
            arxius.EnviarBool(socket, false);
            System.out.println("L'arxiu no existeix");
        }
    }
    
    public static void TancarServer(Socket socket)
    {
        arxius.EnviarByte(socket, 0);
        
        System.out.println("\nServidor tancat");
    }
}
