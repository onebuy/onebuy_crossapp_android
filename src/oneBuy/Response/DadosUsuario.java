package oneBuy.Response;

import java.io.Serializable;
import java.util.ArrayList;

public class DadosUsuario implements Serializable{
	private static final long serialVersionUID = 1L;
	public String NomeCompleto;
    public String Apelido;
    public String Idioma;
    public String CPF;
    public String RG;
    public String Sexo;
    public String Telefone;
    public String Celular;
    public String Email;
    public String DataNascimento;
    public Transacao Transacao;
    public EnderecoCobranca EnderecoCobranca;
    public EnderecoEntrega EnderecoEntrega;
    public String IdTransacao;
    public String IdentificadorUsuarioCodificado;
    public String SenhaUsuarioCodificado;
    public String EmailCodificado;
    public String CPFCodificado;
    public String RGOrgaoEmissor;
    public ArrayList<ProgramaMilhagem> ProgramasMilhagem;
    public Cartao Cartao;
}
