package br.com.cenajur.util;

public class Constantes {

	private Constantes() {
	}
	
	public static final String COLABORADOR_CONECTADO = "colaboradorConectado";
	
	public static final Long TIPO_COLABORADOR_ADVOGADO = 2L;
	
	public static final Long SITUACAO_PROCESSO_ARQUIVADO = 3L;
	public static final Long SITUACAO_PROCESSO_CLIENTE_ATIVO = 1L;
	public static final Long SITUACAO_PROCESSO_CLIENTE_ARQUIVADO = 2L;
	public static final Long SITUACAO_PROCESSO_PARTE_CONTRARIA_ATIVO = 1L;
	public static final Long SITUACAO_PROCESSO_PARTE_CONTRARIA_ARQUIVADO = 2L;
	
	public static final Long TIPO_CATEGORIA_PROCESSO = 1L;
	public static final Long TIPO_CATEGORIA_AUDIENCA = 2L;
	public static final Long TIPO_CATEGORIA_ANDAMENTO_PROCESSO = 3L;
	public static final Long TIPO_CATEGORIA_CLIENTE = 4L;
	public static final Long TIPO_CATEGORIA_GERAL = 5L;
	
	public static final String PASTA_UPLOAD_ARQUIVO = "E:\\arquivos\\";
	public static final String PASTA_UPLOAD_IMAGEM = "E:\\imagens\\";
	public static final String PASTA_DOWNLOAD_ARQUIVO = "http://localhost:8080/arquivos/";
	public static final String PASTA_DOWNLOAD_IMAGEM = "http://localhost:8080/imagens/";
	public static final String PASTA_DOWNLOAD_IMAGEM_TMP = "http://localhost:8080/imagens/tmp/";
	
	public static final String PASTA_ANDAMENTO_PROCESSO = "andamentos_processos\\";
	public static final String PASTA_PROCESSO = "processos\\";
	public static final String PASTA_AUDIENCIA = "audiencias\\";
	public static final String PASTA_GERAL = "geral\\";
	public static final String PASTA_CLIENTE = "clientes\\";
	
}
