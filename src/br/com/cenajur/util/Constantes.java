package br.com.cenajur.util;

public class Constantes {

	private Constantes() {
	}
	
	public static final String COLABORADOR_CONECTADO = "colaboradorConectado";
	
	public static final Long TIPO_COLABORADOR_ADVOGADO = 2L;
	public static final Long TIPO_COLABORADOR_ESTAGIARIO = 50L;
	
	public static final Long SITUACAO_PROCESSO_ARQUIVADO = 3L;
	public static final Long SITUACAO_PROCESSO_CLIENTE_ATIVO = 1L;
	public static final Long SITUACAO_PROCESSO_CLIENTE_ARQUIVADO = 3L;
	public static final Long SITUACAO_PROCESSO_PARTE_CONTRARIA_ATIVO = 1L;
	public static final Long SITUACAO_PROCESSO_PARTE_CONTRARIA_ARQUIVADO = 3L;
	
	public static final Long SITUACAO_AUDIENCIA_AGUARDANDO = 3L;
	public static final Long SITUACAO_AUDIENCIA_REALIZADA = 1L;
	public static final Long SITUACAO_AUDIENCIA_NAO_REALIZADA = 2L;
	
	public static final Long TIPO_CATEGORIA_PROCESSO = 1L;
	public static final Long TIPO_CATEGORIA_AUDIENCA = 2L;
	public static final Long TIPO_CATEGORIA_ANDAMENTO_PROCESSO = 3L;
	public static final Long TIPO_CATEGORIA_CLIENTE = 4L;
	public static final Long TIPO_CATEGORIA_GERAL = 5L;
	public static final Long TIPO_CATEGORIA_COLABORADOR = 6L;
	
	public static final Long PLANO_MENSAL = 1L;
	
	public static final Long TIPO_AGENDA_TAREFA = 1L;
	public static final Long TIPO_AGENDA_AUDIENCIA = 3L;
	public static final Long TIPO_AGENDA_VISITA_DO_CLIENTE = 4L;
	
	public static final Long REGRA_BLOQUEIO_MENSAGEM = 1L;
	public static final Long REGRA_BLOQUEIO_TAREFA = 2L;
	public static final Long REGRA_BLOQUEIO_AUDIENCIA = 3L;
	
	//public static final String PASTA_UPLOAD_ARQUIVO = "E:\\arquivos\\";
	public static final String PASTA_UPLOAD_ARQUIVO = "/home/cenajurc/arquivos/";
	//public static final String PASTA_UPLOAD_IMAGEM = "E:\\imagens\\";
	public static final String PASTA_UPLOAD_IMAGEM = "/home/cenajurc/arquivos/imagens/";
	//public static final String PASTA_UPLOAD_IMAGEM_TEMP = "E:\\imagens\\tmp/";
	public static final String PASTA_UPLOAD_IMAGEM_TEMP = "/home/cenajurc/arquivos/imagens/tmp/";
	
	//public static final String PASTA_DOWNLOAD_ARQUIVO = "http://localhost:80/arquivos/";
	public static final String PASTA_DOWNLOAD_ARQUIVO = "http://www.agepol.org.br/arquivos/";
	//public static final String PASTA_DOWNLOAD_IMAGEM = "http://localhost:80/imagens/";
	public static final String PASTA_DOWNLOAD_IMAGEM = "http://www.agepol.org.br/arquivos/imagens/";
	//public static final String PASTA_DOWNLOAD_IMAGEM_TMP = "http://localhost:80/imagens/tmp/";
	public static final String PASTA_DOWNLOAD_IMAGEM_TMP = "http://www.agepol.org.br/arquivos/imagens/tmp/";
	
	//public static final String PASTA_ANDAMENTO_PROCESSO = "andamentos_processos\\";
	public static final String PASTA_ANDAMENTO_PROCESSO = "andamentos_processos/";
	//public static final String PASTA_PROCESSO = "processos\\";
	public static final String PASTA_PROCESSO = "processos/";
	//public static final String PASTA_AUDIENCIA = "audiencias\\";
	public static final String PASTA_AUDIENCIA = "audiencias/";
	
