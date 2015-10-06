package oneBuy.Util;

import java.util.LinkedList;
import java.util.List;

public class NeoITException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1895563656019824783L;
	private List<NeoITDetalheException> vetErros;
	
	private void setVetErros(List<NeoITDetalheException> vetErros) 
	{
		this.vetErros = vetErros;
	}

	private List<NeoITDetalheException> getVetErros() 
	{
		if (vetErros == null)
		{
			vetErros = new LinkedList<NeoITDetalheException>();
		}
		
		return vetErros;
	}
	
	public String getMessage()
    {
        return this.getDescricaoErros();
    }

    public int getQtdeErros()
    {
        return this.getVetErros().size();
    }

    public Boolean ContemErros()
    {
        Boolean contemErro = false;
        if (this.getVetErros().size() > 0)
        {
            contemErro = true;
        }
        return contemErro;
    }

    public String getDescricaoErros()
    {
        String retorno = "";

        for (int i = 0; i < this.getVetErros().size(); i++)
        {
        	if (i > 0)
        	{
        		retorno += "\n";
        	}
            retorno += this.getVetErros().get(i).getMensagemErro();
        }
        return retorno;
    }

    public List<NeoITDetalheException> getErros()
    {
        return this.getVetErros();
    }

    public NeoITException()
    {
    	super();
    	 setVetErros(new LinkedList<NeoITDetalheException>());
    }

    public NeoITException(String mensagemErro)
    {
    	super();
        this.AdicionarErro(new NeoITDetalheException(mensagemErro));
    }
    
    public NeoITException(NeoITDetalheException erro)
    {
    	super();
        this.AdicionarErro(erro);
    }


    public void AdicionarErro(NeoITDetalheException erro)
    {
        this.getVetErros().add(erro);
    }

    public void AdicionarErro(List<NeoITDetalheException> lstErro)
    {
        this.getVetErros().addAll(lstErro);
    }

    public void AdicionarErro(String Mensagem)
    {
        this.getVetErros().add(new NeoITDetalheException(Mensagem));
    }

    public void AdicionarErro(String Mensagem, String Propriedade)
    {
        this.getVetErros().add(new NeoITDetalheException(Mensagem, Propriedade));
    }

    public String ToString()
    {
        return this.getMessage();
    }
}
