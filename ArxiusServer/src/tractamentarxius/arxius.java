package tractamentarxius;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class arxius {
    public static ArrayList<String> LlistaArxius(String path)
    {
        ArrayList<String> files = new ArrayList<>();
        
        try
        {
            Stream<Path> filenames = Files.list(Paths.get(path)).filter(Files::isRegularFile);
            List<Path> result = filenames.collect(Collectors.toList());
            
            for (Path p:result) 
            {
                files.add(p.toString().substring(7));
            }
            
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
        
        return files;
    }
    
    public static void LlegirArxiu(String ruta, Socket socket)
    {
        try
        {
            FileInputStream fis = new FileInputStream(ruta);

            int caracter = fis.read();
            EnviarByte(socket, caracter);
            while(caracter != -1)
            {
                    caracter = fis.read();
                    EnviarByte(socket, caracter);
            }

            fis.close();
        }
        catch(Exception e)
        {
                System.out.println(e.toString());
        }
    }
    
    public static void EscriureArxiu(String ruta, Socket socket)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(ruta);

            int caracter = RebreByte(socket);
            while(caracter != -1)
            {
                    fos.write(caracter);
                    caracter = RebreByte(socket);
            }

            fos.close();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public static void EnviarByte(Socket socket, int caracter)
    {
        try
        {
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(caracter);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public static int RebreByte(Socket socket)
    {
        int numero = 0;
        
        try
        {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            numero = (int) ois.readObject();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
        return numero;
    }
    
    public static void EnviarString(Socket socket, String caracter)
    {
        try
        {
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(caracter);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
    
    public static String RebreString (Socket socket)
    {
        String numero = null;
        
        try
        {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            numero = (String) ois.readObject();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
        return numero;
    }
    
    
    public static Boolean RebreBool(Socket socket)
    {
        Boolean numero = null;
        
        try
        {
            InputStream is = socket.getInputStream();
            ObjectInputStream ois = new ObjectInputStream(is);
            numero = (Boolean) ois.readObject();
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
        
        return numero;
    }
    
    public static void EnviarBool(Socket socket, Boolean caracter)
    {
        try
        {
            OutputStream os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(caracter);
        }
        catch(Exception e)
        {
            System.out.println(e.toString());
        }
    }
}
