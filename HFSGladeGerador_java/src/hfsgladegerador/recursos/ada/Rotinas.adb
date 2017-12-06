with Glib; use Glib;
with Glib.Object; use Glib.Object;
with Glib.Values; use Glib.Values;
with Gtkada.Intl; use Gtkada.Intl;
with Gtk.Enums; use Gtk.Enums;
with Gtk.Message_Dialog; use Gtk.Message_Dialog;

package body Rotinas is

   procedure Destroy_Window(Win : access Gtk.Window.Gtk_Window_Record'Class;
                            Ptr : Gtk_Window_Access)
   is
      pragma Unreferenced (Win);
   begin
      Ptr.all := null;
   end Destroy_Window;

   procedure Destroy_Dialog(Win : access Gtk.Dialog.Gtk_Dialog_Record'Class;
                            Ptr : Gtk_Dialog_Access)
   is
      pragma Unreferenced (Win);
   begin
      Ptr.all := null;
   end Destroy_Dialog;
   
   procedure MsgDlg(sMensagem: String; tipo: Gtk_Message_Type)
   is
      pragma Suppress(All_Checks);      
      dialog: Gtk_Message_Dialog;
      retorno: Gtk_Response_Type;
   begin
		dialog := Gtk_Message_Dialog_New(Parent   => null,
									   Flags    => Destroy_With_Parent,
									   The_Type => tipo,
									   Buttons  => Buttons_Ok,
									   Message  =>  "%s", 
									   Arg5     => sMensagem'Address);
   	    dialog.Set_Position(Win_Pos_Center);
   	    retorno := dialog.Run;
		dialog.Destroy;
   end MsgDlg;

	<MetodosObjetos>
end Rotinas;
