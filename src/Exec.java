import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Exec extends AutoOP
{
	public Exec(String args) {
		this.args = args;
	}

	public Result run() throws Exception {
		Process p = Runtime.getRuntime().exec(this.args, null, new File(System.getProperty("user.home")));
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line = "";
		StringBuilder response = new StringBuilder();
		while ((line = input.readLine()) != null) {
			response.append(line);
			response.append("\n");
		}
		return new Result(response);
	}
}
