<?php

class <classeForm> extends <classePaiForm>
{
	<declaracaoObjetos>

	function __construct($Rotinas)
	{
		parent::__construct(<construtorPaiForm>);
		parent::connect("destroy",array(&$this,"destroy"));

		$this->Rotinas = $Rotinas;
		
		<inicializa>
		<conectaEventos>
			

		$object['FrmPrincipal'] = parent;
		return $object;
	}
	
	public function destroy(){
		$this->hide();
	}

	public function mostrar(){
		<conteudoMostrar>
	}
	
	<metodos>
}

?>
