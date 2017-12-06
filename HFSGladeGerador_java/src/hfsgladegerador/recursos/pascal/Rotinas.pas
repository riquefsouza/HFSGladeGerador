unit URotinas;

{$mode objfpc}{$H+}

interface

uses
  Classes, SysUtils, glib2, gtk2, odbcsql;

type
 TRotinas = class
 private
   { private declarations }
	dbConexao: SQLHSTMT;
	henv: SQLHENV;
	hdbc: SQLHDBC;
 public
   { public declarations }
	constructor Create;
	procedure MsgDlg(sMensagem: string; tipo: TGtkMessageType);
	function retLocale(sTexto: string): string;
	function retUTF8(sTexto: string): string;
	function ConectaBanco(sFonteDeDados, sLogin, sSenha: string): boolean;
	procedure DisconectaBanco;
	function ConsultaDados(hstmtlocal: SQLHSTMT; sSql: string):boolean;
	function AtualizaDados(hstmtlocal: SQLHSTMT; sSql: string): boolean;
	<declaracaoMetodosObjetos>
end;

const
  TAM_MAX_STR: integer = 1000;
  STR_NCPREPSQL  : string = 'Não conseguiu preparar o SQL!';
  STR_NCEXECSQL  : string = 'Não conseguiu executar o SQL!';

var
 Rotinas: TRotinas;

implementation

constructor TRotinas.Create;
begin
//
end;

procedure TRotinas.MsgDlg(sMensagem: string; tipo: TGtkMessageType);
var dialog: PGtkWidget;
begin
  dialog := gtk_message_dialog_new(nil,
    GTK_DIALOG_DESTROY_WITH_PARENT, tipo, GTK_BUTTONS_OK,
    '%s', PChar(retUTF8(sMensagem)));
  gtk_window_set_position (GTK_WINDOW(dialog), GTK_WIN_POS_CENTER_ALWAYS);
  gtk_dialog_run(GTK_DIALOG (dialog));
  gtk_widget_destroy(dialog);
end;

function TRotinas.retLocale(sTexto: string): string;
var sTemp: string;
nBytesLidos, nBytesGravados: gsize;
nErroOcorrido: PGError;
begin
sTemp:='';

sTemp := g_locale_from_utf8(PChar(sTexto), (TAM_MAX_STR * 4),
        @nBytesLidos, @nBytesGravados, @nErroOcorrido);

if sTemp='' then
  sTemp:=sTexto;

result:=sTemp;
end;

function TRotinas.retUTF8(sTexto: string): string;
var sTemp: string;
nBytesLidos, nBytesGravados: gsize;
nErroOcorrido: PGError;
begin
sTemp:= g_locale_to_utf8(PChar(sTexto), TAM_MAX_STR,
        @nBytesLidos, @nBytesGravados, @nErroOcorrido);
result:=sTemp;
end;

function TRotinas.ConectaBanco(sFonteDeDados, sLogin, sSenha: string): boolean;
var rc: SQLRETURN;
begin
dbConexao := SQL_NULL_HSTMT;
henv  := SQL_NULL_HENV;
hdbc  := SQL_NULL_HDBC;

// Aloca o ambiente ODBC e salva o manuseador.
rc := SQLAllocHandle(SQL_HANDLE_ENV, SQL_NULL_HANDLE, henv);

if (rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO) then begin
  // Faz o ODBC saber que esta Ã© uma aplicaÃ§Ã£o ODBC 3.0.
  rc := SQLSetEnvAttr(henv, SQL_ATTR_ODBC_VERSION, SQLPOINTER(SQL_OV_ODBC3), 0);

  if (rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO) then begin
    // Aloca o manuseador da conexÃ£o
    rc := SQLAllocHandle(SQL_HANDLE_DBC, henv, hdbc);

    if (rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO) then begin
      // Atribui login timeout para 5 segundos.
      SQLSetConnectAttr(hdbc, SQL_LOGIN_TIMEOUT, SQLPOINTER(5), 0);

      // Conecta na fonte de dados
      rc := SQLConnect(hdbc, Pchar(sFonteDeDados), SQL_NTS,
               PChar(sLogin), SQL_NTS, PChar(sSenha), SQL_NTS);

      if (rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO) then begin
        // Aloca manuseador do procedimento
        rc := SQLAllocHandle(SQL_HANDLE_STMT, hdbc, dbConexao);

        if (rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO) then begin
          result:=true;
          exit;
	end else
          SQLFreeHandle(SQL_HANDLE_STMT, dbConexao);
      end else
          SQLDisconnect(hdbc);
    end else
        SQLFreeHandle(SQL_HANDLE_DBC, hdbc);
  end;
end else
    SQLFreeHandle(SQL_HANDLE_ENV, henv);

result:=false;
end;

procedure TRotinas.DisconectaBanco;
begin
  SQLFreeHandle(SQL_HANDLE_STMT, dbConexao);
  SQLDisconnect(hdbc);
  SQLFreeHandle(SQL_HANDLE_DBC, hdbc);
  SQLFreeHandle(SQL_HANDLE_ENV, henv);
end;

function TRotinas.ConsultaDados(hstmtlocal: SQLHSTMT; sSql: string):boolean;
var rc: SQLRETURN;
begin
rc := SQLPrepare(hstmtlocal, PChar(sSql), SQL_NTS);
if not((rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO)) then begin
  Rotinas.MsgDlg(STR_NCPREPSQL, GTK_MESSAGE_ERROR);
  result:=false;
  exit;
end;
rc := SQLExecute(hstmtlocal);
if not((rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO)) then begin
  Rotinas.MsgDlg(STR_NCEXECSQL, GTK_MESSAGE_ERROR);
  result:=false;
  exit;
end;
if (rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO) then begin
  rc := SQLFetch(hstmtlocal);
  if ( rc = SQL_NO_DATA ) then begin
    SQLCloseCursor(hstmtlocal);
    result:=false;
  end else begin
    SQLCloseCursor(hstmtlocal);
    result:=true;

  end;
end else begin
  SQLCloseCursor(hstmtlocal);
  result:=false;
end;
end;

function TRotinas.AtualizaDados(hstmtlocal: SQLHSTMT; sSql: string): boolean;
var rc: SQLRETURN;
begin
rc := SQLPrepare(hstmtlocal, PChar(sSql), SQL_NTS);
  if (rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO) then begin
    rc := SQLExecute(hstmtlocal);
    if (rc = SQL_SUCCESS) or (rc = SQL_SUCCESS_WITH_INFO) then
      result:=true
    else
      result:=false;
  end else begin
    MsgDlg(STR_NCPREPSQL, GTK_MESSAGE_ERROR);
    result:=false;
  end;
end;

<MetodosObjetos>

end.
