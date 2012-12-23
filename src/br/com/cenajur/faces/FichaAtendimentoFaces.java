package br.com.cenajur.faces;

import java.util.HashMap;
import java.util.Map;

import javax.faces.bean.ManagedBean;

import br.com.cenajur.util.CenajurUtil;
import br.com.topsys.web.util.TSFacesUtil;

@ManagedBean(name = "fichaAtendimentoFaces")
public class FichaAtendimentoFaces{

	public String gerarRelatorio() {

        try {

            Map<String, Object> parametros = new HashMap<String, Object>();

            parametros.put("SUBREPORT_DIR", TSFacesUtil.getServletContext().getRealPath("WEB-INF/relatorios/"));

//            new JasperUtil().gerarRelatorio(nomeRelatorio, nomeRelatorioImpressao.toString(), parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }

        return "sucesso";

    }

}
