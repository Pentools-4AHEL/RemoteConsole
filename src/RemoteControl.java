import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import bernhard.scharrer.netapi.packet.Packet;
import bernhard.scharrer.netapi.server.Client;
import bernhard.scharrer.netapi.server.NetAPI;
import bernhard.scharrer.netapi.server.Server;
import bernhard.scharrer.netapi.server.TrafficManager;
import bernhard.scharrer.netapi.server.WindowsConsole;

public class RemoteControl {
	
	private static Process run;
	
	public static void main(String[] args) {
		
		Server server = NetAPI.start(args[0], Integer.parseInt(args[1]), new TrafficListener(), new WindowsConsole());
		server.start();
		
	}
	
	private static class TrafficListener implements TrafficManager {

		@Override
		public void connect(Client client) {}

		@Override
		public void disconnect(Client client) {}

		@Override
		public void receive(Client client, String msg) {
			try {
				run = Runtime.getRuntime().exec("cmd /c "+msg);
				
				BufferedReader input = new BufferedReader(new InputStreamReader(run.getInputStream()));
				
				String line = null, total = "";
				
				while ((line = input.readLine())!=null) {
					total = total + line + "\n";
				}
				
				total = total + "\n\n Returned with value: " + run.waitFor();
				
				client.send(total);
				
			} catch (IOException | InterruptedException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void receive(Client client, Packet packet) {}

		@Override
		public void receive(Client client, int[] data) {}

		@Override
		public void receive(Client client, float[] data) {}
		
	}
	
}
