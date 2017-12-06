#import <gtk/gtk.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSString.h>
#import <sql.h>
//---------------------------------------------------------------------------
#define TAM_MAX_STR 1000
#define G_VALUE_INIT  { 0, { { 0 } } }
//---------------------------------------------------------------------------
id rotinas;
//---------------------------------------------------------------------------
@interface Rotinas : NSObject
{
	SQLHSTMT dbConexao;
	SQLHENV henv;
	SQLHDBC hdbc;

	<declaracaoObjetos>
}
//---------------------------------------------------------------------------
-(id)init;
-(void)dealloc;
//---------------------------------------------------------------------------
/*
 * C functions
 */
//---------------------------------------------------------------------------
void Rotinas_MsgDlg(gchar* sMensagem, GtkMessageType tipo);
gchar* Rotinas_retLocale(const gchar *sTexto);
gchar* Rotinas_retUTF8(const gchar *sTexto);
gboolean Rotinas_ConectaBanco(gchar *sFonteDeDados, 
					gchar *sLogin, gchar *sSenha);
void Rotinas_DisconectaBanco();
gboolean Rotinas_AtualizaDados(CONEXAO hstmtlocal, GString *sSql);
gboolean Rotinas_ConsultaDados(CONEXAO hstmtlocal, GString *sSql);

<MetodosObjetos>
//---------------------------------------------------------------------------
@end
