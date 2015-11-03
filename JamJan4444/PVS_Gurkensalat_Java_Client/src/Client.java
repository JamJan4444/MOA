import java.net.*;
import java.io.*;

public class Client {

	String s1="Das Pferd frisst Gurkensalat\n";
	String s2="ENDE\n";
	
	public static void main(String args[]) {
		
		for(int i = 0;i < 100;i++){
			(new Client()).sendMsg();
		}
	}
		
	public void sendMsg(){
		
		try{
			
			Socket talkSocket = new Socket("localhost", 4711);
			BufferedReader fromServer = new BufferedReader(
					new InputStreamReader(talkSocket.getInputStream(),"Cp1252"));
			OutputStreamWriter toServer = new OutputStreamWriter(talkSocket.getOutputStream(), "Cp1252");
			

			System.out.println(s1);
			toServer.write(s1);
			toServer.flush();	// force message to be sent
			String result = fromServer.readLine(); // blocking read
			System.out.println("Receive: "+result);
			
			
			toServer.close();// close writer
			fromServer.close(); // close reader
			talkSocket.close(); // close socket (necessary??)
			
		}catch (Exception e){
			e.printStackTrace(); 
		}
	}
}