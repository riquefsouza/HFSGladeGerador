#import "Rotinas.h"
#import "<classePrincipal>.h"

#import <sql.h>
#import <sqlext.h>
//---------------------------------------------------------------------------
@implementation Rotinas

-(id)init
{
	if (self = [super init]) {
		/* */
	}

	rotinas = self;
	return self;
}
//---------------------------------------------------------------------------
-(void)dealloc
{
	[super dealloc];
}
//---------------------------------------------------------------------------
/*
 * C functions
 */
//---------------------------------------------------------------------------
void Rotinas_MsgDlg(gchar* sMensagem, GtkMessageType tipo) {
  GtkWidget *dialog;
  dialog = gtk_message_dialog_new(GTK_WINDOW(<classePrincipal>.<objPrincipal>),
    GTK_DIALOG_DESTROY_WITH_PARENT, tipo, GTK_BUTTONS_OK,
    "%s", Rotinas_retUTF8(sMensagem));
  gtk_window_set_position (GTK_WINDOW(dialog), GTK_WIN_POS_CENTER);
  gtk_dialog_run(GTK_DIALOG (dialog));
  gtk_widget_destroy(dialog);
}
// ---------------------------------------------------------------------------
gchar* Rotinas_retLocale(const gchar *sTexto) {
GString *sTemp = g_string_new("");
gsize nBytesLidos, nBytesGravados;
GError *nErroOcorrido;

g_string_assign(sTemp, g_locale_from_utf8(sTexto, (TAM_MAX_STR * 4),
        &nBytesLidos, &nBytesGravados, &nErroOcorrido));

if (sTemp->str==NULL)
  g_string_assign(sTemp, sTexto);

return sTemp->str;
}
// ---------------------------------------------------------------------------
gchar* Rotinas_retUTF8(const gchar *sTexto) {


GString *sTemp = g_string_new("");
gsize nBytesLidos, nBytesGravados;
GError *nErroOcorrido;

g_string_assign(sTemp, g_locale_to_utf8(sTexto, TAM_MAX_STR,
        &nBytesLidos, &nBytesGravados, &nErroOcorrido));



/*
g_print("nBytesLidos = %d\n", nBytesLidos);
g_print("nBytesGravados = %d\n", nBytesGravados);
switch (nErroOcorrido->code) {
  case G_CONVERT_ERROR_NO_CONVERSION:
    g_print("nErroOcorrido = G_CONVERT_ERROR_NO_CONVERSION\n");
    break;
  case G_CONVERT_ERROR_ILLEGAL_SEQUENCE:
    g_print("nErroOcorrido = G_CONVERT_ERROR_ILLEGAL_SEQUENCE\n");
    break;
  case G_CONVERT_ERROR_FAILED:
    g_print("nErroOcorrido = G_CONVERT_ERROR_FAILED\n");
    break;
  case G_CONVERT_ERROR_PARTIAL_INPUT:
    g_print("nErroOcorrido = G_CONVERT_ERROR_PARTIAL_INPUT\n");
    break;
  case G_CONVERT_ERROR_BAD_URI:
    g_print("nErroOcorrido = G_CONVERT_ERROR_BAD_URI\n");
    break;
  case G_CONVERT_ERROR_NOT_ABSOLUTE_PATH:
    g_print("nErroOcorrido = G_CONVERT_ERROR_NOT_ABSOLUTE_PATH\n");
    break;
  default:
    g_print("nErroOcorrido = %d\n", nErroOcorrido->code);
}
*/


if (sTemp->str==NULL)
  g_string_assign(sTemp, sTexto);

return sTemp->str;

//return sTexto;
}
// ---------------------------------------------------------------------------
gboolean Rotinas_ConectaBanco(gchar *sFonteDeDados,
  gchar *sLogin, gchar *sSenha)
{
SQLRETURN rc;

dbConexao = SQL_NULL_HSTMT;
henv  = SQL_NULL_HENV;
hdbc  = SQL_NULL_HDBC;

// Aloca o ambiente ODBC e salva o manuseador.
rc = SQLAllocHandle(SQL_HANDLE_ENV, SQL_NULL_HANDLE, &henv);

if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
  // Faz o ODBC saber que esta é uma aplicação ODBC 3.0.
  rc = SQLSetEnvAttr(henv, SQL_ATTR_ODBC_VERSION, (SQLPOINTER) SQL_OV_ODBC3, 0);

  if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
    /* Aloca o manuseador da conexão */
    rc = SQLAllocHandle(SQL_HANDLE_DBC, henv, &hdbc);

    if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
      /* Atribui login timeout para 5 segundos. */
      SQLSetConnectAttr(hdbc, SQL_LOGIN_TIMEOUT, (SQLPOINTER) 5, 0);

      /* Conecta na fonte de dados */
      rc = SQLConnect(hdbc, (SQLTCHAR*) sFonteDeDados, SQL_NTS,
               (SQLTCHAR*) sLogin, SQL_NTS, (SQLTCHAR*) sSenha, SQL_NTS);

      if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO){
        /* Aloca manuseador do procedimento */
        rc = SQLAllocHandle(SQL_HANDLE_STMT, hdbc, &dbConexao);

        if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
          return TRUE;
	} else
          SQLFreeHandle(SQL_HANDLE_STMT, dbConexao);
      } else
          SQLDisconnect(hdbc);
    } else
        SQLFreeHandle(SQL_HANDLE_DBC, hdbc);
  }
} else
    SQLFreeHandle(SQL_HANDLE_ENV, henv);

