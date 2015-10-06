package oneBuy.Response;

import java.util.ArrayList;

public class RetornoResponse {
	public boolean Sucesso;
	public ArrayList<String> Mensagem;

	public RetornoResponse() {
		Sucesso = false;
		Mensagem = new ArrayList<String>();
	}

	public boolean getSucesso() {
		return Sucesso;
	}

	public void setSucesso(boolean sucesso) {
		Sucesso = sucesso;
	}
	
	public ArrayList<String> getMensagem() {
		return Mensagem;
	}

	public void setMensagem(ArrayList<String> mensagem) {
		Mensagem = mensagem;
	}
}
