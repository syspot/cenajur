package br.com.cenajur.faces;

import javax.faces.bean.ManagedBean;

import br.com.cenajur.util.CenajurUtil;

@ManagedBean(name = "fichaAtendimentoFaces")
public class FichaAtendimentoFaces{

	public String gerarRelatorio() {

        try {

            //Map<String, Object> parametros = CenajurUtil.getHashMapReport();

//            new JasperUtil().gerarRelatorio(nomeRelatorio, nomeRelatorioImpressao.toString(), parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("N�o foi poss�vel gerar relat�rio.");

            ex.printStackTrace();

        }

        return "sucesso";

    }

}
