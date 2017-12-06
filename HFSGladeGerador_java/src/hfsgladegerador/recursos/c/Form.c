#include "<classeForm>.h"
#include "Rotinas.h"
// ---------------------------------------------------------------------------
GtkWidget *<classeForm>_Criar()
{
	<classeForm>.<objForm> = <classePaiForm><construtorPaiForm>;

	<inicializa>
	<conectaEventos>
		
	g_signal_connect ((gpointer) <classeForm>.<objForm>, "destroy", 
		G_CALLBACK (on_<classeForm>_destroy), NULL);

	return <classeForm>.<objForm>;
}
// ---------------------------------------------------------------------------
void <classeForm>_Mostrar() 
{
    <conteudoMostrar>
}
// ---------------------------------------------------------------------------
void on_<classeForm>_destroy(GtkObject *object, gpointer user_data) {
	
}
// ---------------------------------------------------------------------------
<metodos>
// ---------------------------------------------------------------------------
