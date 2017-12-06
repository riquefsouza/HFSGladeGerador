using Gtk;
using System;
using System.Collections;
using System.Data;
using System.Data.Odbc;

namespace <nomeProjeto>.comum
{
	public class Rotinas
	{
		private static Rotinas instancia;
		public OdbcConnection dbConexao;
		
		private Rotinas()
		{
			dbConexao = new OdbcConnection();
		}
		
		public static Rotinas getInstancia()
		{
			if (instancia == null)
			{
				instancia = new Rotinas();
			}
			return instancia;
		}
		
		public void MsgDlg(string sMensagem, MessageType tipo)
		{
			MessageDialog dialog = new MessageDialog(null,
						 DialogFlags.DestroyWithParent, tipo, ButtonsType.Ok, sMensagem);
			dialog.SetPosition(WindowPosition.Center);
			dialog.Run();
			dialog.Destroy();
		}
		
		public void ConectaBanco(string sFonteDeDados, string sLogin, string sSenha) {
			dbConexao.ConnectionString = sFonteDeDados;
			dbConexao.Open();
		}
		
		public void DisconectaBanco() {
			dbConexao.Close();
		}
		
		public bool ConsultaDados(OdbcCommand Consulta, string sTextoSql)
		{
			bool nTemLinhas = false;
			
			Consulta = dbConexao.CreateCommand();
			Consulta.CommandText = sTextoSql;
			
			OdbcDataReader Leitor = Consulta.ExecuteReader();
			nTemLinhas = Leitor.Read();
			Leitor.Close();
			return nTemLinhas;
			
		}
		
		<MetodosObjetos>
	}
}
