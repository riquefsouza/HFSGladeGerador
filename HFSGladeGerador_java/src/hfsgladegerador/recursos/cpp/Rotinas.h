#ifndef ROTINAS_H
#define ROTINAS_H
// ---------------------------------------------------------------------------
	#include <sql.h>
// ---------------------------------------------------------------------------
	typedef SQLHSTMT CONEXAO;

	extern CONEXAO dbConexao;
	extern SQLHENV henv;
	extern SQLHDBC hdbc;
// ---------------------------------------------------------------------------
	class TRotinas {
	public:
		TRotinas();
		~TRotinas();
		void MsgDlg(Glib::ustring sMensagem, Gtk::MessageType tipo);
		Glib::ustring retLocale(Glib::ustring sTexto);
		Glib::ustring retUTF8(Glib::ustring sTexto);
		bool ConectaBanco(Glib::ustring sFonteDeDados, 
			Glib::ustring sLogin, Glib::ustring sSenha);
		void DisconectaBanco();
		bool AtualizaDados(CONEXAO hstmtlocal, Glib::ustring sSql);
		bool ConsultaDados(CONEXAO hstmtlocal, Glib::ustring sSql);
		
		<MetodosObjetos>
	private:
		
	};
// ---------------------------------------------------------------------------
	extern TRotinas *Rotinas;
// ---------------------------------------------------------------------------
#endif