	//public static final String PASTA_GERAL = "geral\\";
	public static final String PASTA_GERAL = "geral/";
	//public static final String PASTA_CLIENTE = "clientes\\";
	public static final String PASTA_CLIENTE = "clientes/";
	//public static final String PASTA_COLABORADOR = "colaboradores\\";
	public static final String PASTA_COLABORADOR = "colaboradores/";
	
	public static final String DOC_TEMP = "doc_temp";
	public static final String FOTO_CAM_TEMP = "_foto_cam.jpg";
	
	public static final Long REGRA_EMAIL_AUDIENCIA = 1L;
	public static final Long REGRA_EMAIL_VISITA_COM_CLIENTE = 2L;
	public static final Long REGRA_EMAIL_ANDAMENTO_PROCESSO = 3L;
	
	public static final Long CONFIGURACOES_REPLACE_EMAIL_ASSOCIADO = 1L;
	public static final Long CONFIGURACOES_REPLACE_EMAIL_PROCESSO = 2L;
	public static final Long CONFIGURACOES_REPLACE_EMAIL_DATA_ATUAL = 4L;
	public static final Long CONFIGURACOES_REPLACE_EMAIL_DATA_VISITA = 5L;
	public static final Long CONFIGURACOES_REPLACE_EMAIL_LOCAL = 6L;
	public static final Long CONFIGURACOES_REPLACE_EMAIL_DATA = 7L;
	public static final Long CONFIGURACOES_REPLACE_EMAIL_HORA = 8L;
	public static final Long CONFIGURACOES_REPLACE_EMAIL_ADVOGADO = 9L;
	public static final Long CONFIGURACOES_REPLACE_EMAIL_TIPO_ANDAMENTO = 10L;
	public static final Long CONFIGURACOES_REPLACE_EMAIL_DESCRICAO = 11L;
	
	public static final Long PERMISSAO_MENSAGENS = 18L;
	public static final Long PERMISSAO_COLABORADOR = 8L;
	public static final Long PERMISSAO_OBJETO = 2L;
	public static final Long PERMISSAO_LOG_ENVIO_EMAIL = 49L;
	public static final Long PERMISSAO_PROCESSO = 13L;
	public static final Long PERMISSAO_CLIENTE = 12L;
	public static final Long PERMISSAO_AUDIENCIA = 16L;
	public static final Long PERMISSAO_ANDAMENTO = 17L;
	public static final Long PERMISSAO_FATURAMENTO = 24L;
	public static final Long PERMISSAO_AGENDA = 26L;
	public static final Long PERMISSAO_GRUPO = 9L;
	
	public static final Long EMAIL_ID = 1L;
	public static final Long MENSAGEM_COMPROVANTE_PAGAMENTO_ID = 1L;
	
	public static final Long TIPO_INFORMACAO_AUDIENCIA_ID = 1L;
	public static final Long TIPO_INFORMACAO_ANDAMENTO_ID = 2L;
	public static final Long TIPO_INFORMACAO_ANIVERSARIO_ID = 3L;
	public static final Long TIPO_INFORMACAO_PROCESSO_NOVO_ID = 4L;
	public static final Long TIPO_INFORMACAO_INADIMPLENCIA_ID = 5L;
	public static final Long TIPO_INFORMACAO_ASSOCIADOS_NOVOS_ID = 6L;
	public static final Long TIPO_INFORMACAO_VISITAS_ID = 7L;
	
	public static final Long TIPO_PROCESSO_COLETIVO = 3L;
	
	public static final String CONFIGURACAO_REPLACE_NUMERO_PROCESSO = "(numero_processo)";
	public static final String CONFIGURACAO_REPLACE_DATA = "(data)";
	public static final String CONFIGURACAO_REPLACE_HORA = "(hora)";
	public static final String CONFIGURACAO_REPLACE_LOCAL = "(local)";
	public static final String CONFIGURACAO_REPLACE_COLABORADOR = "(colaborador)";
	public static final String CONFIGURACAO_REPLACE_OBJETO = "(objeto)";
	public static final String CONFIGURACAO_REPLACE_PARTE_CONTRARIA = "(parte_contraria)";
	
