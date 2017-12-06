#ifndef ROTINAS_H
#define ROTINAS_H
// ---------------------------------------------------------------------------
	#include <sql.h>
// ---------------------------------------------------------------------------
	#define TAM_MAX_STR 1000
	#define G_VALUE_INIT  { 0, { { 0 } } }
// ---------------------------------------------------------------------------
	typedef SQLHSTMT CONEXAO;

	extern CONEXAO  dbConexao;
	extern SQLHENV  henv;
	extern SQLHDBC  hdbc;
// ---------------------------------------------------------------------------
	void Rotinas_MsgDlg(gchar* sMensagem, GtkMessageType tipo);
	gchar* Rotinas_retLocale(const gchar *sTexto);
	gchar* Rotinas_retUTF8(const gchar *sTexto);
	gboolean Rotinas_ConectaBanco(gchar *sFonteDeDados, 
						gchar *sLogin, gchar *sSenha);
	void Rotinas_DisconectaBanco();
	gboolean Rotinas_AtualizaDados(CONEXAO hstmtlocal, GString *sSql);
	gboolean Rotinas_ConsultaDados(CONEXAO hstmtlocal, GString *sSql);
// ---------------------------------------------------------------------------
	<MetodosObjetos>
// ---------------------------------------------------------------------------
#endif
// ---------------------------------------------------------------------------
