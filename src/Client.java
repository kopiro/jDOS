import org.zeromq.ZMQ;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class Client
{

	private static Scanner s;
	static ZMQ.Socket socket;
	static OutputStream out;

	public static void parseSimpleRequest() {
		String str = socket.recvStr();
		System.out.println(str);
	}

	public static void parseTransferRequest(String filename) throws FileNotFoundException {
		try {
			out = new FileOutputStream(filename);
			String info = socket.recvStr();
			if (info.substring(0, 1).equals("1")) {
				while (true) {
					System.out.flush();
					byte[] r = socket.recv();
					out.write(r);
					if (!socket.hasReceiveMore()) break;
				}
				System.out.println("Transfer done!");
				out.close();
			} else {
				System.out.println(info.substring(2));
			}
		} catch (IOException e) {
			System.out.println("IO Excepiton " + e.getMessage());
		}
	}

	public static void main(String[] args) throws Exception {
		ZMQ.Context context = ZMQ.context(1);
		System.out.println("Coennecting to hello world server");
		socket = context.socket(ZMQ.REQ);
		socket.connect("tcp://127.0.0.1:5555");
		while (!Thread.currentThread().isInterrupted()) {
			s = new Scanner(System.in);
			String req = s.nextLine();
			socket.send(req);
			if (req.split(" ")[0].equals("transfer")) {
				parseTransferRequest(req.split(" ")[1]);
			} else {
				parseSimpleRequest();
			}
		}
		socket.close();
		context.term();
	}
}