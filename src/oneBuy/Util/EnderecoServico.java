package oneBuy.Util;

public class EnderecoServico {
	
	//Homolog
	public final static String URL_BASE					 		= "https://d-api.onebuy.com/hub/v1/";
	
	//Obter Autorização 
	public final static String URL_OBTER_AUTORIZACAO	  	 = URL_BASE.concat("IntegracaoCrossApp/ObterAutorizacao");
	
	public final static String URL_CAPTURAR_PEDIDO	  	     = URL_BASE.concat("IntegracaoCrossApp/CapturarPedido");
	
	public final static String URL_REALIZAR_CHECKOUT	  	 = URL_BASE.concat("IntegracaoLoja/RealizarCheckoutOneBuy");
	
	public final static String URL_CRIAR_PEDIDO	  	 		= URL_BASE.concat("IntegracaoLoja/CriarPedido");
	
	public final static String URL_SONDAR_PEDIDO	 		= URL_BASE.concat("IntegracaoLoja/SondarPedido");

}
