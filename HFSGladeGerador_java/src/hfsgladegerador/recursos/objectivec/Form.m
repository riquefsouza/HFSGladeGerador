#import "<classeForm>.h"
#import "Rotinas.h"
//---------------------------------------------------------------------------
@implementation <classeForm>

-(id)initWithArgCount:(int *)argc andArgVals:(char *[])argv
{
	//call parent class' init
	if (self = [super init]) {

		form = <classePaiForm><construtorPaiForm>;

		<inicializa>
		<conectaEventos>
	
		g_signal_connect(form, "destroy", G_CALLBACK(on_<classeForm>_destroy), NULL);
		gtk_widget_show_all(form);
	}

	<objForm> = self;
	return self;
}
//---------------------------------------------------------------------------
-(void)startGtkMainLoop
{
	gtk_main();
}
//---------------------------------------------------------------------------
-(void)Mostrar{
/*
	NSLog(@"Mostrar.\n");
	printf("Mostrar.\n");
*/	
	<conteudoMostrar>
}
//---------------------------------------------------------------------------
-(void)destroyWidget{
	<objForm> = NULL;

	if(GTK_IS_WIDGET (form))
	{	
		gtk_widget_destroy(form);
	}
}
//---------------------------------------------------------------------------
-(void)dealloc{
	[self destroyWidget];

	[super dealloc];
}
//---------------------------------------------------------------------------
void on_<classeForm>_destroy(GtkObject *object, gpointer user_data)
{
	/* [myMainWindow printSomething]; */
	gtk_main_quit();
}
//---------------------------------------------------------------------------
<metodos>
//---------------------------------------------------------------------------
@end
