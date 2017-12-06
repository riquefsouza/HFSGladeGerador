<?php

	include_once 'Rotinas.class.php';
	<declaracaoForms>

	$Rotinas = new Rotinas;
	$<objPrincipal> = new <classePrincipal>($Rotinas);
	$<objPrincipal>->show_all();
	
	Gtk::Main();
	
?>
