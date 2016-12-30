package a21200800isec.cmcticket2.Assets;

import android.util.Log;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Created by red_f on 28/12/2016.
 */

public class TCPConnection {
    private boolean connected;
    private Socket socket;
    private int timeout;
    private InetAddress addr;
    private int port;

    public TCPConnection(InetAddress ip, int p) {
        this.addr = ip;
        this.port = p;
    }


    public boolean isConnect(){return this.connected;}
    public void setSocket(Socket s){
        this.socket  = s;
    }
    public void setAddress(InetAddress ip){
        this.addr = ip;
    }
    public void setPort(int p){ this.port = p;}

    public InetAddress getAddress(){ return this.addr;}
    public int getPort(){return this.port;}
    public Socket getSocket(){ return this.socket;}

    public void connect(){
        try {
            this.setSocket(new Socket(this.getAddress(),this.getPort()));
            this.connected = true;
        } catch (IOException ex) {
            Log.d("Erro","Can't connect");
        }
    }
    public Object readData(){
        Object obj = null;
        try {
            ObjectInputStream in = new ObjectInputStream(this.getSocket().getInputStream());
            obj = in.readObject();
        } catch (IOException ex) {
            System.out.println("Data access error:\n\t"+ex);
        } catch (ClassNotFoundException ex) {
            System.out.println("Data access error:\n\t"+ex);
        }
        return obj;
    }
    public void writeData(Object obj){
        try {

            ObjectOutputStream out = new ObjectOutputStream(this.getSocket().getOutputStream());
            out.writeObject(obj);
            out.flush();
        } catch (IOException ex) {
            System.out.println("Data access error:\n\t"+ex);
        }

    }

}
