import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Torsearch extends AutoOP
{
	public Result run() throws Exception {
		Torrent[] torrents = this.getTorrents(this.args);
		return new Result(torrents)
		{
			public String toString() {
				StringBuffer sb = new StringBuffer();
				int i = 0;
				for (Torrent t : (Torrent[]) this.data) {
					sb.append(++i).append(") ").append(t.toString()).append("\n");
				}
				return sb.toString();
			}
		};
	}

	private Gson gson;

	public Torsearch() {
		this.gson = new Gson();
	}

	public Torrent[] getTorrents(String query) throws Exception {
		String request = this.getRequest(query);
		System.out.println(request);
		return this.gson.fromJson(request, Torrent[].class);
	}

	private String getRequest(String query) throws Exception {
		URL url = new URL("http://apify.ifc0nfig.com/tpb/search?id=" + query);
		HttpURLConnection c = (HttpURLConnection) url.openConnection();
		c.setRequestProperty("Content-type", "application/json");
		c.setUseCaches(true);
		c.setDoInput(true);
		c.setDoOutput(true);
		InputStream is = c.getInputStream();
		BufferedReader rd = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line;
		while ((line = rd.readLine()) != null) {
			buffer.append(line);
		}
		c.disconnect();
		return buffer.toString();
	}

	class Torrent
	{
		private int id;
		private String name;
		private String category;
		private String magnet;
		private String uploaded;
		private String size;
		private int seeders;
		private int leechers;

		public Torrent() {
		}

		public String toString() {
			return String.format("%s (S:%d, L:%d, %s", this.name, this.seeders, this.leechers, this.size);
		}

		public int getID() {
			return id;
		}

		public String getName() {
			return name;
		}

		public String getCategory() {
			return category;
		}

		public String getMagnet() {
			return magnet;
		}

		public String getUploaded() {
			return uploaded;
		}

		public String getSize() {
			return size;
		}

		public int getSeeders() {
			return seeders;
		}

		public int getLeechers() {
			return leechers;
		}
	}
}

