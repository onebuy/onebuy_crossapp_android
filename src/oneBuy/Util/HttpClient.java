package oneBuy.Util;

import oneBuy.CrossAppApi.Android.R;

import java.io.InputStream;
import java.security.KeyStore;
import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

import android.content.Context;

public class HttpClient extends DefaultHttpClient {
	private static final int TIME_OUT_CONEXAO = 300000; // 30 segundos 
	private static Context context;
 
	public static void setContext(Context context) {
		HttpClient.context = context;
	}

	public HttpClient(HttpParams params) {
	  super(params);
	}

	public HttpClient(ClientConnectionManager httpConnectionManager, HttpParams params) {
		super(httpConnectionManager, params);
	}

	@Override
    protected ClientConnectionManager createClientConnectionManager() {
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		registry.register(new Scheme("https", newSslSocketFactory(), 443));
		
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		HttpProtocolParams.setUseExpectContinue(params, false);
					
		HttpConnectionParams.setConnectionTimeout(params, TIME_OUT_CONEXAO);
		HttpConnectionParams.setSoTimeout(params, TIME_OUT_CONEXAO);
		
		return new SingleClientConnManager(params, registry);
    }
	 
    private SSLSocketFactory newSslSocketFactory() {
        try {
            // Obter uma instância do formato Bouncy Castle KeyStore
            KeyStore trusted = KeyStore.getInstance("BKS");
            // Obter o recurso raw, que contém o armazenamento de chaves com
            // seus certificados confiáveis (raiz e quaisquer certs intermédios)
            InputStream in = HttpClient.context.getResources().openRawResource(R.raw.onebuyssl); //nome do seu arquivo de armazenamento de chaves
            try {
                // Forneça a senha do armazenamento de chaves
                trusted.load(in, HttpClient.context.getResources().getString(R.string.senhaCertificado).toCharArray());
            } finally {
                in.close();
            }
            // Passe o armazenamento de chaves para o SSLSocketFactory. 
            // A Factory é responsável pela verificação do certificado do servidor.
            SSLSocketFactory sf = new SSLSocketFactory(trusted);
            // Verificação Hostname de certificado
            sf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER); // This can be changed to less stricter verifiers, according to need
            return sf;
        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
	  
    
    /**
     * Cria um objeto do tipo DefaultHttpClient para permitir o acesso ao serviço por HTTPS (443) 
     * @return Objeto do tipo DefaultHttpClient preparado para acesso à serviços HTTPS (443)
     */
    /*
	public DefaultHttpClient getHttpClient(Context contexto) {
		try {
		
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
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
    */  	
	/**
	 * Classe para permitir o uso de certificado https auto-assinado
	 * @author mvargas
	 *
	 */
    /*
	private class MySSLSocketFactory extends SSLSocketFactory {
		SSLContext sslContext = SSLContext.getInstance("TLS");

		public MySSLSocketFactory(KeyStore truststore) 
				throws NoSuchAlgorithmException, KeyManagementException,
				KeyStoreException, UnrecoverableKeyException {
			super(truststore);

			TrustManager tm = new X509TrustManager() {
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
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
	*/
	    
	    
	    
	    
	    
}
