use strict;
package <classeForm>;

use Glib qw(TRUE FALSE);
use Gtk2;

<declaracaoForms>

our $<objForm> = undef;

sub new {
	my( $self, $parent ) = @_;
	$parent = undef unless defined $parent;

	<inicializa>
	<conectaEventos>
    
    $self->signal_connect(destroy => \&on_destroy, $self);
		
	return $self;
}

sub on_destroy {
 my( $self, $parent ) = @_;


}


1;
