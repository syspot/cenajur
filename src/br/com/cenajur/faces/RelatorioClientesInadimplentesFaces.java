package br.com.cenajur.faces;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;

@RequestScoped
@ManagedBean(name = "relatorioClientesInadimplentesFaces")
public class RelatorioClientesInadimplentesFaces{

	@PostConstruct
	public void init() {

        try {

            Map<String, Object> parametros = new HashMap<String, Object>();

            new JasperUtil().gerarRelatorioHtml("relatClientesInadimplentes.jasper", "relatorio_clientes_inadimplentes".toString(), parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }

    }

}
