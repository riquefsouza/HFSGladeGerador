#!/usr/bin/env ruby
module <nomeProjeto>

require 'gtk2'
require 'Rotinas'
<declaracaoForms>

$Rotinas = Rotinas.new
$<objPrincipal> = <classePrincipal>.new

$<objPrincipal>.show()

Gtk.main()

end
