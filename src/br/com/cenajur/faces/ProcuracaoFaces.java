package br.com.cenajur.faces;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.com.cenajur.model.Cliente;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.Configuracao;
import br.com.cenajur.relat.JasperUtil;
import br.com.cenajur.util.CenajurUtil;
import br.com.cenajur.util.Constantes;
import br.com.topsys.util.TSUtil;
import br.com.topsys.web.faces.TSMainFaces;

@ViewScoped
@ManagedBean(name = "procuracaoFaces")
public class ProcuracaoFaces extends TSMainFaces{

	private Cliente clienteSelecionado;
	private Colaborador advogadoSelecionado;
	private Boolean flagColetivo;
	
	@PostConstruct
	public void init(){
		this.clienteSelecionado = new Cliente();
		this.advogadoSelecionado = new Colaborador();
	}
	
	public void selecionarTodos(){
		this.advogadoSelecionado = new Colaborador();
	}
	
	public String gerarRelatorio() {

		if(TSUtil.isEmpty(clienteSelecionado) || TSUtil.isEmpty(clienteSelecionado.getId())){
			super.addErrorMessage("Associado: Campo obrigatório");
			return null;
		}
		
		if((TSUtil.isEmpty(advogadoSelecionado) || TSUtil.isEmpty(advogadoSelecionado.getId())) && !flagColetivo){
			super.addErrorMessage("Selecione o Advogado ou marque a opção 'Procuração Coletiva'");
			return null;
		}
		
		if(!flagColetivo){
			this.advogadoSelecionado = this.advogadoSelecionado.getById();
		}
			
		this.clienteSelecionado = this.clienteSelecionado.getById();
        	
		try {

        	StringBuilder outorgante = new StringBuilder("<strong>OUTORGANTE:</strong> ");
        	
        	outorgante.append("<strong><i>").append(this.clienteSelecionado.getNome()).append("</i></strong>");
        	
        	if(!TSUtil.isEmpty(this.clienteSelecionado.getRg())){
        		outorgante.append(" <strong>RG:</strong> ").append(this.clienteSelecionado.getRg());
        	}
        	
        	if(!TSUtil.isEmpty(this.clienteSelecionado.getCpf())){
        		outorgante.append(" <strong>CPF:</strong> ").append(this.clienteSelecionado.getCpf());
        	}
        	
    		outorgante.append(TSUtil.isEmpty(clienteSelecionado.getLogradouro()) ? "" : " <strong>ENDEREÇO:</strong> " + clienteSelecionado.getLogradouro() + ", ");
        	outorgante.append(TSUtil.isEmpty(clienteSelecionado.getNumero()) ? "" : clienteSelecionado.getNumero() + ", ");
        	outorgante.append(TSUtil.isEmpty(clienteSelecionado.getComplemento()) ? "" : clienteSelecionado.getComplemento() + ", ");
        	outorgante.append(TSUtil.isEmpty(clienteSelecionado.getBairro()) ? "" : clienteSelecionado.getBairro() + ", ");
        	outorgante.append(TSUtil.isEmpty(clienteSelecionado.getCidade()) || TSUtil.isEmpty(clienteSelecionado.getCidade().getId()) ? "" : clienteSelecionado.getCidade().getNomeCompleto());
        	outorgante.append(TSUtil.isEmpty(clienteSelecionado.getCep()) ? "" : clienteSelecionado.getCep());
        	
        	if(!TSUtil.isEmpty(this.clienteSelecionado.getTelefone())){
        		outorgante.append(" <strong>TEL:</strong> ").append(this.clienteSelecionado.getTelefone());
        	}
        	
        	StringBuilder outorgados = new StringBuilder("<strong>OUTORGADOS:</strong> ");
        	
        	if(flagColetivo){
        		
        		List<Colaborador> advogados = advogadoSelecionado.findAllAdvogados();
        		
        		for(Colaborador advogado : advogados){
        			
        			outorgados.append("<strong>").append(advogado.getNome()).append("</strong>").append(!TSUtil.isEmpty(advogado.getOab()) ? " (OAB/BA n. " + advogado.getOab() + "), " : " (RG " + advogado.getRg() + "), ");
        			
        		}
        		
        		outorgados.delete(outorgados.length() - 2, outorgados.length() - 1);
        		
        	} else{
        		
        		outorgados.append(advogadoSelecionado.getNome()).append(!TSUtil.isEmpty(advogadoSelecionado.getOab()) ? " (OAB/BA n. " + advogadoSelecionado.getOab() + ") " : " (RG " + advogadoSelecionado.getRg() + ") ");
        		
        	}
        	
        	outorgados.append("todos com escritório profissional na " + new Configuracao(Constantes.CONFIGURACAO_ENDERECO_CENAJUR).getById().getValor() + ", nesta Capital.");
        	
        	String texto = outorgante.toString() + "<br/><br/>" + outorgados.toString() + "<br/><br/>" + "Pelo presente instrumento particular de mandato e na melhor  forma de direito, o outorgante acima qualificado, nomeia e constitui seu procurador o outorgado supramencionado com o fim de representá-lo junto aos Órgãos Federais, Estaduais e Municipais, Autarquias e Fundações, Juízos Comuns e Especiais, Instituições Financeiras e seguradoras em geral, onde figure como autor ou réu, assistente ou opoente, podendo desistir, transigir, fazer acordo, assumir compromissos, receber, passar recibos e dar quitação, exercer a adjudicação e assinar o auto e carta respectiva, substabelecer com ou sem reservas e praticar os atos necessários ao bom desempenho deste mandato, por mais especiais que sejam, além dos poderes citados na cláusula <em><strong>Ad Judicia</strong></em>.";
        	
            Map<String, Object> parametros = CenajurUtil.getHashMapReport();

            parametros.put("P_TEXTO", texto);

            new JasperUtil().gerarRelatorio("procuracao.jasper", "procuracao", parametros);

        } catch (Exception ex) {

            CenajurUtil.addErrorMessage("Não foi possível gerar relatório.");

            ex.printStackTrace();

        }

        return null;

    }
	
	public String addCliente(){
		return null;
	}
	
	public String addAdvogado(){
		return null;
	}

	public Cliente getClienteSelecionado() {
		return clienteSelecionado;
	}

	public void setClienteSelecionado(Cliente clienteSelecionado) {
		this.clienteSelecionado = clienteSelecionado;
	}

	public Colaborador getAdvogadoSelecionado() {
		return advogadoSelecionado;
	}

	public void setAdvogadoSelecionado(Colaborador advogadoSelecionado) {
		this.advogadoSelecionado = advogadoSelecionado;
	}

	public Boolean getFlagColetivo() {
		return flagColetivo;
	}

	public void setFlagColetivo(Boolean flagColetivo) {
		this.flagColetivo = flagColetivo;
	}

	
}
