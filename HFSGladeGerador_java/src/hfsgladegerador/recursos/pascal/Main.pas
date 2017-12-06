program <nomeProjeto>;

{$mode objfpc}{$H+}

uses
	{$IFDEF UNIX}{$IFDEF UseCThreads}
	cthreads,
	{$ENDIF}{$ENDIF}
	Classes, glib2, gtk2, URotinas<declaracaoForms>;

begin
	gtk_set_locale();
	gtk_init(@argc, @argv);

	<objPrincipal> := T<classePrincipal>.Create;
	<objPrincipal>.Mostrar;
	
	gtk_main(); 
end.
