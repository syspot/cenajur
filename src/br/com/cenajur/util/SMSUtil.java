package br.com.cenajur.util;


public class SMSUtil {

	public void enviarMensagem(String tel, String msg) {

		System.out.println(msg);
		
		/*
		tel = tel.replaceAll("\\D", "");

		if (tel.length() == 8) {

			tel = "5571" + tel;

		} else if (tel.length() == 10) {

			tel = "55" + tel;

		} else if (tel.length() != 12) {

			return;

		}

		String urlString = "http://sms.televia.com.br/sms/sms.php?tel=param1&msg=param2";

		urlString = urlString.replace("param1", tel);
		urlString = urlString.replace("param2", msg);
		urlString = urlString.replaceAll(" ", "+");

		System.out.println(urlString);

		try {

			URL url = new URL(urlString);
			URLConnection connection = url.openConnection();
			connection.connect();
			connection.getContent();

			// HttpURLConnection connection =
			// (HttpURLConnection)url.openConnection();
			// connection.setRequestMethod("GET");
			// connection.connect();

			// HttpURLConnection connection = (HttpURLConnection)
			// url.openConnection();

			// connection.setRequestProperty("Request-Method", "GET");

			// connection.setDoInput(true);
			// connection.setDoOutput(true);

			// connection.connect();

		} catch (Exception e) {

			e.printStackTrace();

		}
		
		try {

			URL conexao = new URL(url.toString());

			URLConnection connection = conexao.openConnection();

			connection.connect();

			connection.getContent();

		} catch (Exception ex) {

		}
		
		*/

	}
}