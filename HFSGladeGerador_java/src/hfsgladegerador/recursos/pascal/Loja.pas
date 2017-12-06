{
	modelo_<objStore>: PGtkTreeModel;
	procedure Rotinas_<objStore>(arvore: PGtkTreeView);
}
procedure TRotinas.<objStore>(arvore: PGtkTreeView);
var <objStore>: <declaracaoClasseStore>;
begin
	//Criar Store	
	<objStore> := <classeStore>_new(<qtdTipos>, <tiposStore>);
	modelo_<objStore> := GTK_TREE_MODEL(<objStore>);
	gtk_tree_view_set_model(arvore, modelo_<objStore>);
	g_object_unref(modelo_<objStore>);

	//Adicionar Valores a Store	
	<StoreAddValores>
end;
