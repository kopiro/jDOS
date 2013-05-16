import java.util.ArrayList;

public class RequestDispatcher
{
	private static ArrayList<Operation> history = new ArrayList<Operation>();

	public static void dispatch(String request) {

		System.out.println(request);

		String requestOperation = request.split(" ")[0];
		String argsOperation = request.replaceFirst(requestOperation + " ", "");
		requestOperation = requestOperation.substring(0, 1).toUpperCase() + requestOperation.substring(1);

		try {

			Class DCA = Class.forName(requestOperation);
			Operation op = (Operation) DCA.newInstance();
			op.setArgs(argsOperation);

			try {
				if (op instanceof AutoOP) {
					AutoOP.Result result = ((AutoOP) op).run();
					Server.socket.send(result.toString());
					if (((AutoOP) op).addToHistory) {
						((AutoOP) op).setResult(result);
						RequestDispatcher.addOperation(op);
					}
				} else if (op instanceof ManualOP) {
					((ManualOP) op).dispatch();
				}
			} catch (Exception e) {
				Server.socket.send("Error while processing your result: " + e.getMessage());
			}

		} catch (ClassNotFoundException e) {
			Server.socket.send("Your operation is not valid");
		} catch (IllegalAccessException e) {
			Server.socket.send("Your operation is not illegal");
		} catch (InstantiationException e) {
			Server.socket.send("Your operation could not be instanced");
		}

	}

	public static ArrayList<Operation> getHistory() {
		return history;
	}

	public static void addOperation(Operation r) {
		history.add(r);
	}

	public static Operation getLastOperation() {
		return history.get(history.size() - 1);
	}

	public static Operation getLastOperationOfType(String type) {
		for (int i = history.size() - 1; i >= 0; i++) {
			if (history.get(i).getClass().getName().equals(type)) {
				return history.get(i);
			}
		}
		return null;
	}

}
