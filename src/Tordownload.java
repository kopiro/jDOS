public class Tordownload extends AutoOP
{
	public Result run() throws Exception {
		Torsearch.Torrent[] torrents;
		int index = 0;
		try {
			index = Integer.parseInt(this.args) - 1;
			Operation lastOp = RequestDispatcher.getLastOperationOfType("Torsearch");
			torrents = (Torsearch.Torrent[]) ((AutoOP) lastOp).getResult().getData();
			if (index < 0 || index > torrents.length) {
				return new Result("Index out of bounds!");
			}
		} catch (NumberFormatException e) {
			torrents = (new Torsearch()).getTorrents(this.args);
			index = 0;
		}
		try {
			this.openMagnet(torrents[index]);
			return new Result("OK");
		} catch (Exception e) {
			return new Result("Failed");
		}
	}

	public Process openMagnet(Torsearch.Torrent torrent) throws Exception {
		return Runtime.getRuntime().exec("open " + torrent.getMagnet());
	}

}
