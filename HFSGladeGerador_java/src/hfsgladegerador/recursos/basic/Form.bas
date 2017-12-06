dim shared <objForm> as GtkWidget ptr
------------------------------------------------------------------------------
declare function <classeForm>_Criar as GtkWidget ptr
declare procedure <classeForm>_Mostrar
declare sub on_<classeForm>_destroy(widget as GtkWidget ptr, byval user_data as gpointer ptr)
<declaracaoMetodos>
------------------------------------------------------------------------------
public function <classeForm>_Criar as GtkWidget ptr
	<declaracaoObjetos>

	<inicializa>
	<conectaEventos>
  
	gtk_signal_connect(GTK_OBJECT(<objForm>), "destroy", GTK_SIGNAL_FUNC(@on_<classeForm>_destroy), NULL)
  
	<classeForm>_Criar=<objForm>
end function
------------------------------------------------------------------------------
public sub <classeForm>_Mostrar
    <conteudoMostrar>
end sub
------------------------------------------------------------------------------
public sub on_frmPrincipal_destroy(widget as GtkWidget ptr, byval user_data as gpointer ptr)

end sub
------------------------------------------------------------------------------
<metodos>
