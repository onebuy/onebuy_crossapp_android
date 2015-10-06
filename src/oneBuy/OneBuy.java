package oneBuy;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import oneBuy.CrossAppApi.Android.R;
import oneBuy.Request.AutorizacaoCrossAppRequest;
import oneBuy.Request.CapturaPedidoCrossAppRequest;
import oneBuy.Request.CheckoutOneBuyDadosRequest;
import oneBuy.Request.DadosIdentificacaoRequest;
import oneBuy.Request.PedidoRequest;
import oneBuy.Request.SondaPedidoRequest;
import oneBuy.Response.AutorizacaoResponse;
import oneBuy.Response.CapturaPedidoResponse;
import oneBuy.Response.CheckoutOneBuyDadosResponse;
import oneBuy.Response.PedidoResponse;
import oneBuy.Response.RetornoResponse;
import oneBuy.Response.SondaPedidoResponse;
import oneBuy.Util.Criptografia;
import oneBuy.Util.EnderecoServico;
import oneBuy.Util.HttpManager;
import oneBuy.Util.OneBuyApplication;
import oneBuy.Util.TaskCompleteAbrirAppOneBuy;
import oneBuy.Util.TaskCompleteCapturarPedido;
import oneBuy.Util.TaskCompleteCriarPedido;
import oneBuy.Util.TaskCompleteRealizarCheckOut;
import oneBuy.Util.TaskCompleteSondaPedido;
import oneBuy.Util.Util;

public class OneBuy
{
	private Context contexto;
	private String mCultura;
	private String mCulturaPadrao = "pt-BR";
	private String mCodigoIntegracaoLoja;
	private asyncTaskObterAutorizacao asyncTaskObterAutorizacao;
	private RetornoResponse objRetornoResponse;
	private AutorizacaoResponse objAutorizacaoResponse;
	private TaskCompleteAbrirAppOneBuy mTaskCompleteAbrirAppOneBuy;
	
	private asyncTaskRealizarCheckOut asyncTaskRealizarCheckOut;
	private TaskCompleteRealizarCheckOut mTaskCompleteRealizarCheckOut;
	private CheckoutOneBuyDadosResponse objCheckoutOneBuyDadosResponse;
	
	private asyncTaskCriarPedido asyncTaskCriarPedido;
	private TaskCompleteCriarPedido mTaskCompleteCriarPedido;
	private PedidoResponse objPedidoResponse;
	
	private asyncTaskCapturarPedido asyncTaskCapturarPedido;
	private TaskCompleteCapturarPedido mTaskCompleteCapturarPedido;
	private CapturaPedidoResponse objCapturaPedidoResponse;
	
	private asyncTaskSondarPedido asyncTaskSondarPedido;
	private TaskCompleteSondaPedido mTaskCompleteSondaPedido;
	private SondaPedidoResponse objSondaPedidoResponse;
	
	public void setCultura(String cultura) {
		OneBuyApplication aplicacao = OneBuyApplication.getInstance();
		if(aplicacao != null)
		{
			aplicacao.setCultura(cultura);	
		}
		
		mCultura = cultura;
	}
	
	private String getCultura() {
		
		if(mCultura == null || mCultura == "")
		{
			mCultura = mCulturaPadrao;
			OneBuyApplication aplicacao = OneBuyApplication.getInstance();
			aplicacao.setCultura(mCulturaPadrao);	
		}
		
		return mCultura;
	}
	
	public void setCodigoIntegracaoLoja(String codigoIntegracaoLoja) {
		mCodigoIntegracaoLoja = codigoIntegracaoLoja;
	}
	
	private String getCodigoIntegracaoLoja() {
		return mCodigoIntegracaoLoja;
	}
	
	public void setTaskCompleteAbrirAppOneBuy(TaskCompleteAbrirAppOneBuy objTaskComplete) {
		mTaskCompleteAbrirAppOneBuy = objTaskComplete;
	}
	
	private TaskCompleteAbrirAppOneBuy getTaskCompleteAbrirAppOneBuy() {
		return mTaskCompleteAbrirAppOneBuy;
	}
	
	public void setTaskCompleteRealizarCheckOut(TaskCompleteRealizarCheckOut objTaskComplete) {
		mTaskCompleteRealizarCheckOut = objTaskComplete;
	}
	
	private TaskCompleteRealizarCheckOut getTaskCompleteRealizarCheckOut() {
		return mTaskCompleteRealizarCheckOut;
	}
	
