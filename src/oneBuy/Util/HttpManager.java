package oneBuy.Util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import android.content.Context;


public class HttpManager {

	private static RestTemplate restTemplate;
	private static final String CHAVE_CONTENT_TYPE = "Content-Type";
	private static final String CHAVE_APP_JSON = "application/json";
	private static final int TIME_OUT_CONEXAO = 300000; // 30 segundos 
	
	public static RestTemplate getRestTemplate(Context contexto)
	{
		if(restTemplate == null)
		{
			restTemplate = new RestTemplate();
			
			//HttpClient.setContext(contexto);
			//DefaultHttpClient objHttpClient = new HttpClient((HttpParams) contexto);
			//restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(objHttpClient));		
			
			HttpManager objHttpManager = new HttpManager();			
			DefaultHttpClient objHttpClient = objHttpManager.getHttpClient();			
			restTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(objHttpClient));	
		}
		
		return restTemplate;
	}
	
	/**
	 * Cria um objeto do tipo DefaultHttpClient para permitir o acesso ao serviço por HTTPS (443) 
	 * @return Objeto do tipo DefaultHttpClient preparado para acesso à serviços HTTPS (443)
	 */
		public DefaultHttpClient getHttpClient() {
			try {

				KeyStore trustStore = KeyStore.getInstance(KeyStore
						.getDefaultType());
				trustStore.load(null, null);

				SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
				sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

				HttpParams params = new BasicHttpParams();
				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
				HttpProtocolParams.setUseExpectContinue(params, false);
							
				HttpConnectionParams.setConnectionTimeout(params, TIME_OUT_CONEXAO);
				HttpConnectionParams.setSoTimeout(params, TIME_OUT_CONEXAO);
				
				
				SchemeRegistry registry = new SchemeRegistry();
				registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				registry.register(new Scheme("https", sf, 443));

				ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);

				return new DefaultHttpClient(ccm, params);
			} catch (Exception e) {
				return new DefaultHttpClient();
			}
		}
		
		/**
		 * Classe para permitir o uso de certificado https auto-assinado
		 * @author mvargas
		 *
		 */
		private class MySSLSocketFactory extends SSLSocketFactory {
			SSLContext sslContext = SSLContext.getInstance("TLS");

			public MySSLSocketFactory(KeyStore truststore)
					throws NoSuchAlgorithmException, KeyManagementException,
					KeyStoreException, UnrecoverableKeyException {
				super(truststore);

				TrustManager tm = new X509TrustManager() {
					public void checkClientTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
					}

					public void checkServerTrusted(X509Certificate[] chain,
							String authType) throws CertificateException {
					}

					public X509Certificate[] getAcceptedIssuers() {
						return null;
					}
				};

				sslContext.init(null, new TrustManager[] { tm }, null);
			}

			@Override
			public Socket createSocket(Socket socket, String host, int port,
					boolean autoClose) throws IOException, UnknownHostException {
				return sslContext.getSocketFactory().createSocket(socket, host,
						port, autoClose);
			}

			@Override
			public Socket createSocket() throws IOException {
				return sslContext.getSocketFactory().createSocket();
			}
		}
		
	public static HttpHeaders headersPadrao()
	{
		// cabecalhos da requisição
		final HttpHeaders headers = new HttpHeaders();
		headers.add(CHAVE_CONTENT_TYPE, CHAVE_APP_JSON);
		return headers;		
	}
	
	public static HttpHeaders headersPadrao(final Context contexto) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ShortBufferException, NoSuchProviderException, UnsupportedEncodingException, NeoITException
	{	
		// cabecalhos da requisição
		final HttpHeaders headers = headersPadrao();

		return headers;
	}
	
	
}
