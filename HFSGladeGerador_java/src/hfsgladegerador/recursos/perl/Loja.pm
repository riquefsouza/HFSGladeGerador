sub <objStore> {
	my $self = shift;
 
	$<objStore> = Gtk2::<classeStore>->new(<tiposStore>);
	my $iter = $<objStore>.append;
	<StoreAddValores>

	return <objStore>;
}
