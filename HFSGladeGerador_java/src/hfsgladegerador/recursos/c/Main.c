#include "<nomeProjeto>.h"
#include "<classePrincipal>.h"
// ---------------------------------------------------------------------------
int main(int argc, char *argv[]) {

#ifdef G_OS_WIN32
    package_prefix = g_win32_get_package_installation_directory(NULL, NULL);
    package_datadir = g_strdup_printf("%s%s", package_prefix, "");
#endif

#ifdef ENABLE_NLS
  #ifdef G_OS_WIN32
     gchar *temp;

     temp = g_strdup_printf("%s%s", package_prefix, "/lib/locale");
     bindtextdomain(GETTEXT_PACKAGE, temp);
     g_free(temp);
  #else
     //bindtextdomain (GETTEXT_PACKAGE, PACKAGE_LOCALE_DIR);
     bindtextdomain (GETTEXT_PACKAGE, "/usr/local/share/locale");
  #endif
  bind_textdomain_codeset(GETTEXT_PACKAGE, "UTF-8");
  textdomain(GETTEXT_PACKAGE);
#endif
// ---------------------------------------------------------------------------
  gtk_set_locale();
  gtk_init(&argc, &argv);

  <classePrincipal>.<objPrincipal> = <classePrincipal>_Criar();

  gtk_widget_show(<classePrincipal>.<objPrincipal>);
  gtk_main();

// ---------------------------------------------------------------------------
#ifdef G_OS_WIN32
    g_free(package_prefix);
    g_free(package_datadir);
#endif
// ---------------------------------------------------------------------------
    return 0;
}
// ---------------------------------------------------------------------------
