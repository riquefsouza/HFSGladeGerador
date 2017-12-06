require 'gtk2'
require 'DBI'
#require 'DBD::ODBC'
require 'iconv'

$Rotinas = nil

class Rotinas
	attr :dbConexao, true
	STR_NCONBANCO     = "N\xe3o consigo conectar com o banco de dados!"
	STR_NALOCODBC     = "N\xe3o foi poss\xedvel alocar um manuseador para o ambiente ODBC"
	STR_NCPREPSQL     = "N\xe3o conseguiu preparar o SQL!"
	STR_NCEXECSQL     = "N\xe3o conseguiu executar o SQL!"
  
	def to_utf8(texto)
		return Iconv.iconv('utf-8','iso-8859-1', texto)[0]
	end

	def MsgDlg(sMensagem, tipo)
		dialog = Gtk::MessageDialog.new(nil, Gtk::Dialog::DESTROY_WITH_PARENT, 
						tipo, Gtk::MessageDialog::BUTTONS_OK, to_utf8(sMensagem))
		dialog.set_window_position(Gtk::Window::POS_CENTER)
		dialog.run()
		dialog.destroy()
	end

	def ConectaBanco(sBancoDeDados, sLogin, sSenha)
		begin
			self.dbConexao = DBI.connect( sBancoDeDados, sLogin, sSenha ) 
			return true
		rescue
			MsgDlg(Resource::STR_NCONBANCO, Gtk::MessageDialog::ERROR)
			return false
		end
	end
  
	def DisconectaBanco()
		self.dbConexao.disconnect()
	end

	def AtualizaDados(hBancoLocal, sSql)
		begin
			sth = hBancoLocal.prepare( sSql )
			sth.execute()
			sth.finish()
			return true
		rescue
			MsgDlg(Resource::STR_NCEXECSQL, Gtk::MessageDialog::ERROR)
			return false
		end
	end
	
	<MetodosObjetos>
end
