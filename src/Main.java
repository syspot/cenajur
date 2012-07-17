import br.com.topsys.util.TSCryptoUtil;
import br.com.topsys.util.TSUtil;


public class Main {

	public static void main(String args[]){

		String valor = "nayara";
		if (!TSUtil.isEmpty(valor)) {
	       System.out.println(TSCryptoUtil.gerarHash(valor, "md5"));
	    }

	}
	
}