	public static final String TEMPLATE_SMS_AUDIENCIA_AGUARDANDO = "AUDIÊNCIA: Prezado(a), Vossa Senhoria possui uma nova Audiência. "+ CONFIGURACAO_REPLACE_NUMERO_PROCESSO +", em "+ CONFIGURACAO_REPLACE_DATA +", "+ CONFIGURACAO_REPLACE_HORA +", no(a) "+ CONFIGURACAO_REPLACE_LOCAL +" e será acompanhado(a) por "+ CONFIGURACAO_REPLACE_COLABORADOR;
	public static final String TEMPLATE_SMS_AUDIENCIA_REALIZADA = "AUDIÊNCIA: Prezado(a), A sua opinião é importante! Em "+ CONFIGURACAO_REPLACE_DATA +" às "+ CONFIGURACAO_REPLACE_HORA +", Vossa Senhoria foi acompanhado na audiência realizada no(a) "+ CONFIGURACAO_REPLACE_LOCAL +" pelo "+ CONFIGURACAO_REPLACE_COLABORADOR +". Assim pedimos que envie sua crítica ou sugestão para o Coordenador Jurídico.";
	public static final String TEMPLATE_SMS_AUDIENCIA_NAO_REALIZADA = "AUDIÊNCIA: Prezado(a), A audiência anteriormente designada para o dia "+ CONFIGURACAO_REPLACE_DATA +" às "+ CONFIGURACAO_REPLACE_HORA +" na "+ CONFIGURACAO_REPLACE_LOCAL +" foi SUSPENSA, e não será mais realizada nesta data.";
	public static final String TEMPLATE_SMS_ANDAMENTO = "ANDAMENTO PROCESSUAL: Prezado(a), Vossa Senhoria possui um novo Andamento no seu processo " + CONFIGURACAO_REPLACE_NUMERO_PROCESSO + ", que tramita "+ CONFIGURACAO_REPLACE_LOCAL + ", tendo como Advogado responsável "+ CONFIGURACAO_REPLACE_COLABORADOR + ".";
	public static final String TEMPLATE_SMS_VISITA_ASSOCIADO = "CENAJUR AGRADECE SUA VISITA: Prezado(a), Agradecemos sua visita na Associação em " + CONFIGURACAO_REPLACE_DATA + ", sendo atendido por " + CONFIGURACAO_REPLACE_COLABORADOR;
	public static final String TEMPLATE_SMS_ANIVERSARIANTE = "FELIZ ANIVERSÁRIO: Prezado(a), A equipe do CENAJUR deseja SAÚDE, PAZ e cada vez mais SEGURANÇA JURÍDICA. Feliz Aniversário!";
	public static final String TEMPLATE_SMS_INADIMPLENTES = "CENAJUR INFORMA: Prezado(a), Até o momento não consta o pagamento do último mês. Favor Contactar a Associação. Caso já tenha efetuado o pagamento, desconsidere esta mensagem.";
	public static final String TEMPLATE_SMS_PROCESSO_NOVO = "PROCESSO NOVO: Prezado(a), Vossa Senhoria possui um processo novo. Número do Processo: " + CONFIGURACAO_REPLACE_NUMERO_PROCESSO + ", Local : " + CONFIGURACAO_REPLACE_LOCAL + ", Objeto: " + CONFIGURACAO_REPLACE_OBJETO + ", Parte Contrária: " + CONFIGURACAO_REPLACE_PARTE_CONTRARIA + " e Advogado: " + CONFIGURACAO_REPLACE_COLABORADOR;
	public static final String TEMPLATE_SMS_ASSOCIADO_NOVO = "CENAJUR AGRADECE: Prezado(a), Obrigado por escolher o CENAJUR, aproveitamos a oportunidade e reafirmamos nosso compromisso de prestar uma assistência jurídica efetiva e com qualidade, ética, responsabilidade e experiência.";
	
}
