package oneBuy.Util;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.kobjects.base64.Base64;

import oneBuy.Response.DadosUsuario;
import oneBuy.Response.Transacao;

public class Criptografia {
	//  private static Cipher mCipher;
	private static final int VETOR_CRIPTOGRAFIA_QUANTIDADE = 16;
	
	/**
	 * Descriptografa todos os dados do usuário retornados pelo checkout
	 * 
	 * @param objDadosUsuarioCheckout
	 * @param chaveCriptografia
	 * @return
	 * @throws InvalidKeyException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws ShortBufferException
	 * @throws NoSuchProviderException
	 * @throws UnsupportedEncodingException
	 * @throws NeoITException
	 */
	public DadosUsuario DescriptografarDadosUsuarioCheckout(
			DadosUsuario objDadosUsuarioCheckout, String chaveCriptografia)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			ShortBufferException, NoSuchProviderException,
			UnsupportedEncodingException, NeoITException {

		DadosUsuario objDadosUsuario = new DadosUsuario();

		objDadosUsuario.Apelido = DescriptografarComChave(
				objDadosUsuarioCheckout.Apelido, chaveCriptografia);
		objDadosUsuario.Celular = DescriptografarComChave(
				objDadosUsuarioCheckout.Celular, chaveCriptografia);
		objDadosUsuario.CPF = DescriptografarComChave(
				objDadosUsuarioCheckout.CPF, chaveCriptografia);
		objDadosUsuario.CPFCodificado = DescriptografarComChave(
				objDadosUsuarioCheckout.CPFCodificado, chaveCriptografia);
		objDadosUsuario.DataNascimento = DescriptografarComChave(
				objDadosUsuarioCheckout.DataNascimento, chaveCriptografia);
		objDadosUsuario.Email = DescriptografarComChave(
				objDadosUsuarioCheckout.Email, chaveCriptografia);
		objDadosUsuario.EmailCodificado = DescriptografarComChave(
				objDadosUsuarioCheckout.EmailCodificado, chaveCriptografia);
		objDadosUsuario.IdentificadorUsuarioCodificado = DescriptografarComChave(
				objDadosUsuarioCheckout.IdentificadorUsuarioCodificado,
				chaveCriptografia);
		objDadosUsuario.Idioma = DescriptografarComChave(
				objDadosUsuarioCheckout.Idioma, chaveCriptografia);
		objDadosUsuario.NomeCompleto = DescriptografarComChave(
				objDadosUsuarioCheckout.NomeCompleto, chaveCriptografia);
		objDadosUsuario.RG = DescriptografarComChave(
				objDadosUsuarioCheckout.RG, chaveCriptografia);
		objDadosUsuario.RGOrgaoEmissor = DescriptografarComChave(
				objDadosUsuarioCheckout.RGOrgaoEmissor, chaveCriptografia);
		objDadosUsuario.SenhaUsuarioCodificado = DescriptografarComChave(
				objDadosUsuarioCheckout.SenhaUsuarioCodificado,
				chaveCriptografia);
		objDadosUsuario.Sexo = DescriptografarComChave(
				objDadosUsuarioCheckout.Sexo, chaveCriptografia);
		objDadosUsuario.Telefone = DescriptografarComChave(
				objDadosUsuarioCheckout.Telefone, chaveCriptografia);

		//Esta campo não vem criptografado
		objDadosUsuario.IdTransacao = objDadosUsuarioCheckout.IdTransacao;
				
		//Verifica se tem cartão
		if(objDadosUsuarioCheckout.Cartao != null)
		{
			objDadosUsuario.Cartao = new oneBuy.Response.Cartao();
			objDadosUsuario.Cartao.Bandeira = DescriptografarComChave(
					objDadosUsuarioCheckout.Cartao.Bandeira, chaveCriptografia);
			objDadosUsuario.Cartao.NumeroCartaoMascarado = DescriptografarComChave(
					objDadosUsuarioCheckout.Cartao.NumeroCartaoMascarado,
					chaveCriptografia);
		}
		
		//Verifica se tem Endereço de cobrança
		if(objDadosUsuarioCheckout.EnderecoCobranca != null)
		{
			objDadosUsuario.EnderecoCobranca = new oneBuy.Response.EnderecoCobranca();
			objDadosUsuario.EnderecoCobranca.Bairro = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.Bairro,
					chaveCriptografia);
			objDadosUsuario.EnderecoCobranca.CEP = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.CEP, chaveCriptografia);
			objDadosUsuario.EnderecoCobranca.Cidade = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.Cidade,
					chaveCriptografia);
			objDadosUsuario.EnderecoCobranca.Complemento = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.Complemento,
					chaveCriptografia);
			objDadosUsuario.EnderecoCobranca.Estado = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.Estado,
					chaveCriptografia);
			objDadosUsuario.EnderecoCobranca.Logradouro = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.Logradouro,
					chaveCriptografia);
			objDadosUsuario.EnderecoCobranca.Numero = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.Numero,
					chaveCriptografia);
			objDadosUsuario.EnderecoCobranca.Pais = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.Pais,
					chaveCriptografia);
			objDadosUsuario.EnderecoCobranca.Telefone = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.Telefone,
					chaveCriptografia);
			objDadosUsuario.EnderecoCobranca.UF = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoCobranca.UF, chaveCriptografia);	
		}
		
		//Verifica se tem endereço de entrega
		if(objDadosUsuarioCheckout.EnderecoEntrega != null)
		{
			objDadosUsuario.EnderecoEntrega = new oneBuy.Response.EnderecoEntrega();
			objDadosUsuario.EnderecoEntrega.Bairro = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.Bairro,
					chaveCriptografia);
			objDadosUsuario.EnderecoEntrega.CEP = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.CEP, chaveCriptografia);
			objDadosUsuario.EnderecoEntrega.Cidade = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.Cidade,
					chaveCriptografia);
			objDadosUsuario.EnderecoEntrega.Complemento = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.Complemento,
					chaveCriptografia);
			objDadosUsuario.EnderecoEntrega.Estado = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.Estado,
					chaveCriptografia);
			objDadosUsuario.EnderecoEntrega.Logradouro = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.Logradouro,
					chaveCriptografia);
			objDadosUsuario.EnderecoEntrega.Numero = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.Numero,
					chaveCriptografia);
			objDadosUsuario.EnderecoEntrega.Pais = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.Pais, chaveCriptografia);
			objDadosUsuario.EnderecoEntrega.Telefone = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.Telefone,
					chaveCriptografia);
			objDadosUsuario.EnderecoEntrega.UF = DescriptografarComChave(
					objDadosUsuarioCheckout.EnderecoEntrega.UF, chaveCriptografia);
		}
		
		//Verifica se tem programa de milhagem
		if(objDadosUsuarioCheckout.Transacao != null)
		{
			objDadosUsuario.Transacao = new Transacao();
		
			objDadosUsuario.Transacao.DataFinal = DescriptografarComChave(
					objDadosUsuarioCheckout.Transacao.DataFinal, chaveCriptografia);
			objDadosUsuario.Transacao.DataInicial = DescriptografarComChave(
					objDadosUsuarioCheckout.Transacao.DataInicial, chaveCriptografia);
			objDadosUsuario.Transacao.NumeroTransacao = DescriptografarComChave(
					objDadosUsuarioCheckout.Transacao.NumeroTransacao, chaveCriptografia);
			
		}
				
		//Verifica se tem programa de milhagem
		if(objDadosUsuarioCheckout.ProgramasMilhagem != null)
		{
			objDadosUsuario.ProgramasMilhagem = new ArrayList<oneBuy.Response.ProgramaMilhagem>();
			for (oneBuy.Response.ProgramaMilhagem objProgramasMilhagem : objDadosUsuarioCheckout.ProgramasMilhagem) {

				oneBuy.Response.ProgramaMilhagem objDescript = new oneBuy.Response.ProgramaMilhagem();
				objDescript.CodigoEmpresa = DescriptografarComChave(
						objProgramasMilhagem.CodigoEmpresa, chaveCriptografia);
				objDescript.CodigoMilhagem = DescriptografarComChave(
						objProgramasMilhagem.CodigoMilhagem, chaveCriptografia);
				objDescript.Empresa = DescriptografarComChave(
						objProgramasMilhagem.Empresa, chaveCriptografia);
				objDadosUsuario.ProgramasMilhagem.add(objDescript);
			}
		}
		

		return objDadosUsuario;
	}

	public static String CriptografiaComChave(String dados,
			String chaveCriptografia) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, ShortBufferException, NoSuchProviderException,
			NeoITException, UnsupportedEncodingException {
		chaveCriptografia = chaveCriptografia.substring(0, VETOR_CRIPTOGRAFIA_QUANTIDADE);
		return CriptografiaComChave(dados, chaveCriptografia, true);
	}

	public static String DescriptografarComChave(String dados,
			String chaveCriptografia) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, ShortBufferException, NoSuchProviderException,
			NeoITException, UnsupportedEncodingException {
		chaveCriptografia = chaveCriptografia.substring(0, VETOR_CRIPTOGRAFIA_QUANTIDADE);
		return CriptografiaComChave(dados, chaveCriptografia, false);
	}

	/**
	 * Metodo que irá criptografar/descriptografar utilizando com base a chave e
	 * o vetor passados como parametro
	 * 
	 * @param dados
	 *            que será criptografado/descriptografado
	 * @param chave
	 *            que será utilizada para criptografado/descriptografado
	 * @param vetor
	 *            que será utilizada para criptografado/descriptografado
	 * @param criptografar
	 *            true crip false decripty
	 * @return Retorna o valor passado criptografado/descriptogrado
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws ShortBufferException
	 * @throws UnsupportedEncodingException
	 */
	private static String CriptografiaComChave(String dados, String chave, boolean criptografar)
			throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, InvalidAlgorithmParameterException,
			IllegalBlockSizeException, BadPaddingException,
			ShortBufferException, UnsupportedEncodingException {
		
		String retorno = "";
		Cipher mCipher = null;
		
		if (dados != null && dados.length() > 0 
				&& chave != null && chave.length() > 0)
		{
			final byte[] chaveDecode = chave.getBytes("UTF-8");
			final Cipher cipher;

			if (mCipher == null) {
				mCipher = Cipher.getInstance("AES/ECB/NoPadding");
			}
			// depois de checar o null fazer a referencia local
			cipher = mCipher;

			if (dados != null && dados.length() > 0 && chave != null
					&& chave.length() > 0) {
				final int criptografia;

				if (criptografar) {
					criptografia = Cipher.ENCRYPT_MODE;
					final byte[] dadosCriptografar = dados.getBytes("UTF-8");

					cipher.init(criptografia, new SecretKeySpec(chaveDecode, "AES"));
					final byte[] ciphertext = cipher.doFinal(dadosCriptografar);

					retorno = Base64.encode(ciphertext);
				} else {
					criptografia = Cipher.DECRYPT_MODE;
					final byte[] dadosCriptografar = hexStringToByteArray(dados);

					cipher.init(criptografia, new SecretKeySpec(chaveDecode, "AES"));
					final byte[] resultadoCripto = cipher.doFinal(dadosCriptografar);

					retorno = new String(resultadoCripto,"UTF-8").trim();
				}
			}
		}
		else
		{
			//Não faz nada!
		}
		

		return retorno.trim();
	}

	public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len/2];

        for(int i = 0; i < len; i+=2){
            data[i/2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i+1), 16));
        }

        return data;
    }

	public static String CriptografiaComChaveEvetor(String dados,
			String chaveCriptografia) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, ShortBufferException, NoSuchProviderException,
			NeoITException, UnsupportedEncodingException {
		chaveCriptografia = chaveCriptografia.substring(0, VETOR_CRIPTOGRAFIA_QUANTIDADE);
		String vetorCriptografia = Util.gerarHashMD5(chaveCriptografia).substring(0, VETOR_CRIPTOGRAFIA_QUANTIDADE);
		return CriptografiaComChaveEvetor(dados, chaveCriptografia,vetorCriptografia, true);
	}
	
	public static String DescriptografarComChaveEvetor(String dados,
			String chaveCriptografia) throws InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException, ShortBufferException, NoSuchProviderException,
			NeoITException, UnsupportedEncodingException {
		chaveCriptografia = chaveCriptografia.substring(0, VETOR_CRIPTOGRAFIA_QUANTIDADE);
		String vetorCriptografia = Util.gerarHashMD5(chaveCriptografia).substring(0, VETOR_CRIPTOGRAFIA_QUANTIDADE);
		return CriptografiaComChaveEvetor(dados, chaveCriptografia,vetorCriptografia, false);
	}
	
	/**
	 * Metodo que irá criptografar/descriptografar utilizando com base a chave e o vetor passados como parametro
	 * @param dados que será criptografado/descriptografado
	 * @param chave que será utilizada para criptografado/descriptografado
	 * @param vetor que será utilizada para criptografado/descriptografado
	 * @param criptografar true crip false decripty
	 * @return Retorna o valor passado criptografado/descriptogrado
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws InvalidAlgorithmParameterException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws ShortBufferException
	 * @throws UnsupportedEncodingException 
	 */
	private static String CriptografiaComChaveEvetor(String dados, String chave, String vetor, boolean criptografar) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ShortBufferException, UnsupportedEncodingException
	{			
		final byte[] chaveDecode = chave.getBytes("UTF-8");
		final byte[] vetorDecode = vetor.getBytes("UTF-8");
		String retorno = null;		
		
		final Cipher cipher;
		Cipher mCipher = null;
		
		if (mCipher == null) {
			mCipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
		}
		// depois de checar o null fazer a referencia local
		cipher = mCipher;
		
		if (dados != null && dados.length() > 0 
				&& chave != null && chave.length() > 0
				&& vetor != null && vetor.length() > 0 
				)
		{	
			final int criptografia;
			
			if (criptografar) 
			{
				criptografia = Cipher.ENCRYPT_MODE;
				final byte[] dadosCriptografar = dados.getBytes("UTF-8");
				
				cipher.init(criptografia, new SecretKeySpec(chaveDecode, "AES"), new IvParameterSpec(vetorDecode));
				final byte[] ciphertext = cipher.doFinal(dadosCriptografar);
				
				retorno = Base64.encode(ciphertext);
			}
			else 
			{
				criptografia = Cipher.DECRYPT_MODE;
				final byte[] dadosCriptografar = Base64.decode(dados);
				
				cipher.init(criptografia, new SecretKeySpec(chaveDecode, "AES"), new IvParameterSpec(vetorDecode));
				final byte[] resultadoCripto = cipher.doFinal(dadosCriptografar);
				
				retorno = new String(resultadoCripto);			
			}
		}
		
		return retorno;
	}

	/**
     * Responsavel por Criptografar os dados de Login utilizando codigoAutorizacao
     * @param dados
     * @param chave
     * @param vetor
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws ShortBufferException
     * @throws NeoITException 
     * @throws NoSuchProviderException 
     * @throws UnsupportedEncodingException 
     */
    public static String CriptografarSimples(String dados,String chave) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ShortBufferException, NoSuchProviderException, NeoITException, UnsupportedEncodingException
    {
    	String codigoAutorizacaoSHA512 = Util.gerarHashSha512(chave);
    	String chaveCriptografiaMD5 = Util.gerarHashMD5(codigoAutorizacaoSHA512);
    	String vetorCriptografia = chaveCriptografiaMD5.substring(0, VETOR_CRIPTOGRAFIA_QUANTIDADE);
    	
    	return CriptografiaComChaveEvetor(dados, chaveCriptografiaMD5, vetorCriptografia, true);
    }
    
    /**
     * Responsavel por Descriptografar os dados de Login utilizando codigoAutorizacao
     * @param dados
     * @param chave
     * @param vetor
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidAlgorithmParameterException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @throws ShortBufferException
     * @throws NeoITException 
     * @throws NoSuchProviderException 
     * @throws UnsupportedEncodingException 
     */
    public static String DescriptografarSimples(String dados,String chave) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException, ShortBufferException, NoSuchProviderException, NeoITException, UnsupportedEncodingException
    {
    	String codigoAutorizacaoSHA512 = Util.gerarHashSha512(chave);
    	String chaveCriptografiaMD5 = Util.gerarHashMD5(codigoAutorizacaoSHA512);
    	String vetorCriptografia = chaveCriptografiaMD5.substring(0, VETOR_CRIPTOGRAFIA_QUANTIDADE);
    	
    	return CriptografiaComChaveEvetor(dados, chaveCriptografiaMD5, vetorCriptografia, false);
    }

}
