public sub Rotinas_<objStore>(arvore as GtkTreeView ptr)
dim modelo_<objStore> as GtkTreeModel ptr
dim <objStore> as <declaracaoClasseStore> ptr

	<objStore> = <classeStore>_new(<qtdTipos>, <tiposStore>)
	modelo_<objStore> = GTK_TREE_MODEL(<objStore>)
	gtk_tree_view_set_model(arvore, modelo_<objStore>)
	g_object_unref(modelo_<objStore>)

	'Adicionar Valores a Store	
	<StoreAddValores>
end sub
