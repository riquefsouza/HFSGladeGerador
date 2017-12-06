#ifndef RESOURCE_H
#define RESOURCE_H
// ---------------------------------------------------------------------------
	#include <sys/types.h>
	#include <sys/stat.h>
	#include <stdio.h>
	#include <stdlib.h>
	#include <string.h>
	#include <time.h>

	#include <gtk/gtk.h>
	#include <gdk/gdkkeysyms.h>
// ---------------------------------------------------------------------------
	#ifdef HAVE_CONFIG_H
		#include "config.h"
	#endif

	#ifdef G_OS_WIN32
		#include <windows.h>
	#else
		#include <unistd.h>
	#endif
// ---------------------------------------------------------------------------
	/* macros gettext padroes. */
	#ifdef ENABLE_NLS
	  #include <libintl.h>
	  #undef _
	  #define _(String) dgettext (PACKAGE, String)
	  #ifdef gettext_noop
		#define N_(String) gettext_noop (String)
	  #else
		#define N_(String) (String)
	  #endif
	#else
	  #define textdomain(String) (String)
	  #define gettext(String) (String)
	  #define dgettext(Domain,Message) (Message)
	  #define dcgettext(Domain,Message,Type) (Message)
	  #define bindtextdomain(Domain,Directory) (Domain)
	  #define _(String) (String)
	  #define N_(String) (String)
	#endif
// ---------------------------------------------------------------------------
	#define STR_NCPREPSQL	"Não conseguiu preparar o SQL!"
	#define STR_NCEXECSQL	"Não conseguiu executar o SQL!"
// ---------------------------------------------------------------------------
#endif