	public void setTaskCompleteCriarPedido(TaskCompleteCriarPedido objTaskComplete) {
		mTaskCompleteCriarPedido = objTaskComplete;
	}
	
	private TaskCompleteCriarPedido getTaskCompleteCriarPedido() {
		return mTaskCompleteCriarPedido;
	}
	
	public void setTaskCompleteCapturarPedido(TaskCompleteCapturarPedido objTaskComplete) {
		mTaskCompleteCapturarPedido = objTaskComplete;
	}
	
	private TaskCompleteCapturarPedido getTaskCompleteCapturarPedido() {
		return mTaskCompleteCapturarPedido;
	}
	
	public void setTaskCompleteSondaPedido(TaskCompleteSondaPedido objTaskComplete) {
		mTaskCompleteSondaPedido = objTaskComplete;
	}
	
	private TaskCompleteSondaPedido getTaskCompleteSondaPedido() {
		return mTaskCompleteSondaPedido;
	}
	
	public OneBuy(Context contextoCorrente)
	{
		if(contextoCorrente != null)
		{
			contexto = contextoCorrente;
		}
	}
			
	/**
	 * Inicia o fluxo de obter autorização e dispara a chamada apara aplicação do OneBuy para gerar o Token
	 * @param cultura
	 * @param codigoIntegracaoLoja
	 * @return RetornoResponse
	 * @throws Exception 
	 */
	public void AbrirAppOneBuy() {
		
		//Verifica se o AppCliente do OneBuy esta instalado
		if(Util.isOneBuyInstalado(contexto))
		{
			asyncTaskObterAutorizacao = new asyncTaskObterAutorizacao();
			asyncTaskObterAutorizacao.execute();
		}
		else
		{
			//Se não estiver instalado abrir o market
			Util.abrirMarket(contexto);
		}
	}
	
	/**
	 * Metodo responsavel por efetuar a Chamada do ObterAutorizacao
	 * @param cultura
	 * @param codigoIntegracaoLoja
	 * @param identificadorUsuarioTransacao
	 * @return AutorizacaoResponse pode ser null, tratar
	 * @throws HttpStatusCodeException
	 * @throws ResourceAccessException
	 * @throws Exception
	 */
	private AutorizacaoResponse ObterAutorizacao(String cultura,String codigoIntegracaoLoja,String identificadorUsuarioTransacao) throws HttpStatusCodeException,ResourceAccessException, Exception {
		
		AutorizacaoResponse objAutorizacaoResponse = null;
		//Consome o serviço
		try
		{	
			AutorizacaoCrossAppRequest objAutorizacaoCrossAppRequest = new AutorizacaoCrossAppRequest();
			objAutorizacaoCrossAppRequest.Cultura = cultura;
			objAutorizacaoCrossAppRequest.CodigoIntegracaoLoja = codigoIntegracaoLoja;
			objAutorizacaoCrossAppRequest.IdentificadorUsuarioTransacao = identificadorUsuarioTransacao;
			
			//Cria o client do serviço
			final RestTemplate restTemplate = HttpManager.getRestTemplate(contexto);
			
			//Header de autenticação
			final HttpEntity<AutorizacaoCrossAppRequest> entity = new HttpEntity<AutorizacaoCrossAppRequest>(objAutorizacaoCrossAppRequest, oneBuy.Util.HttpManager.headersPadrao());		
			
			final String url = String.format(EnderecoServico.URL_OBTER_AUTORIZACAO);
			final ResponseEntity<AutorizacaoResponse> retorno = restTemplate.exchange(url, HttpMethod.POST, entity, AutorizacaoResponse.class);
			
			//Retorno
			objAutorizacaoResponse = retorno.getBody();
			
			if(objAutorizacaoResponse != null &&
					objAutorizacaoResponse.RetornoResponse.Sucesso)
			{
				//Atualiza o objeto compartilhado
				OneBuyApplication aplicacao = OneBuyApplication.getInstance();
				aplicacao.setCodigoAutorizacao(objAutorizacaoResponse.CodigoAutorizacao);
				aplicacao.setCultura(cultura);
				aplicacao.setCodigoIntegracaoLoja(codigoIntegracaoLoja);
				aplicacao.setIdentificadorUsuarioTransacao(identificadorUsuarioTransacao);
			}
			else {
				//Não devemos fazer nada
			}
		}
		catch (HttpStatusCodeException objHttpStatusCodeException) 
		{
			throw objHttpStatusCodeException;		
		}
		catch (ResourceAccessException objResourceAccessException) 
		{
			throw objResourceAccessException;			
		} 
		catch (Exception objException) 
		{
			throw objException;			
		}
		
		return objAutorizacaoResponse;
	}
	
