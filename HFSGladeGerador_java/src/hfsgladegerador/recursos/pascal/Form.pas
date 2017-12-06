unit U<classeForm>;

{$mode objfpc}{$H+}

interface

uses
	Classes, SysUtils, glib2, gtk2, gdk2;

type
 TFrmPrincipal = class
 private
	{ private declarations }
	form: PGtkWidget;
	procedure on_destroy(widget: PGtkWidget; data: pgpointer); cdecl;
 public
	{ public declarations }	
	constructor Create;
	destructor Destroy;
	function <classeForm>_Criar: PGtkWidget;
	procedure <classeForm>_Mostrar;
	procedure on_<classeForm>_destroy(widget: PGtkWidget; user_data: pgpointer);
	<declaracaoMetodos>  
end;

implementation

constructor T<classeForm>.Create;
begin
	<inicializa>
	<conectaEventos>
	gtk_signal_connect(PGTKOBJECT(form), 'destroy', GTK_SIGNAL_FUNC(@T<classeForm>.on_destroy), NULL);
end;

destructor T<classeForm>.Destroy;
begin
	gtk_widget_destroy(form);
end;

procedure T<classeForm>.on_destroy(widget: pGtkWidget; data: pgpointer); cdecl;
begin
  //gtk_main_quit();
end;

procedure <classeForm>_Mostrar;
begin
    <conteudoMostrar>
end;

<metodos>
end.
