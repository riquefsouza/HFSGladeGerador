#import <gtk/gtk.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSString.h>
//---------------------------------------------------------------------------
id <objForm>;
//---------------------------------------------------------------------------
@interface <classeForm> : NSObject
{
	GtkWidget *form;
	<declaracaoObjetos>
}
//---------------------------------------------------------------------------
-(id)initWithArgCount:(int *)argc andArgVals:(char *[])argv;
-(void)destroyWidget;
-(void)startGtkMainLoop;
-(void)Mostrar;
//---------------------------------------------------------------------------
/*
 * C callback functions
 */
//---------------------------------------------------------------------------
void on_<classeForm>_destroy(GtkObject *object, gpointer user_data);
//---------------------------------------------------------------------------
@end
