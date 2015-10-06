package oneBuy.Request;

import java.util.ArrayList;

public class DadosPedidoRequest {

	public String NumeroPedidoLoja;

	public String FormaPagamento;

	public int NumeroParcelas;

	public String NumeroCheckout;

	public Double Valor;

	public int ValorFrete;

	public ArrayList<DadosPedidoItemRequest> PedidoItens;
}
