<?php

define('STR_NCONBANCO','Não consigo conectar com o banco de dados!');
define('STR_NALOCODBC','Não foi possível alocar um manuseador para o ambiente ODBC');
define('STR_NCPREPSQL','Não conseguiu preparar o SQL!');
define('STR_NCEXECSQL','Não conseguiu executar o SQL!');	

class Rotinas
{
	private $dbConexao;

	function __construct()
	{
		//parent::__construct();			
	}
	
	function MsgDlg($sMensagem, $tipo) {
		$dialog = new GtkMessageDialog(null, Gtk::DIALOG_MODAL, $tipo, Gtk::BUTTONS_OK, $sMensagem);	
		$dialog->set_position(Gtk::WIN_POS_CENTER);
		$dialog->set_has_separator(false);
		$dialog->run();
		$dialog->destroy();
	}
	
	function ConectaBanco($sFonteDeDados, $sLogin, $sSenha) {
		try {
			$this->dbConexao = new PDO($sFonteDeDados);
			return true;
		} catch (PDOException $ex){
			print "ERRO [Rotinas.ConectaBanco]: " . $ex->getMessage();
			return false;
		}
	}

	function DisconectaBanco() {
		$this->dbConexao = null;
	}

	function ConsultaDados($sSql){		
		try {
			$stmt = $this->dbConexao->query($sSql);

			if($stmt) {
				/*
				foreach($stmt as $linha){
					print $linha['codigo']
				}				
				while ($linha = $stmt->fetch(PDO::FETCH_OBJ)){
					print $linha->codigo;
				}				
				*/
				if ($stmt->fetchObject()) 
					return true;
				else
					return false;	
					
			} else {
				return false;	
			}
		} catch (PDOException $ex){
			print "ERRO [Rotinas.ConsultaDados]: " . $ex->getMessage();
			return false;
		}
	}

	function AtualizaDados($sSql){		
		try {
			return $this->dbConexao->exec($sSql);
		} catch (PDOException $ex){
			print "ERRO [Rotinas.AtualizaDados]: " . $ex->getMessage();
			return null;
		}
	}

	<MetodosObjetos>
}

?>
