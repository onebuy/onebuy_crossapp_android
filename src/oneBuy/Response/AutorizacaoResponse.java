package oneBuy.Response;

public class AutorizacaoResponse {
	public String CodigoAutorizacao;
	public RetornoResponse RetornoResponse;

	public String getCodigoAutorizacao() {
		return CodigoAutorizacao;
	}

	public void setSucesso(String codigoAutorizacao) {
		CodigoAutorizacao = codigoAutorizacao;
	}
	
	public RetornoResponse getRetornoResponse() {
		return RetornoResponse;
	}

	public void setRetornoResponse(RetornoResponse retornoResponse) {
		RetornoResponse = retornoResponse;
	}
	
	
}
