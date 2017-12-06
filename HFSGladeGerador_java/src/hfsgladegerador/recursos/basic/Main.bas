#include once "gtk/gtk.bi"
#include once "vbcompat.bi"
------------------------------------------------------------------------------
#include once "Rotinas.bas"
<declaracaoForms>
------------------------------------------------------------------------------
sub main()  
	gtk_set_locale()
	gtk_init(0, NULL)

	<classePrincipal>_Criar
	gtk_widget_show(<objPrincipal>)

    gtk_main()
    end 0   
end sub
------------------------------------------------------------------------------
main()
