use strict;
package Rotinas;

use Glib qw(TRUE FALSE);
use Gtk2;
use POSIX qw(strftime);
use DBI qw(:sql_types);
use DBD::ODBC;

our $Rotinas = undef;

my %atribs = ( dbConexao => undef );

sub new {
  my $self = shift;
  return $self;
}

sub atrib {
  my( $self, $atributo ) = @_;
  return $atribs{$atributo};
}

sub set_atrib {
  my( $self, $atributo, $valor ) = @_;
  $atribs{$atributo} = $valor;
}

sub MsgDlg {
	my( $self, $sMensagem, $tipo ) = @_;
	my $dialog = Gtk2::MessageDialog->new(undef, 'destroy-with-parent',
	                                       $tipo, 'ok', $sMensagem);	
	$dialog->set_position('GTK_WIN_POS_CENTER');
	$dialog->run;
	$dialog->destroy;
}

sub ConectaBanco {
my ($self, $sBancoDeDados, $sLogin, $sSenha) = @_;

  $atribs{'dbConexao'} = DBI->connect( $sBancoDeDados, $sLogin, $sSenha );

  if ($atribs{'dbConexao'}) {
    return $Resource::true;
  } else {
    MsgDlg($Resource::STR_NCONBANCO, 'error');
    return $Resource::false;
  }
}

sub DisconectaBanco {
  my $self = shift;

  $atribs{'dbConexao'}->disconnect();
}

sub AtualizaDados {
my( $self, $hBancoLocal, $sSql ) = @_;

  my $sth = $hBancoLocal->prepare( $sSql );
  if ($sth->execute()) {
    $sth->finish();
    return $Resource::true;
  } else {
    MsgDlg($Resource::STR_NCEXECSQL, 'error');
    $sth->finish();
    return $Resource::false;
  }
}

<MetodosObjetos>


1;
