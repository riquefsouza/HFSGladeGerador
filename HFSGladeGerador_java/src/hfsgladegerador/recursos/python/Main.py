#!/usr/bin/env python
import pygtk
pygtk.require('2.0')
import gtk

from Rotinas import Rotinas

class <nomeProjeto>:
	def __init__(self):
		Rotinas.instancia = Rotinas()
		<classePrincipal>.<objPrincipal> = <classePrincipal>()
		<classePrincipal>.<objPrincipal>.form.show()

	def main(self):
		gtk.main()

if __name__ == "__main__":
	instancia = <nomeProjeto>()
	instancia.main()
