with Gtk.Handlers;
pragma Elaborate_All (Gtk.Handlers);
with Gtk.Widget; use Gtk.Widget;
with Gtk.Window; use Gtk.Window;
with Gtk.Dialog; use Gtk.Dialog;
with Gtk.Menu_Item; use Gtk.Menu_Item;
with Gtk.Button; use Gtk.Button;
with Gtk.Tree_View; use Gtk.Tree_View;
with Gtk.Notebook; use Gtk.Notebook;
with Gtk.Message_Dialog; use Gtk.Message_Dialog;
with Gtk.Tree_Model; use Gtk.Tree_Model;
with Gtk.List_Store; use Gtk.List_Store;
with Gtk.Tree_Store; use Gtk.Tree_Store;
with Gtk.Combo_Box; use Gtk.Combo_Box;

package Rotinas is

   package Widget_Callback is new Gtk.Handlers.Callback(Gtk_Widget_Record);
   package Widget_Return_Callback is new Gtk.Handlers.Return_Callback(Gtk_Widget_Record, Boolean);   
   package Window_Callback is new Gtk.Handlers.Callback(Gtk_Window_Record);
   package Dialog_Callback is new Gtk.Handlers.Callback(Gtk_Dialog_Record);
   package MenuItem_Callback is new Gtk.Handlers.Callback(Gtk_Menu_Item_Record);
   package Button_Callback is new Gtk.Handlers.Callback(Gtk_Button_Record);
   package TreeView_Callback is new Gtk.Handlers.Callback(Gtk_Tree_View_Record);
   package Notebook_Callback is new Gtk.Handlers.Callback(Gtk_Notebook_Record);   
   package ComboBox_Callback is new Gtk.Handlers.Callback(Gtk_Combo_Box_Record);

   type Gtk_Window_Access is access all Gtk_Window;
   procedure Destroy_Window(Win : access Gtk.Window.Gtk_Window_Record'Class;
                            Ptr : Gtk_Window_Access);

   type Gtk_Dialog_Access is access all Gtk_Dialog;
   procedure Destroy_Dialog(Win : access Gtk_Dialog_Record'Class;
                            Ptr : Gtk_Dialog_Access);
                            
   procedure MsgDlg(sMensagem: String; tipo: Gtk_Message_Type);

	<MetodosObjetos>
end Rotinas;
