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

            CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }

        return "sucesso";

    }

}
