package br.com.cenajur.util;

import br.com.cenajur.model.Audiencia;
import br.com.cenajur.model.AudienciaAdvogado;
import br.com.cenajur.model.Colaborador;
import br.com.cenajur.model.ContadorSms;
import br.com.cenajur.model.Processo;
import br.com.cenajur.model.ProcessoCliente;
import br.com.cenajur.model.ProcessoNumero;
import br.com.cenajur.model.TipoInformacao;
import br.com.topsys.util.TSDateUtil;
import br.com.topsys.util.TSParseUtil;
import br.com.topsys.util.TSUtil;

public class SMSLayoutUtil {

	public void enviarSMSAndamentoProcesso(Processo processo) {

		String msg = Constantes.TEMPLATE_SMS_ANDAMENTO;

		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_NUMERO_PROCESSO, new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());
		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_LOCAL, processo.getVara().getById().getDescricao());
		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_COLABORADOR, processo.getAdvogado().getApelido());

		SMSUtil smsUtil = new SMSUtil();

		for (ProcessoCliente processoCliente : processo.getProcessosClientes()) {

			if (!TSUtil.isEmpty(processoCliente.getCliente().getCelular())
					&& Constantes.SITUACAO_PROCESSO_CLIENTE_ATIVO.equals(processoCliente.getSituacaoProcessoCliente().getId())) {

				smsUtil.enviarMensagem(processoCliente.getCliente().getCelular(), msg.toString());
				new ContadorSms().gravarPorTipo(new TipoInformacao(Constantes.TIPO_INFORMACAO_ANDAMENTO_ID));

			}

		}

	}

	public void enviarSMSAudiencia(Audiencia audiencia) {

		Processo processo = audiencia.getProcessoNumero().getProcesso().getById();

		String msg = Constantes.TEMPLATE_SMS_AUDIENCIA_AGUARDANDO;

		if (Constantes.SITUACAO_AUDIENCIA_REALIZADA.equals(audiencia.getSituacaoAudiencia().getId())) {

			msg = Constantes.TEMPLATE_SMS_AUDIENCIA_REALIZADA;

		} else if (Constantes.SITUACAO_AUDIENCIA_NAO_REALIZADA.equals(audiencia.getSituacaoAudiencia().getId())) {

			msg = Constantes.TEMPLATE_SMS_AUDIENCIA_NAO_REALIZADA;

		}

		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_NUMERO_PROCESSO, new ProcessoNumero().obterNumeroProcessoPrincipal(processo).getNumero());
		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_DATA, TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.DD_MM_YYYY));
		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_HORA, TSParseUtil.dateToString(audiencia.getDataAudiencia(), TSDateUtil.HH_MM));
		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_LOCAL, audiencia.getVara().getById().getDescricao());
		msg = msg.replace(Constantes.CONFIGURACAO_REPLACE_COLABORADOR,
				audiencia.getAudienciasAdvogados().toString().substring(1, audiencia.getAudienciasAdvogados().toString().length() - 1));

		SMSUtil smsUtil = new SMSUtil();

		for (ProcessoCliente processoCliente : processo.getProcessosClientes()) {

			if (!TSUtil.isEmpty(processoCliente.getCliente().getCelular())
					&& Constantes.SITUACAO_PROCESSO_CLIENTE_ATIVO.equals(processoCliente.getSituacaoProcessoCliente().getId())) {

				smsUtil.enviarMensagem(processoCliente.getCliente().getCelular(), msg.toString());
				new ContadorSms().gravarPorTipo(new TipoInformacao(Constantes.TIPO_INFORMACAO_AUDIENCIA_ID));

			}

		}

		Colaborador advogado = null;

		for (AudienciaAdvogado audienciaAdvogado : audiencia.getAudienciasAdvogados()) {

			advogado = audienciaAdvogado.getAdvogado().getById();

			if (!TSUtil.isEmpty(advogado.getCelular())) {

				smsUtil.enviarMensagem(advogado.getCelular(), msg.toString());
				new ContadorSms().gravarPorTipo(new TipoInformacao(Constantes.TIPO_INFORMACAO_AUDIENCIA_ID));

			}

		}
	}
}
