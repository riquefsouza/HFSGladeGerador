#import "<nomeProjeto>.h"
#import "<classePrincipal>.h"
#import <Foundation/NSAutoreleasePool.h>
//---------------------------------------------------------------------------
int main(int argc, char *argv[]) {
	NSAutoreleasePool * pool = [[NSAutoreleasePool alloc] init];

	gtk_init(&argc, &argv);

	<classePrincipal> *<objPrincipal> = [[<classePrincipal> alloc] initWithArgCount:&argc andArgVals:argv];

	[<objPrincipal> startGtkMainLoop];
	[<objPrincipal> release];
	[pool release];

	return 0;
}
//---------------------------------------------------------------------------
