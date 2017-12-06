import pygtk
pygtk.require('2.0')
import gtk
import gobject

class Rotinas:
	dbConexao = None
	STR_NCONBANCO = u"N\xe3o consigo conectar com o banco de dados!"

	def __init__(self):
		self.data = []

	def MsgDlg(self, sMensagem, tipo):
		self.dialog = gtk.MessageDialog(None, gtk.DIALOG_DESTROY_WITH_PARENT, 
			tipo, gtk.BUTTONS_OK, sMensagem)
		self.dialog.set_position(gtk.WIN_POS_CENTER)
		self.dialog.run()
		self.dialog.destroy()

	def ConectaBanco(self, sBancoDeDados, sLogin, sSenha):
		import odbc
		#import MySQLdb
		try:
			#self.dbConexao = MySQLdb.connect( host = 'localhost', 
			#  db = sBancoDeDados, user = sLogin, passwd = sSenha )
			self.dbConexao = odbc.odbc( sBancoDeDados )
		except:
			self.MsgDlg(Resource.STR_NCONBANCO, gtk.MESSAGE_ERROR)
			return False
		else:
			return True

	def DisconectaBanco(self):
		pass

	<MetodosObjetos>