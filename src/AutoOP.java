abstract public class AutoOP extends Operation
{
	protected Result result;
	public Boolean addToHistory = true;

	public abstract Result run() throws Exception;

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	class Result
	{
		protected Object data;

		public Result(Object data) {
			this.data = data;
		}

		public Object getData() {
			return this.data;
		}

		public void setData(Object data) {
			this.data = data;
		}

		public String toString() {
			return this.data.toString();
		}
	}
}
