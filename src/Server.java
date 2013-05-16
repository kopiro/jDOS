import org.zeromq.ZMQ;

public class Server
{
	static ZMQ.Socket socket;

	public static void main(String[] args) throws Exception {
		ZMQ.Context context = ZMQ.context(1);
		socket = context.socket(ZMQ.REP);
		socket.bind("tcp://*:5555");
		System.out.println("Server started");
		while (!Thread.currentThread().isInterrupted()) {
			RequestDispatcher.dispatch(socket.recvStr());
		}
		socket.close();
		context.term();
	}
}