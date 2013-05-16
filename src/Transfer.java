import org.zeromq.ZMQ;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Transfer extends ManualOP
{
	public void dispatch() {
		try {
			File file = new File(System.getProperty("user.home") + "/" + this.args);
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bufferedInput = new BufferedInputStream(fis);
			byte[] buffer = new byte[1024];
			String initPacket = "1 OK";
			System.out.println(initPacket);
			Server.socket.send(initPacket, ZMQ.SNDMORE);
			while ((bufferedInput.read(buffer)) != -1) {
				Server.socket.send(buffer, ZMQ.SNDMORE);
			}
			Server.socket.send("\0");
		} catch (Exception ex) {
			Server.socket.send("0 " + ex.getMessage());
		}
	}

}
