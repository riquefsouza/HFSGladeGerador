#include "gtk/gtk.bi"
#include "gdsl/gdsl.bi"
#include "postgresql/libpq-fe.bi"

------------------------------------------------------------------------------
#define TAM_MAX_STR 256
#define STR_NCPREPSQL           "Não conseguiu preparar o SQL!"
#define STR_NCEXECSQL           "Não conseguiu executar o SQL!"
------------------------------------------------------------------------------
type CONEXAO as PGconn ptr
type boolean as integer
------------------------------------------------------------------------------
dim shared Rotinas_dbConexao as CONEXAO
dim shared Rotinas_sBarraLogin as string
dim shared Rotinas_sUsuarioLogin as string
dim shared Rotinas_sUsuarioSenha as string
dim shared Rotinas_sCodigoSelecionado as string
dim shared Rotinas_sPreco as string
dim shared Rotinas_sQtdEstoque as string
dim shared Rotinas_sepDTHR as string
------------------------------------------------------------------------------
declare function Rotinas_retLocale(sTexto as string) as string
declare function Rotinas_retUTF8(sTexto as string) as string
declare sub Rotinas_MsgDlg(sMensagem as string, tipo as GtkMessageType)
declare function Rotinas_ConectaBanco(sFonteDeDados as string, _
  sLogin as string, sSenha as string) as boolean
declare sub Rotinas_DisconectaBanco
declare function Rotinas_ConsultaDados(hstmtlocal as CONEXAO, _
   sSql as string) as boolean
declare function Rotinas_AtualizaDados(hstmtlocal as CONEXAO, _
  sSql as string) as boolean
<declaracaoMetodosObjetos>  
------------------------------------------------------------------------------
public function Rotinas_retLocale(sTexto as string) as string
dim sTemp as zstring ptr
dim nBytesLidos as gsize ptr
dim nBytesGravados as gsize ptr
dim nErroOcorrido as GError ptr ptr

sTemp = g_locale_from_utf8(sTexto, (TAM_MAX_STR * 4), _
        nBytesLidos, nBytesGravados, nErroOcorrido)

if (*sTemp)="" then
  Rotinas_retLocale=""
else
  Rotinas_retLocale=*sTemp
end if

end function
------------------------------------------------------------------------------
public function Rotinas_retUTF8(sTexto as string) as string
dim sTemp as zstring ptr
dim nBytesLidos as gsize ptr
dim nBytesGravados as gsize ptr
dim nErroOcorrido as GError ptr ptr

sTemp = g_locale_to_utf8(sTexto, TAM_MAX_STR, _
  nBytesLidos, nBytesGravados, nErroOcorrido)
Rotinas_retUTF8=*sTemp
end function
------------------------------------------------------------------------------
public sub Rotinas_MsgDlg(sMensagem as string, tipo as GtkMessageType)
dim dialog as GtkWidget ptr

  dialog = gtk_message_dialog_new(NULL, _
    GTK_DIALOG_DESTROY_WITH_PARENT, tipo, GTK_BUTTONS_OK, _
	"%s", Rotinas_retUTF8(sMensagem))
  gtk_window_set_position(GTK_WINDOW(dialog), GTK_WIN_POS_CENTER)
  gtk_dialog_run(GTK_DIALOG(dialog))
  gtk_widget_destroy(dialog)
end sub
------------------------------------------------------------------------------
public function Rotinas_ConectaBanco(sFonteDeDados as string, _
  sLogin as string, sSenha as string) as boolean
dim as string conninfo
  
  conninfo = "dbname=" & sFonteDeDados & " user=" & sLogin & " password=" & sSenha
	
  Rotinas_dbConexao = PQconnectdb(conninfo)
	
  if (PQstatus(Rotinas_dbConexao) <> CONNECTION_OK) then
    print "Conexao com o banco de dados falhou: "; *PQerrorMessage(Rotinas_dbConexao)
    PQfinish(Rotinas_dbConexao)
    Rotinas_ConectaBanco=false
  end if

  Rotinas_ConectaBanco=true
end function
------------------------------------------------------------------------------
public sub Rotinas_DisconectaBanco
  PQfinish(Rotinas_dbConexao)
end sub
------------------------------------------------------------------------------
public function Rotinas_ConsultaDados(hstmtlocal as CONEXAO, _
   sSql as string) as boolean
dim as PGresult ptr res

  res = PQexec(hstmtlocal, sSql)
  if PQresultStatus(res) = PGRES_TUPLES_OK then    
    if PQntuples(res) > 0 then
      Rotinas_ConsultaDados=true
    else
      Rotinas_ConsultaDados=false
    end if
  else
    Rotinas_ConsultaDados=false
  end if
  PQclear(res)
end function
------------------------------------------------------------------------------
public function Rotinas_AtualizaDados(hstmtlocal as CONEXAO, _
  sSql as string) as boolean
dim as PGresult ptr res
 
  res = PQexec(hstmtlocal, sSql)
  if PQresultStatus(res) = PGRES_TUPLES_OK then
    Rotinas_AtualizaDados=true
  else
    Rotinas_AtualizaDados=false
  end if
  PQclear(res)
end function
------------------------------------------------------------------------------
<MetodosObjetos>