return FALSE;

}
// ---------------------------------------------------------------------------
void Rotinas_DisconectaBanco()
{
  SQLFreeHandle(SQL_HANDLE_STMT, dbConexao);
  SQLDisconnect(hdbc);
  SQLFreeHandle(SQL_HANDLE_DBC, hdbc);
  SQLFreeHandle(SQL_HANDLE_ENV, henv);
}
// ---------------------------------------------------------------------------
gboolean Rotinas_ConsultaDados(CONEXAO hstmtlocal, GString *sSql)
{
SQLRETURN   rc;

rc = SQLPrepare(hstmtlocal, (SQLTCHAR*) sSql->str, SQL_NTS);
if (!(rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO)) {
  Rotinas_MsgDlg(STR_NCPREPSQL, GTK_MESSAGE_ERROR);
  return FALSE;
}
rc = SQLExecute(hstmtlocal);
if (!(rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO)) {
  Rotinas_MsgDlg(STR_NCEXECSQL, GTK_MESSAGE_ERROR);
  return FALSE;
}
if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
  rc = SQLFetch(hstmtlocal);
  if ( rc == SQL_NO_DATA ) {
  	SQLCloseCursor(hstmtlocal);
  	return FALSE;
  } else {
  	SQLCloseCursor(hstmtlocal);
    return TRUE;
  }
} else {
  SQLCloseCursor(hstmtlocal);
  return FALSE;
}
}
// ---------------------------------------------------------------------------
gboolean Rotinas_AtualizaDados(CONEXAO hstmtlocal, GString *sSql)
{
SQLRETURN   rc;
//gchar sLocal[TAM_MAX_STR * 4];
//strcpy(sLocal, Rotinas_retLocale(sSql->str));

rc = SQLPrepare(hstmtlocal, (SQLTCHAR*) sSql->str, SQL_NTS);
  if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
    rc = SQLExecute(hstmtlocal);
    if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO)
      return TRUE;
    else {
     return FALSE;
    }
  } else {
    Rotinas_MsgDlg(STR_NCPREPSQL, GTK_MESSAGE_ERROR);
    return FALSE;
  }
}
// ---------------------------------------------------------------------------
<MetodosObjetos>
// ---------------------------------------------------------------------------
@end
