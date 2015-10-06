package oneBuy.Util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import oneBuy.CrossAppApi.Android.R;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;

public class Util {
	
	
	/**
	 * Monta um Indetificador com base em informações do Aparelho
	 * OBS: Caso ocorra algum tipo de erro retorna null
	 * @return
	 */
	public String getIdentificadorAparelho() {
	  String manufacturer = Build.MANUFACTURER; //TCT
	  String model = Build.MODEL; //ALCATEL ONE TOUCH 5037E
	  String display = Build.DISPLAY; //Jelly Bean
	   
	  String retorno = null;
	  
	  try {
		  retorno = gerarHashMD5(String.format("%s %s %s", manufacturer, model, display));
		} catch (NoSuchAlgorithmException e) {
			retorno = null;
		} catch (NoSuchProviderException e) {
			retorno = null;
		} catch (UnsupportedEncodingException e) {
			retorno = null;
		}
		   
		return retorno;
	}
	
	public static String gerarHashMD5(String chave) throws NoSuchAlgorithmException,NoSuchProviderException, UnsupportedEncodingException
	{
		String resultHash = null;
	    MessageDigest md5 = MessageDigest.getInstance("MD5");

        byte[] result = new byte[md5.getDigestLength()];
        md5.reset();
        md5.update(chave.getBytes());
        result = md5.digest();

        StringBuffer buf = new StringBuffer(result.length * 2);

        for (int i = 0; i < result.length; i++) {
            int intVal = result[i] & 0xff;
            if (intVal < 0x10) {
                buf.append("0");
            }
            buf.append(Integer.toHexString(intVal));
        }

        resultHash = buf.toString();
	    return resultHash;
	}

	public static String gerarHashSha512(String chave) throws NoSuchAlgorithmException,NoSuchProviderException, UnsupportedEncodingException
	{
		
		MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512"); 
	    digest.update(chave.getBytes("UTF-16LE"));
	    byte messageDigest[] = digest.digest();

	    // Create Hex String
	    StringBuffer hexString = new StringBuffer();
	    for (int i = 0; i < messageDigest.length; i++) {
	        String h = Integer.toHexString(0xFF & messageDigest[i]);
	        while (h.length() < 2)
	            h = "0" + h;
	        hexString.append(h);
	    }
	    return hexString.toString();
	    
	}
	
	/**
	 * Verifica se o onebuy esta instalado para que poss ser chamado
	 * @param targetPackage
	 * @return
	 */
	@SuppressWarnings("unused")
	public static boolean isOneBuyInstalado(Context contexto){
		
		   PackageManager pm = contexto.getPackageManager();
		   
		   try {
			   
		     PackageInfo info = pm.getPackageInfo(contexto.getString(R.string.ONEBUY_PACKAGE),PackageManager.GET_META_DATA);
		     
		   } catch (PackageManager.NameNotFoundException e) {
		     return false;
		   }  
		   return true;
	}
	
	public static void abrirMarket(Context contexto)
    {
    	final String endereco = String.format("market://details?id=%s", contexto.getString(R.string.ONEBUY_PACKAGE));
    	
    	final Intent intent = new Intent(Intent.ACTION_VIEW);
    	intent.setData(Uri.parse(endereco));
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	contexto.startActivity(intent);
    }
	
	public static boolean isOnline(Context ctx) {
		ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		
		final boolean retorno;
		
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {		
			retorno = true;
		} else {
			retorno = false;
		}
		cm = null;
		netInfo = null;		
		return retorno;
	}
}
