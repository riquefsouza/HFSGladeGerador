#include "resource.h"
#include "Rotinas.h"

#include <sql.h>
#include <sqlext.h>
// ---------------------------------------------------------------------------
CONEXAO dbConexao;
SQLHENV henv;
SQLHDBC hdbc;
// ---------------------------------------------------------------------------
TRotinas *Rotinas = (TRotinas *) NULL;
// ---------------------------------------------------------------------------
TRotinas::TRotinas() { 

}
// ---------------------------------------------------------------------------
TRotinas::~TRotinas() { 

}
// ---------------------------------------------------------------------------
void TRotinas::MsgDlg(Glib::ustring sMensagem, Gtk::MessageType tipo) {

  Gtk::MessageDialog dialog(retUTF8(sMensagem), false,
   tipo, Gtk::BUTTONS_OK, true);
  dialog.set_position(Gtk::WIN_POS_CENTER);
  dialog.set_has_separator(false);
  dialog.run();
}
// ---------------------------------------------------------------------------
Glib::ustring TRotinas::retLocale(Glib::ustring sTexto) {
  return Glib::locale_from_utf8(sTexto);
}
// ---------------------------------------------------------------------------
Glib::ustring TRotinas::retUTF8(Glib::ustring sTexto) {
  //return Glib::locale_to_utf8(sTexto);
  return sTexto;
}
// ---------------------------------------------------------------------------
bool TRotinas::ConectaBanco(Glib::ustring sFonteDeDados,
 Glib::ustring sLogin, Glib::ustring sSenha) {
   SQLRETURN rc;

   dbConexao = SQL_NULL_HSTMT;
   henv = SQL_NULL_HENV;
   hdbc = SQL_NULL_HDBC;

   // Aloca o ambiente ODBC e salva o manuseador.
   rc = SQLAllocHandle(SQL_HANDLE_ENV, SQL_NULL_HANDLE, & henv);

   if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
     // Faz o ODBC saber que esta é uma aplicação ODBC 3.0.
     rc = SQLSetEnvAttr(henv, SQL_ATTR_ODBC_VERSION,
      (SQLPOINTER)SQL_OV_ODBC3, 0);

     if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
       /* Aloca o manuseador da conexão */
       rc = SQLAllocHandle(SQL_HANDLE_DBC, henv, & hdbc);

       if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
         /* Atribui login timeout para 5 segundos. */
         SQLSetConnectAttr(hdbc, SQL_LOGIN_TIMEOUT, (SQLPOINTER)5, 0);

         /* Conecta na fonte de dados */
         rc = SQLConnect(hdbc, (SQLTCHAR *) sFonteDeDados.c_str(), SQL_NTS,
          (SQLTCHAR *) sLogin.c_str(), SQL_NTS, (SQLTCHAR *) sSenha.c_str(), SQL_NTS);

         if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
           /* Aloca manuseador do procedimento */
           rc = SQLAllocHandle(SQL_HANDLE_STMT, hdbc, & dbConexao);

           if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
             return true;
           } else
             SQLFreeHandle(SQL_HANDLE_STMT, dbConexao);
         } else
           SQLDisconnect(hdbc);
       } else
         SQLFreeHandle(SQL_HANDLE_DBC, hdbc);
     }
   } else
     SQLFreeHandle(SQL_HANDLE_ENV, henv);

   return false;

}
// ---------------------------------------------------------------------------
void TRotinas::DisconectaBanco() {
  SQLFreeHandle(SQL_HANDLE_STMT, dbConexao);
  SQLDisconnect(hdbc);
  SQLFreeHandle(SQL_HANDLE_DBC, hdbc);
  SQLFreeHandle(SQL_HANDLE_ENV, henv);
}
// ---------------------------------------------------------------------------
bool TRotinas::ConsultaDados(CONEXAO hstmtlocal, Glib::ustring sSql) {
  SQLRETURN rc;

  rc = SQLPrepare(hstmtlocal, (SQLTCHAR *) sSql.c_str(), SQL_NTS);
  if (!(rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO)) {
    MsgDlg(STR_NCPREPSQL, Gtk::MESSAGE_ERROR);
    return false;
  }
  rc = SQLExecute(hstmtlocal);
  if (!(rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO)) {
    MsgDlg(STR_NCEXECSQL, Gtk::MESSAGE_ERROR);
    return false;
  }
  if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
    rc = SQLFetch(hstmtlocal);
    if (rc == SQL_NO_DATA) {
      SQLCloseCursor(hstmtlocal);
      return false;
    } else {
      SQLCloseCursor(hstmtlocal);
      return true;
    }
  } else {
    SQLCloseCursor(hstmtlocal);
    return false;
  }
}
// ---------------------------------------------------------------------------
bool TRotinas::AtualizaDados(CONEXAO hstmtlocal, Glib::ustring sSql) {
  SQLRETURN rc;

  rc = SQLPrepare(hstmtlocal, (SQLTCHAR *) retLocale(sSql).c_str(), SQL_NTS);
  if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO) {
    rc = SQLExecute(hstmtlocal);
    if (rc == SQL_SUCCESS || rc == SQL_SUCCESS_WITH_INFO)
      return true;
    else {
      return false;
    }
  } else {
    MsgDlg(STR_NCPREPSQL, Gtk::MESSAGE_ERROR);
    return false;
  }
}

<MetodosObjetos>
