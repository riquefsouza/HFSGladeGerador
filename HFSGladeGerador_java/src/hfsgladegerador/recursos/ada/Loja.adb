	function modelo_<objStore> return Gtk_Tree_Model
	is
		modelo<objStore>: Gtk_Tree_Model;
		<objStore>: Gtk_Tree_Store;
		iter: Gtk_Tree_Iter := Null_Iter;
		parentIter: Gtk_Tree_Iter := Null_Iter;
	begin
		Gtk_New(<objStore>, (<tiposStore>));
		modelo<objStore> := <objStore2>;
	
		<StoreAddValores>		
		return modelo<objStore>;
	end modelo_<objStore>;
	