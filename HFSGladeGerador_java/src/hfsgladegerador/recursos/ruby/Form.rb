require 'gtk2'
require 'Rotinas'
<declaracaoForms>

$<objForm> = nil

class <classeForm> < <classePaiForm>

	def initialize()
		<construtorPaiForm> 

		<inicializa>
		<conectaEventos>	

		self.signal_connect('destroy') do |widget, data|
		  destroy(widget, data)
		end
	end

	def destroy(widget, data)
		
	end

	def mostrar()
		<conteudoMostrar>
	end

	<metodos>
end
