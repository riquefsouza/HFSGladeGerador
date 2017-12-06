#include "resource.h"
#include "<nomeProjeto>.h"
// ---------------------------------------------------------------------------
int main(int argc, char * argv[]) {
  Gtk::Main kit(argc, argv);

  Rotinas = new TRotinas();
  <objPrincipal> = new <classePrincipal>();
  <objPrincipal>->show();

  Gtk::Main::run(*<objPrincipal>);

  return 0;
}
// ---------------------------------------------------------------------------
