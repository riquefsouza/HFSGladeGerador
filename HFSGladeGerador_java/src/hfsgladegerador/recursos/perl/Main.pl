#!/usr/bin/perl -w -- 

use strict;
use Carp;
use Glib qw(TRUE FALSE);
use Gtk2;
use Gtk2::Pango;

package <nomeProjeto>;

use Rotinas;
<declaracaoForms>

sub <nomeProjeto> {
	Gtk2->init;

	$Rotinas::Rotinas = Rotinas->new();
	$<classePrincipal>::<objPrincipal> = <classePrincipal>->new();
	$<classePrincipal>::<objPrincipal>->show();

	Gtk2->main;	
}

<nomeProjeto>();

__END__
