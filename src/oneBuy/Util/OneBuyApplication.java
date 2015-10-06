package oneBuy.Util;

import java.util.UUID;

import android.app.Application;
import android.content.Context;

public class OneBuyApplication extends Application {
	private String mCodigoIntegracaoLoja;
	private String mCultura;
	private String mIdentificadorUsuarioTransacao;
	private String mCodigoAutorizacao;
	private String mIdentificadorAparelho;
	private static Context context;
	private String mCodigoTransacaoGateway;
	private Long mValorPedido;
	
	private static OneBuyApplication instance;
	
	private OneBuyApplication(){}
	   
	public static synchronized OneBuyApplication getInstance(){
		if(instance==null){
			instance=new OneBuyApplication();
		}
		return instance;
	}
	
    @Override
    public void onCreate() {
        super.onCreate();  
        OneBuyApplication.context = getApplicationContext();
    }
    
    public static void setAppContext(Context contexto) {
		OneBuyApplication.context = contexto.getApplicationContext();
	}
	
	public static Context getAppContext() {
		return OneBuyApplication.context;
	}

	public String getCodigoIntegracaoLoja() {
		return mCodigoIntegracaoLoja;
	}

	public void setCodigoIntegracaoLoja(String codigoIntegracaoLoja) {
		mCodigoIntegracaoLoja = codigoIntegracaoLoja;
	}

	public void setCultura(String cultura) {
		this.mCultura = cultura;
	}

	public String getCultura() {
		return mCultura;
	}

	public String getIdentificadorUsuarioTransacao() {
		
		if(mIdentificadorUsuarioTransacao == null ||
				mIdentificadorUsuarioTransacao == "")
		{
			mIdentificadorUsuarioTransacao = UUID.randomUUID().toString();
			OneBuyApplication aplicacao = OneBuyApplication.getInstance();
			aplicacao.setIdentificadorUsuarioTransacao(mIdentificadorUsuarioTransacao);
		}
		
		return mIdentificadorUsuarioTransacao;
	}

	public void setIdentificadorUsuarioTransacao(String identificadorUsuarioTransacao) {
		mIdentificadorUsuarioTransacao = identificadorUsuarioTransacao;
	}
	
	public String getCodigoAutorizacao() {
		return mCodigoAutorizacao;
	}

	public void setCodigoAutorizacao(String codigoAutorizacao) {
		mCodigoAutorizacao = codigoAutorizacao;
	}

	public String getIdentificadorAparelho() {
		
		if(mIdentificadorAparelho == null ||
				mIdentificadorAparelho == "")
		{
			mIdentificadorAparelho = new Util().getIdentificadorAparelho();
			OneBuyApplication aplicacao = OneBuyApplication.getInstance();
			aplicacao.setIdentificadorAparelho(mIdentificadorAparelho);
		}
		
		return mIdentificadorAparelho;
	}

	public void setIdentificadorAparelho(String identificadorAparelho) {
		mIdentificadorAparelho = identificadorAparelho;
	}
	
	public String getCodigoTransacaoGateway() {
		return mCodigoTransacaoGateway;
	}

	public void setCodigoTransacaoGateway(String codigoTransacaoGateway) {
		mCodigoTransacaoGateway = codigoTransacaoGateway;
	}
	
	public Long getValorPedido() {
		return mValorPedido;
	}

	public void setValorPedido(Long valorPedido) {
		mValorPedido = valorPedido;
	}
		
	
	
	/**
	 * Metodo responsavel por garantir sempre um novo fluxo ao ObterAutorizacao 
	 * @param contexto
	 */
	public void LimparValorApplication(Context contexto)
	{
		OneBuyApplication aplicacao = OneBuyApplication.getInstance();
		aplicacao.setCultura(null);
		aplicacao.setCodigoIntegracaoLoja(null);
		aplicacao.setIdentificadorUsuarioTransacao(null);
		aplicacao.setCodigoAutorizacao(null);
	}
	
}
