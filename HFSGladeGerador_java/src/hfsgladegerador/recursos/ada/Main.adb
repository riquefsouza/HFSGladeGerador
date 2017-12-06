with Gtk; use Gtk;
with Gtk.Main;
with Gtk.Widget; use Gtk.Widget;
with <classePrincipal>; use <classePrincipal>;

procedure <nomeProjeto> is
begin
	Gtk.Main.Init;

	Criar(<classePrincipal>.<objPrincipal>);
	<classePrincipal>.<objPrincipal>.Show_All;

	Gtk.Main.Main;
end <nomeProjeto>;
