package oneBuy.Util;

public class NeoITDetalheException {
    //*****Region Enum

    public enum TipoMensagem
    {
        Sucesso,
        Erro
    }

    
    //*****Region Construtores

    public NeoITDetalheException()
    {

    }

    public NeoITDetalheException(String Erro)
    {
        this.setMensagemErro(Erro);
    }

    public NeoITDetalheException(String Erro, TipoMensagem tipoMensagem)
    {
        this.setMensagemErro(Erro);
        this.setTipoMensagem(tipoMensagem);
    }

    public NeoITDetalheException(String Erro, String Propriedade)
    {
        this.setMensagemErro(Erro);
        this.setPropriedade(Propriedade);
    }

    public NeoITDetalheException(String Erro, String Propriedade, TipoMensagem tipoMensagem)
    {
        this.setMensagemErro(Erro);
        this.setPropriedade(Propriedade);
        this.setTipoMensagem(tipoMensagem);
    }
    
    public int ResourceID()
    {	
    	int resultado = 0;
 		try 
 		{
			return Integer.parseInt(this.propriedade);
		}
 		catch (NumberFormatException e) 
 		{
			return resultado;
		}
    }


    //*****Region Propriedades
    
    private String mensagemErro;
    public String getMensagemErro() {
		return mensagemErro;
	}
	public void setMensagemErro(String mensagemErro) {
		this.mensagemErro = mensagemErro;
	}

	private String propriedade;
	public String getPropriedade() {
		return propriedade;
	}
	public void setPropriedade(String propriedade) {
		this.propriedade = propriedade;
	}
	
	private TipoMensagem tipoMensagem;
	public TipoMensagem getTipoMensagem() {
		return tipoMensagem;
	}
	public void setTipoMensagem(TipoMensagem tipoMensagem) {
		this.tipoMensagem = tipoMensagem;
	}
}
