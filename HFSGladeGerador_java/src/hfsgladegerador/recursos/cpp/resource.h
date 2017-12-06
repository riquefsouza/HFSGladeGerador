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

	#ifdef G_OS_WIN32
		#include <windows.h>
	#else
		#include <unistd.h>
	#endif

	#include <gdkmm.h>
	#include <gtkmm.h>

	#define STR_NCPREPSQL	"Não conseguiu preparar o SQL!"
	#define STR_NCEXECSQL	"Não conseguiu executar o SQL!"
// ---------------------------------------------------------------------------
#endif