	/**
	 * Metodo responsavel por realizar o checkout através do token e retorna os dados do usuário
	 * @param tokenOneBuy
	 * @return Retorna os dados do usuario já descriptografados
	 * @throws HttpStatusCodeException
	 * @throws ResourceAccessException
	 * @throws Exception
	 */
	public void RealizarCheckOut(String tokenOneBuy) {
		
		//Verifica se o AppCliente do OneBuy esta instalado
		if(Util.isOneBuyInstalado(contexto))
		{
			asyncTaskRealizarCheckOut = new asyncTaskRealizarCheckOut();
			asyncTaskRealizarCheckOut.execute(tokenOneBuy);
		}
		else
		{
			//Se não estiver instalado abrir o market
			Util.abrirMarket(contexto);
		}
	}
	
	/**
	 * Responsavel por criar o pedido no OneBuy
	 * @param objPedidoRequest
	 * @return
	 * @throws HttpStatusCodeException
	 * @throws ResourceAccessException
	 * @throws Exception
	 */
	public void CriarPedido(PedidoRequest objPedidoRequest) {
		
		//Verifica se o AppCliente do OneBuy esta instalado
		if(Util.isOneBuyInstalado(contexto))
		{
			asyncTaskCriarPedido = new asyncTaskCriarPedido();
			asyncTaskCriarPedido.execute(objPedidoRequest);
		}
		else
		{
			//Se não estiver instalado abrir o market
			Util.abrirMarket(contexto);
		}
	}
	
	public void SondarPedido(){
		
		//Verifica se o AppCliente do OneBuy esta instalado
		if(Util.isOneBuyInstalado(contexto))
		{
			asyncTaskSondarPedido = new asyncTaskSondarPedido();
			asyncTaskSondarPedido.execute();
		}
		else
		{
			//Se não estiver instalado abrir o market
			Util.abrirMarket(contexto);
		}
	}
	
	public void CapturarPedido() {
		//Verifica se o AppCliente do OneBuy esta instalado
		if(Util.isOneBuyInstalado(contexto))
		{
			asyncTaskCapturarPedido = new asyncTaskCapturarPedido();
			asyncTaskCapturarPedido.execute();
		}
		else
		{
			//Se não estiver instalado abrir o market
			Util.abrirMarket(contexto);
		}
	}
	
	/**
	 * Metodos auxiliares pela obtenção do auth 
	 */
	
	/**
	 * Responsavel por obter a autorização
	 * @author fabio.janssen
	 *
	 */
	class asyncTaskObterAutorizacao extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {
			AbrirAplicativoCrossAppOneBuy();
			return null;
		}

