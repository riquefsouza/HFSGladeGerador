with System; use System;
with Gtk.Main;
with Gdk.Event; use Gdk.Event;
with Gtk.Accel_Group; use Gtk.Accel_Group;
with Gtk.Style; use Gtk.Style;
with Glib; use Glib;
with Gtk; use Gtk;
with Gdk.Types; use Gdk.Types;
with Gtk.Widget; use Gtk.Widget;
with Gtk.Enums; use Gtk.Enums;
with Gtkada.Handlers; use Gtkada.Handlers;
with Rotinas; use Rotinas;
with Gtk.Cell_Renderer_Text; use Gtk.Cell_Renderer_Text;
with Gtk.Tree_Store; use Gtk.Tree_Store;
with Gtk.Message_Dialog; use Gtk.Message_Dialog;
with Gdk.Window; use Gdk.Window;
<declaracaoForms>
package body <classeForm> is

	procedure Criar(<objForm>: out <classeForm>_Access) is
	begin
		<objForm> := new <objForm>_Record;
		Inicializa(<objForm>);
	end Criar;

	procedure Inicializa(<objForm>: access <classeForm>_Record'Class) is
		pragma Suppress(All_Checks);
		Num: Gint;
		pragma Unreferenced(Num);
		Text_Render: Gtk_Cell_Renderer_Text<construtorRender>;
	begin
		<classePai>.Initialize(<objForm><construtorPaiForm>);

		<inicializa>
		<conectaEventos>		
	end Inicializa;

	procedure Mostrar is
		retorno: Gtk_Response_Type;
		pragma Unreferenced(retorno);	
	begin
		<conteudoMostrar>
	end Mostrar;

	<metodos>

end <classeForm>;
