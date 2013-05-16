public class List extends AutoOP
{
	public Result run() throws Exception {
		return new Exec("ls").run();
	}
}
