package pt.ist.bankai.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import pt.ist.bankai.BankaiConfig;
import fi.iki.elonen.NanoHTTPD;

public class BankaiServer extends NanoHTTPD {

	private String artifactId;

	public BankaiServer(String artifactId, int port) {
		super(port);
		this.artifactId = artifactId;
	}

	@Override
	public Response serve(String uri, Method method, Map<String, String> header, Map<String, String> parms,
			Map<String, String> files) {
		if (uri.startsWith("/api/")) {
			return handleAPIMethod(uri, method, header);
		} else {
			File file = null;
			uri = uri.replaceFirst("/", "");
			if (uri.equals(this.artifactId) || uri.equals(this.artifactId + "/")) {
				file = new File(BankaiConfig.DEFAULT_WEBAPP_PATH + this.artifactId + "/index.html");
			} else {
				file = new File(BankaiConfig.DEFAULT_WEBAPP_PATH + uri);
			}
			if (file.exists()) {

				try {
					String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getAbsolutePath());
					if (file.getPath().endsWith(".js")) {
						mimeType = "application/javascript";
					} else if (file.getPath().endsWith(".css")) {
						mimeType = "text/plain";
					}
					if (file.isFile()) {
						return new Response(Response.Status.OK, mimeType, FileUtils.openInputStream(file));
					} else {
						file = new File(file.getAbsolutePath() + "/index.html");
						if (file.exists()) {
							return new Response(Response.Status.OK, mimeType, FileUtils.openInputStream(new File(file
									.getAbsolutePath() + "/index.html")));
						} else {
							return new Response(Response.Status.NOT_FOUND, "plain/text", "NOT FOUND");
						}

					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} else {
				file = new File(uri);
				String urlString = "META-INF/resources/" + file.getPath();
				String mimeType = URLConnection.getFileNameMap().getContentTypeFor(file.getAbsolutePath());
				if (file.getPath().endsWith(".js")) {
					mimeType = "application/javascript";
				} else if (file.getPath().endsWith(".css")) {
					mimeType = "text/plain";
				}
				InputStream stream = BankaiServer.class.getClassLoader().getResourceAsStream(urlString);
				if (stream != null) {
					return new Response(Response.Status.OK, mimeType, stream);
				}
			}
		}
		return new Response(Response.Status.NOT_FOUND, "application/json", method.name() + " " + uri + " NOT FOUND");
	}

	private Response handleAPIMethod(String uri, Method method, Map<String, String> header) {
		uri = uri.replaceFirst("/", "");
		if (uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}
		String resourcePath = BankaiConfig.DEFAULT_WEBAPP_PATH + uri + "." + method.name() + ".json";
		File resource = new File(resourcePath);
		try {
			return new Response(Response.Status.OK, "application/json", FileUtils.openInputStream(resource));
		} catch (IOException e) {
			return new Response(Response.Status.NOT_FOUND, "application/json", method.name() + " " + uri + " NOT FOUND");
		}
	}

}
