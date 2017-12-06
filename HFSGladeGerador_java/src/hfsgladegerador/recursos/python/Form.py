import pygtk
pygtk.require('2.0')
import gtk

from Rotinas import Rotinas

class <classeForm>:
	def __init__(self):
		<construtorPaiForm>
		<inicializa>
		<conectaEventos>

		self.form.connect("destroy", self.destroy)

	def destroy(self, widget, data=None):
		pass

	def mostrar(self):
		<conteudoMostrar>

	<metodos>