		@Override
		protected void onPostExecute(Void ex) {
			super.onPostExecute(ex);
			
			TratarRetornoAbrirAplicativoCrossAppOneBuy();
		}
	}
	
	/**
	 * Obter Autorização de Checkout
	 */
	private void AbrirAplicativoCrossAppOneBuy() {
		try {
			objRetornoResponse = new RetornoResponse();
			
			OneBuyApplication aplicacao = OneBuyApplication.getInstance();
			aplicacao.LimparValorApplication(contexto);
			
			String identificadorUsuarioTransacao = aplicacao.getIdentificadorUsuarioTransacao();
			
			String cultura = getCultura();
			
			if(cultura == null || cultura == "")
			{
				cultura = mCulturaPadrao;
			}
			
			String codigoIntegracaoLoja = getCodigoIntegracaoLoja();
			
			if(cultura != null && codigoIntegracaoLoja != null && identificadorUsuarioTransacao != null)
			{
				objAutorizacaoResponse = ObterAutorizacao(cultura, codigoIntegracaoLoja, identificadorUsuarioTransacao);
				
				//Trata o retorno
				if(objAutorizacaoResponse.RetornoResponse.Sucesso)
				{
					objRetornoResponse.Sucesso = true;
				}
				else
				{
					objRetornoResponse.Mensagem.add(objAutorizacaoResponse.RetornoResponse.Mensagem.get(0));
				}	
			}
			else
			{
				objRetornoResponse.Mensagem.add(contexto.getResources().getString(R.string.erroFalhaInesperada));
			}
			
			
		}
		catch (HttpStatusCodeException objHttpStatusCodeException) {
			objRetornoResponse.Mensagem.add(objHttpStatusCodeException.getMessage());
		} catch (ResourceAccessException objResourceAccessException) {
			objRetornoResponse.Mensagem.add(objResourceAccessException.getMessage());
		} catch (Exception objException) {
			objRetornoResponse.Mensagem.add(objException.getMessage());
		}
	}
	
	/**
	 * Tratar retorno da obtenção do auth 
	 */
	private void TratarRetornoAbrirAplicativoCrossAppOneBuy()
	{
		if(objRetornoResponse.Sucesso && objAutorizacaoResponse.CodigoAutorizacao != null)
		{
			//Abrir aplicação OneBuy.Android.Cliente e gerar o token
			try {
				this.ObterTokenOneBuy(objAutorizacaoResponse.CodigoAutorizacao);
			} catch (Exception e) {
				objRetornoResponse.Mensagem.add(e.getMessage());
			}	
		}
		
		if(getTaskCompleteAbrirAppOneBuy() != null)
		{
			getTaskCompleteAbrirAppOneBuy().onTaskListenerComplete.onTaskCompleted(objRetornoResponse);	
		}
	}
	
	/**
	 * Efetua a chamada para o AppCliente do OneBuy e obtem o token criptografado
	 * @return Token descriptografado pronto para usar
	 * @throws Exception 
	 */
	public void ObterTokenOneBuy(String codigoAutorizacao) throws Exception {
		
		try
		{
			Intent sendIntent = new Intent();
			sendIntent.setAction(contexto.getString(R.string.ONEBUY_FLUXOCROSSAPP));
			sendIntent.putExtra(contexto.getString(R.string.URLRETORNO), contexto.getString(R.string.ONEBUY_FLUXOCROSSAPP_RECEBERTOKEN));		
			sendIntent.putExtra(contexto.getString(R.string.CODIGO_AUTORIZACAO), codigoAutorizacao);		
			contexto.getApplicationContext().sendBroadcast(sendIntent);
		}
		catch(IllegalArgumentException e)
		{
			throw new Exception(e.getMessage()); 
		}
	}
	
	/**
	 * FIM
	 */
	
	/**
	 * Metodos auxiliares para realizar checkout
	 */
	
	/**
	 * Chama o checkout para obter os dados do usuário com base no token 
	 * @author fabio.janssen
	 *
	 */
	class asyncTaskRealizarCheckOut extends AsyncTask<String, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(String... params) {
			
			AbrirRealizarCheckOut(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void ex) {
			super.onPostExecute(ex);
			TratarRetornoAbrirRealizarCheckOut();
			
		}
	}
	
	/**
	 * Chama o serviço de checkout
	 * @param tokenOneBuy
	 * @return
	 */
	private CheckoutOneBuyDadosResponse AbrirRealizarCheckOut(String tokenOneBuy)
	{
		//Consome o serviço
		try
		{	
			objCheckoutOneBuyDadosResponse = new CheckoutOneBuyDadosResponse();
			CheckoutOneBuyDadosRequest objCheckoutOneBuyDadosRequest = new CheckoutOneBuyDadosRequest();
			
			//Consulta os dados da Application
			OneBuyApplication objOneBuyApplication = OneBuyApplication.getInstance();
			
			String tokenDescriptografado = "";
			
			if(objOneBuyApplication.getIdentificadorUsuarioTransacao() != null)
			{
				//Descriptografar Token
				tokenDescriptografado = Criptografia.DescriptografarSimples(tokenOneBuy, objOneBuyApplication.getIdentificadorUsuarioTransacao());
				
				objCheckoutOneBuyDadosRequest.TokenOneBuy = tokenDescriptografado;
				objCheckoutOneBuyDadosRequest.CodigoAutorizacao = objOneBuyApplication.getCodigoAutorizacao();
				objCheckoutOneBuyDadosRequest.IdentificadorUsuario = objOneBuyApplication.getIdentificadorAparelho();
				
				//Cria o client do serviço
				final RestTemplate restTemplate = HttpManager.getRestTemplate(contexto);
				
				//Header de autenticação
				final HttpEntity<CheckoutOneBuyDadosRequest> entity = new HttpEntity<CheckoutOneBuyDadosRequest>(objCheckoutOneBuyDadosRequest, oneBuy.Util.HttpManager.headersPadrao());		
				
				final String url = String.format(EnderecoServico.URL_REALIZAR_CHECKOUT);
				final ResponseEntity<CheckoutOneBuyDadosResponse> retorno = restTemplate.exchange(url, HttpMethod.POST, entity, CheckoutOneBuyDadosResponse.class);
				
				//Retorno
				objCheckoutOneBuyDadosResponse = retorno.getBody();
				
				if(objCheckoutOneBuyDadosResponse != null &&
						objCheckoutOneBuyDadosResponse.RetornoResponse.Sucesso)
				{
					//Descriptografar os dados do usuário retornado
					objCheckoutOneBuyDadosResponse.DadosUsuarioCheckout = new Criptografia().DescriptografarDadosUsuarioCheckout(objCheckoutOneBuyDadosResponse.DadosUsuarioCheckout,objOneBuyApplication.getCodigoAutorizacao());
				}
				else
				{
					//Não devemos fazer nada
				}
			}
			else
			{
				throw new Exception("Não foi possivél descriptografar o Token!");
			}
			
		} catch (HttpStatusCodeException objHttpStatusCodeException) {
			objCheckoutOneBuyDadosResponse.RetornoResponse = new RetornoResponse();
			objCheckoutOneBuyDadosResponse.RetornoResponse.Mensagem.add(objHttpStatusCodeException.getMessage());
		} catch (ResourceAccessException objResourceAccessException) {
			objCheckoutOneBuyDadosResponse.RetornoResponse = new RetornoResponse();
			objCheckoutOneBuyDadosResponse.RetornoResponse.Mensagem.add(objResourceAccessException.getMessage());
		} catch (Exception objException) {
			objCheckoutOneBuyDadosResponse.RetornoResponse = new RetornoResponse();
			objCheckoutOneBuyDadosResponse.RetornoResponse.Mensagem.add(objException.getMessage());
		}
		
		return objCheckoutOneBuyDadosResponse;
		
	}
	
	/**
	 * Tratar retorno do realizar checkout 
	 */
	private void TratarRetornoAbrirRealizarCheckOut()
	{
		if(getTaskCompleteRealizarCheckOut() != null)
		{
			getTaskCompleteRealizarCheckOut().onTaskListenerComplete.onTaskCompleted(objCheckoutOneBuyDadosResponse);	
		}
	}
	
	/**
	 * FIM
	 */
	
	/**
	 * Metodos auxiliares para criar pedido
	 */
	
	/**
	 * Chama o criar pedido no onebuy
	 * @author fabio.janssen
	 *
	 */
	class asyncTaskCriarPedido extends AsyncTask<PedidoRequest, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(PedidoRequest... params) {
			AbrirCriarPedido(params[0]);
			return null;
		}

		@Override
		protected void onPostExecute(Void ex) {
			super.onPostExecute(ex);
			TratarRetornoAbrirCriarPedido();
		}
	}
	
	/**
	 * Chama o serviço de criar pedido no onebuy
	 * @param objPedidoRequest
	 * @return
	 */
	private PedidoResponse AbrirCriarPedido(PedidoRequest objPedidoRequest)
	{
		try
		{	
			objPedidoResponse = new PedidoResponse();
			
			//Consulta os dados da Application
			OneBuyApplication objOneBuyApplication = OneBuyApplication.getInstance();
			objPedidoRequest.CodigoAutorizacao = objOneBuyApplication.getCodigoAutorizacao();
			
			objPedidoRequest.DadosIdentificacao = new DadosIdentificacaoRequest();
			objPedidoRequest.DadosIdentificacao.IdentificadorUsuario = objOneBuyApplication.getIdentificadorAparelho();
			objPedidoRequest.DadosIdentificacao.PlataformaOrigem = contexto.getString(R.string.PLATAFORMA_ORIGEM);
					
			//Cria o client do serviço
			final RestTemplate restTemplate = HttpManager.getRestTemplate(contexto);
			
			//Header de autenticação
			final HttpEntity<PedidoRequest> entity = new HttpEntity<PedidoRequest>(objPedidoRequest, oneBuy.Util.HttpManager.headersPadrao());		
			
			final String url = String.format(EnderecoServico.URL_CRIAR_PEDIDO);
			final ResponseEntity<PedidoResponse> retorno = restTemplate.exchange(url, HttpMethod.POST, entity, PedidoResponse.class);
			
			//Retorno
			objPedidoResponse = retorno.getBody();
			
			objOneBuyApplication.setCodigoTransacaoGateway(objPedidoResponse.CodigoTransacaoGateway);
			objOneBuyApplication.setValorPedido(objPedidoResponse.Valor);
			
		} catch (HttpStatusCodeException objHttpStatusCodeException) {
			objPedidoResponse.RetornoResponse = new RetornoResponse();
			objPedidoResponse.RetornoResponse.Mensagem.add(objHttpStatusCodeException.getMessage());
		} catch (ResourceAccessException objResourceAccessException) {
			objPedidoResponse.RetornoResponse = new RetornoResponse();
			objPedidoResponse.RetornoResponse.Mensagem.add(objResourceAccessException.getMessage());
		} catch (Exception objException) {
			objPedidoResponse.RetornoResponse = new RetornoResponse();
			objPedidoResponse.RetornoResponse.Mensagem.add(objException.getMessage());
		}
		
		return objPedidoResponse;
	}
	
	/**
	 * Tratar o retorno do pedido criado
	 */
	private void TratarRetornoAbrirCriarPedido()
	{
		if(objPedidoResponse.RetornoResponse.Sucesso && objPedidoResponse.CodigoAutorizacao != null)
		{
			//Abrir aplicação OneBuy.Android.Cliente e aprovar o PIN
			try {
				this.AbrirOneBuyParaConfirmarPIN();
			} catch (Exception e) {
				objPedidoResponse.RetornoResponse.Mensagem.add(e.getMessage());
			}	
		}
		
		if(getTaskCompleteCriarPedido() != null)
		{
			getTaskCompleteCriarPedido().onTaskListenerComplete.onTaskCompleted(objPedidoResponse);	
		}
	}
	
	/**
	 * Efetua a chamada para o AppCliente do OneBuy e obtem o token criptografado
	 * @return Token descriptografado pronto para usar
	 * @throws Exception 
	 */
	public void AbrirOneBuyParaConfirmarPIN() throws Exception {
		
		try
		{
			//Chamar oneBuy para o usuario confirmar o pedido
			Intent sendIntent = new Intent();
			sendIntent.setAction(contexto.getString(R.string.ONEBUY_FLUXOCROSSAPPAPROVARCOMPRA));
			sendIntent.putExtra(contexto.getString(R.string.ONEBUY_FLAG_FLUXOCROSSAPP), "1");		
			contexto.getApplicationContext().sendBroadcast(sendIntent);
		}
		catch(IllegalArgumentException e)
		{
			throw new Exception(e.getMessage()); 
		}
	}
	
	/**
	 * FIM
	 */
	
	/**
	 * Metodos auxiliares para capturar pedido
	 */
	
	class asyncTaskCapturarPedido extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {
			AbrirCapturarPedido();
			return null;
		}

		@Override
		protected void onPostExecute(Void ex) {
			super.onPostExecute(ex);
			TratarRetornoCapturarPedido();
		}

	}
	
	private CapturaPedidoResponse AbrirCapturarPedido()
	{
		try
		{	
			objCapturaPedidoResponse = new CapturaPedidoResponse();
			
			CapturaPedidoCrossAppRequest objCapturaPedidoRequest = new CapturaPedidoCrossAppRequest();
			//Consulta os dados da Application
			OneBuyApplication objOneBuyApplication = OneBuyApplication.getInstance();
			objCapturaPedidoRequest.CodigoTransacaoGateway = objOneBuyApplication.getCodigoTransacaoGateway();
			objCapturaPedidoRequest.CodigoAutorizacao = objOneBuyApplication.getCodigoAutorizacao();
			objCapturaPedidoRequest.ValorPedido = objOneBuyApplication.getValorPedido();
			objCapturaPedidoRequest.IdentificadorUsuarioTransacao = objOneBuyApplication.getIdentificadorUsuarioTransacao();
					
			//Cria o client do serviço
			final RestTemplate restTemplate = HttpManager.getRestTemplate(contexto);
			
			//Header de autenticação
			final HttpEntity<CapturaPedidoCrossAppRequest> entity = new HttpEntity<CapturaPedidoCrossAppRequest>(objCapturaPedidoRequest, oneBuy.Util.HttpManager.headersPadrao());		
			
			final String url = String.format(EnderecoServico.URL_CAPTURAR_PEDIDO);
			final ResponseEntity<CapturaPedidoResponse> retorno = restTemplate.exchange(url, HttpMethod.POST, entity, CapturaPedidoResponse.class);
			
			//Retorno
			objCapturaPedidoResponse = retorno.getBody();
			
			
		} catch (HttpStatusCodeException objHttpStatusCodeException) {
			objCapturaPedidoResponse.RetornoResponse = new RetornoResponse();
			objCapturaPedidoResponse.RetornoResponse.Mensagem.add(objHttpStatusCodeException.getMessage());
		} catch (ResourceAccessException objResourceAccessException) {
			objCapturaPedidoResponse.RetornoResponse = new RetornoResponse();
			objCapturaPedidoResponse.RetornoResponse.Mensagem.add(objResourceAccessException.getMessage());
		} catch (Exception objException) {
			objCapturaPedidoResponse.RetornoResponse = new RetornoResponse();
			objCapturaPedidoResponse.RetornoResponse.Mensagem.add(objException.getMessage());
		}
		
		return objCapturaPedidoResponse;
	}
	
	private void TratarRetornoCapturarPedido()
	{
		if(getTaskCompleteCapturarPedido() != null)
		{
			getTaskCompleteCapturarPedido().onTaskListenerComplete.onTaskCompleted(objCapturaPedidoResponse);	
		}
	}
	
	/**
	 * FIM
	 */
	
	/**
	 * Metodos auxiliares para capturar pedido
	 */
	
	class asyncTaskSondarPedido extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Void doInBackground(Void... params) {
			AbrirSondarPedido();
			return null;
		}

		@Override
		protected void onPostExecute(Void ex) {
			super.onPostExecute(ex);
			TratarRetornoSondarPedido();
		}

	}
	
	private SondaPedidoResponse AbrirSondarPedido()
	{
		try
		{
			objSondaPedidoResponse = new SondaPedidoResponse();
			
			SondaPedidoRequest objSondaPedidoRequest = new SondaPedidoRequest();
			//Consulta os dados da Application
			OneBuyApplication objOneBuyApplication = OneBuyApplication.getInstance();
			objSondaPedidoRequest.CodigoTransacaoGateway = objOneBuyApplication.getCodigoTransacaoGateway();
			objSondaPedidoRequest.CodigoAutorizacao = objOneBuyApplication.getCodigoAutorizacao();
			
					
			//Cria o client do serviço
			final RestTemplate restTemplate = HttpManager.getRestTemplate(contexto);
			
			//Header de autenticação
			final HttpEntity<SondaPedidoRequest> entity = new HttpEntity<SondaPedidoRequest>(objSondaPedidoRequest, oneBuy.Util.HttpManager.headersPadrao());		
			
			final String url = String.format(EnderecoServico.URL_SONDAR_PEDIDO);
			final ResponseEntity<SondaPedidoResponse> retorno = restTemplate.exchange(url, HttpMethod.POST, entity, SondaPedidoResponse.class);
			
			//Retorno
			objSondaPedidoResponse = retorno.getBody();
			
			
		} catch (HttpStatusCodeException objHttpStatusCodeException) {
			objSondaPedidoResponse.RetornoResponse = new RetornoResponse();
			objSondaPedidoResponse.RetornoResponse.Mensagem.add(objHttpStatusCodeException.getMessage());
		} catch (ResourceAccessException objResourceAccessException) {
			objSondaPedidoResponse.RetornoResponse = new RetornoResponse();
			objSondaPedidoResponse.RetornoResponse.Mensagem.add(objResourceAccessException.getMessage());
		} catch (Exception objException) {
			objSondaPedidoResponse.RetornoResponse = new RetornoResponse();
			objSondaPedidoResponse.RetornoResponse.Mensagem.add(objException.getMessage());
		}
		
		return objSondaPedidoResponse;
	}
	
	private void TratarRetornoSondarPedido()
	{
		if(getTaskCompleteSondaPedido() != null)
		{
			getTaskCompleteSondaPedido().onTaskListenerComplete.onTaskCompleted(objSondaPedidoResponse);	
		}
	}
	
	/**
	 * FIM
	 */
}
