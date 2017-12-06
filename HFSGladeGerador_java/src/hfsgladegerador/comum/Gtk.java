package hfsgladegerador.comum;

public enum Gtk {
	// Windows
	GtkWindow, //Toplevel which can contain other widgets
	GtkDialog, //Create popup windows
	GtkInputDialog,
	GtkMessageDialog, //A convenient message window
	GtkAboutDialog, //Display information about an application
	GtkAssistant, //A widget used to guide users through multi-step operations
	GtkInvisible, //A widget which is not displayed
	GtkOffscreenWindow, //A toplevel to manage offscreen rendering of child widgets
	GtkWindowGroup, //Limit the effect of grabs

	//Layout Containers
	GtkBox, //A container box
	GtkGrid, //Pack widgets in a rows and columns
	GtkRevealer, //Hide and show with animation
	GtkListBox, //A list container
	GtkFlowBox, //A container that allows reflowing its children
	GtkStack, //A stacking container
	GtkStackSwitcher, //A controller for GtkStack
	GtkStackSidebar, //An automatic sidebar widget
	GtkActionBar, //A full width bar for presenting contextual actions
	GtkHeaderBar, //A box with a centered child
	GtkOverlay, //A container which overlays widgets on top of each other
	GtkButtonBox, //A container for arranging buttons
	GtkPaned, //A widget with two adjustable panes
	GtkLayout, //Infinite scrollable area containing child widgets and/or custom drawing
	GtkNotebook, //A tabbed notebook container
	GtkExpander, //A container which can hide its child
	GtkOrientable, //An interface for flippable widgets
	GtkAspectFrame, //A frame that constrains its child to a particular aspect ratio
	GtkFixed, //A container which allows you to position widgets at fixed coordinates

	//Display Widgets
	GtkLabel, //A widget that displays a small to medium amount of text
	GtkImage, //A widget displaying an image
	GtkSpinner, //Show a spinner animation
	GtkInfoBar, //Report important messages to the user
	GtkProgressBar, //A widget which indicates progress visually
	GtkLevelBar, //A bar that can used as a level indicator
	GtkStatusbar, //Report messages of minor importance to the user
	GtkAccelLabel, //A label which displays an accelerator key on the right of the text

	//Buttons and Toggles
	GtkButton, //A widget that emits a signal when clicked on
	GtkCheckButton, //Create widgets with a discrete toggle button
	GtkRadioButton, //A choice from multiple check buttons
	GtkToggleButton, //Create buttons which retain their state
	GtkLinkButton, //Create buttons bound to a URL
	GtkMenuButton, //A widget that shows a popup when clicked on
	GtkSwitch, //A �light switch� style toggle
	GtkScaleButton, //A button which pops up a scale
	GtkVolumeButton, //A button which pops up a volume control
	GtkLockButton, //A widget to unlock or lock privileged operations
	GtkModelButton, //A button that uses a GAction as model

	//Numeric and Text Data Entry
	GtkEntry, //A single line text entry field
	GtkEntryBuffer, //Text buffer for GtkEntry
	GtkEntryCompletion, //Completion functionality for GtkEntry
	GtkScale, //A slider widget for selecting a value from a range
	GtkSpinButton, //Retrieve an integer or floating-point number from the user
	GtkSearchEntry, //An entry which shows a search icon
	GtkSearchBar, //A toolbar to integrate a search entry with
	GtkEditable, //Interface for text-editing widgets

	//Multiline Text Editor
	GtkTextIter, //Text buffer iterator
	GtkTextMark, //A position in the buffer preserved across buffer modifications
	GtkTextBuffer, //Stores attributed text for display in a GtkTextView
	GtkTextTag, //A tag that can be applied to text in a GtkTextBuffer
	GtkTextTagTable, //Collection of tags that can be used together
	GtkTextView, //Widget that displays a GtkTextBuffer

	//Tree, List and Icon Grid Widgets
	GtkTreeModel, //The tree interface used by GtkTreeView
	GtkTreeSelection, //The selection object for GtkTreeView
	GtkTreeViewColumn, //A visible column in a GtkTreeView widget
	GtkTreeView, //A widget for displaying both trees and lists
	GtkNodeView,
	GtkCellView, //A widget displaying a single row of a GtkTreeModel
	GtkIconView, //A widget which displays a list of icons in a grid
	GtkTreeSortable, //The interface for sortable models used by GtkTreeView
	GtkTreeModelSort, //A GtkTreeModel which makes an underlying tree model sortable
	GtkTreeModelFilter, //A GtkTreeModel which hides parts of an underlying tree model
	GtkCellLayout, //An interface for packing cells
	GtkCellArea, //An abstract class for laying out GtkCellRenderers
	GtkCellAreaBox, //A cell area that renders GtkCellRenderers into a row or a column
	GtkCellAreaContext, //Stores geometrical information for a series of rows in a GtkCellArea
	GtkCellRenderer, //An object for rendering a single cell
	GtkCellEditable, //Interface for widgets which can are used for editing cells
	GtkCellRendererAccel, //Renders a keyboard accelerator in a cell
	GtkCellRendererCombo, //Renders a combobox in a cell
	GtkCellRendererPixbuf, //Renders a pixbuf in a cell
	GtkCellRendererProgress, //Renders numbers as progress bars
	GtkCellRendererSpin, //Renders a spin button in a cell
	GtkCellRendererText, //Renders text in a cell
	GtkCellRendererToggle, //Renders a toggle button in a cell
	GtkCellRendererSpinner, //Renders a spinning animation in a cell
	GtkListStore, //A list-like data structure that can be used with the GtkTreeView
	GtkTreeStore, //A tree-like data structure that can be used with the GtkTreeView

	//Menus, Combo Box, Toolbar
	GtkComboBox, //A widget used to choose from a list of items
	GtkComboBoxText, //A simple, text-only combo box
	GtkMenu, //A menu widget
	GtkMenuBar, //A subclass of GtkMenuShell which holds GtkMenuItem widgets
	GtkMenuItem, //The widget used for item in menus
	GtkRadioMenuItem, //A choice from multiple check menu items
	GtkCheckMenuItem, //A menu item with a check box
	GtkSeparatorMenuItem, //A separator used in menus
	GtkToolShell, //Interface for containers containing GtkToolItem widgets
	GtkToolbar, //Create bars of buttons and other widgets
	GtkToolItem, //The base class of widgets that can be added to GtkToolShell
	GtkToolPalette, //A tool palette with categories
	GtkToolItemGroup, //A sub container used in a tool palette
	GtkSeparatorToolItem, //A toolbar item that separates groups of other toolbar items
	GtkToolButton, //A GtkToolItem subclass that displays buttons
	GtkMenuToolButton, //A GtkToolItem containing a button with an additional dropdown menu
	GtkToggleToolButton, //A GtkToolItem containing a toggle button
	GtkRadioToolButton, //A toolbar item that contains a radio button
	GtkPopover, //Context dependent bubbles
	GtkPopoverMenu, //Popovers to use as menus

	//Selector Widgets and Dialogs
	GtkColorChooser, //Interface implemented by widgets for choosing colors
	GtkColorButton, //A button to launch a color selection dialog
	GtkColorChooserWidget, //A widget for choosing colors
	GtkColorChooserDialog, //A dialog for choosing colors
	GtkFileChooser, //File chooser interface used by GtkFileChooserWidget and GtkFileChooserDialog
	GtkFileChooserButton, //A button to launch a file selection dialog
	GtkFileChooserDialog, //A file chooser dialog, suitable for �File/Open� or �File/Save� commands
	GtkFileChooserWidget, //A file chooser widget
	GtkFileFilter, //A filter for selecting a file subset
	GtkFontChooser, //Interface implemented by widgets displaying fonts
	GtkFontButton, //A button to launch a font chooser dialog
	GtkFontChooserWidget, //A widget for selecting fonts
	GtkFontChooserDialog, //A dialog for selecting fonts
	GtkPlacesSidebar, //Sidebar that displays frequently-used places in the file system

	//Ornaments
	GtkFrame, //A bin with a decorative frame and optional label
	GtkSeparator, //A separator widget

	//Scrolling
	GtkScrollbar, //A Scrollbar
	GtkScrolledWindow, //Adds scrollbars to its child widget
	GtkScrollable, //An interface for scrollable widgets

	//Printing
	GtkPrintOperation, //High-level Printing API
	GtkPrintContext, //Encapsulates context for drawing pages
	GtkPrintSettings, //Stores print settings
	GtkPageSetup, //Stores page setup information
	GtkPaperSize, //Support for named paper sizes
	GtkPrinter, //Represents a printer
	GtkPrintJob, //Represents a print job
	GtkPrintUnixDialog, //A print dialog
	GtkPageSetupUnixDialog, //A page setup dialog

	//Miscellaneous
	GtkAdjustment, //A representation of an adjustable bounded value
	GtkCalendar, //Displays a calendar and allows the user to select a date
	GtkDrawingArea, //A widget for custom user interface elements
	GtkGLArea, //A widget for custom drawing with OpenGL
	GtkEventBox, //A widget used to catch events for widgets which do not have their own window
	GtkHandleBox, //a widget for detachable window portions
	GtkIMContextSimple, //An input method context supporting table-based input methods
	GtkIMMulticontext, //An input method context supporting multiple, loadable input methods
	GtkSizeGroup, //Grouping widgets so they request the same size
	GtkTooltip, //Add tips to your widgets
	GtkViewport, //An adapter which makes widgets scrollable
	GtkAccessible, //Accessibility support for widgets

	//Abstract Base Classes
	GtkWidget, //Base class for all widgets
	GtkContainer, //Base class for widgets which contain other widgets
	GtkBin, //A container with just one child
	GtkMenuShell, //A base class for menu objects
	GtkRange, //Base class for widgets which visualize an adjustment
	GtkIMContext, //Base class for input method contexts

	//Cross-process Embedding
	GtkPlug, //Toplevel for embedding into other processes
	GtkSocket, //Container for widgets from other processes

	//Recently Used Documents
	GtkRecentManager, //Managing recently used files
	GtkRecentChooser, //Interface implemented by widgets displaying recently used files
	GtkRecentChooserDialog, //Displays recently used files in a dialog
	GtkRecentChooserMenu, //Displays recently used files in a menu
	GtkRecentChooserWidget, //Displays recently used files
	GtkRecentFilter, //A filter for selecting a subset of recently used files

	//Choosing from installed applications
	GtkAppChooser, //Interface implemented by widgets for choosing an application
	GtkAppChooserButton, //A button to launch an application chooser dialog
	GtkAppChooserDialog, //An application chooser dialog
	GtkAppChooserWidget, //Application chooser widget that can be embedded in other widgets

	//Gestures
	GtkEventController, //Self-contained handler of series of events
	GtkGesture, //Base class for gestures
	GtkGestureSingle, //Base class for mouse/single-touch gestures
	GtkGestureDrag, //Drag gesture
	GtkGestureLongPress, //"Press and Hold" gesture
	GtkGestureMultiPress, //Multipress gesture
	GtkGesturePan, //Pan gesture
	GtkGestureSwipe, //Swipe gesture
	GtkGestureRotate, //Rotate gesture
	GtkGestureZoom, //Zoom gesture

	//Deprecated
	GtkSymbolicColor, //Symbolic colors
	GtkGradient, //Gradients

	//Resource Files, //Deprecated routines for handling resource files
	GtkStyle, //Deprecated object that holds style information for widgets
	GtkHScale, //A horizontal slider widget for selecting a value from a range
	GtkVScale, //A vertical slider widget for selecting a value from a range
	GtkTearoffMenuItem, //A menu item used to tear off and reattach its menu
	GtkColorSelection, //Deprecated widget used to select a color
	GtkColorSelectionDialog, //Deprecated dialog box for selecting a color
	GtkHSV, //A color wheel widget
	GtkFontSelection, //Deprecated widget for selecting fonts
	GtkFontSelectionDialog, //Deprecated dialog box for selecting fonts
	GtkHBox, //A horizontal container box
	GtkVBox, //A vertical container box
	GtkHButtonBox, //A container for arranging buttons horizontally
	GtkVButtonBox, //A container for arranging buttons vertically
	GtkHPaned, //A container with two panes arranged horizontally
	GtkVPaned, //A container with two panes arranged vertically
	GtkTable, //Pack widgets in regular patterns
	GtkHSeparator, //A horizontal separator
	GtkVSeparator, //A vertical separator
	GtkHScrollbar, //A horizontal scrollbar
	GtkVScrollbar, //A vertical scrollbar
	GtkUIManager, //Constructing menus and toolbars from an XML description
	GtkActionGroup, //A group of actions
	GtkAction, //A deprecated action which can be triggered by a menu or toolbar item
	GtkToggleAction, //An action which can be toggled between two states
	GtkRadioAction, //An action of which only one in a group can be active
	GtkRecentAction, //An action of which represents a list of recently used files
	GtkActivatable, //An interface for activatable widgets
	GtkImageMenuItem, //A menu item with an icon
	GtkMisc, //Base class for widgets with alignments and padding

	//Stock Items, //Prebuilt common menu/toolbar items and corresponding icons
	GtkNumerableIcon, //A GIcon that allows numbered emblems
	GtkArrow, //Displays an arrow
	GtkStatusIcon, //Display an icon in the system tray
	GtkThemingEngine, //Theming renderers
	GtkAlignment, //A widget which controls the alignment and size of its child

	//Theming in GTK+
	GtkStyleContext, //Rendering UI elements
	GtkCssProvider, //CSS-like styling for widgets
	GtkStyleProvider, //Interface to provide style information to GtkStyleContext
	GtkStyleProperties, //Store for style property information
	GtkWidgetPath, //Widget path abstraction
	GtkIconTheme; //Looking up icons by name

//	TODAS, C, CPP, Javascript, Perl, Python, Vala, Ada, CSharp, VBNet, D, Euphoria, 
//	Fortran, FreeBASIC, Go, Guile, Haskell, Java, Lua, OCaml, Pascal, PHP, R, Ruby;
	
	public String toString(Linguagem linguagem){
		String str = "";
		
		if (linguagem.getTipo().equals(Linguagem.Tipo.C)){
			if (this.toString().equals(GtkHBox.toString()))
				str = "gtk_hbox";
			else if (this.toString().equals(GtkVBox.toString()))
				str = "gtk_vbox";
			else if	(this.toString().equals(GtkVPaned.toString()))
				str = "gtk_vpaned"; 
			else if	(this.toString().equals(GtkHPaned.toString()))
				str = "gtk_hpaned"; 
			else if	(this.toString().equals(GtkHButtonBox.toString()))
				str = "gtk_hbutton_box";			
			else	
				str = linguagem.getClasse(this.toString(), '_');
		} else if (linguagem.getTipo().equals(Linguagem.Tipo.Java)){
			return this.toString().substring(3);
		} else if (linguagem.getTipo().equals(Linguagem.Tipo.Ada)){
			if (this.toString().equals(GtkHBox.toString()))
				str = "Gtk_Hbox";
			else if (this.toString().equals(GtkVBox.toString()))
				str = "Gtk_Vbox";
			else if	(this.toString().equals(GtkVPaned.toString()))
				str = "Gtk_Vpaned"; 
			else if	(this.toString().equals(GtkHPaned.toString()))
				str = "Gtk_Hpaned"; 
			else if	(this.toString().equals(GtkHButtonBox.toString()))
				str = "Gtk_Hbutton_Box";			
			else if	(this.toString().equals(GtkStatusbar.toString()))
				str = "Gtk_Status_Bar";			
			else	
				str = linguagem.getClasse(this.toString(), '_');	
		} else {
		
			if (linguagem.getPrefixoGtk().length() > 0) {
				str = linguagem.getPrefixoGtk();
			} else {
				return this.toString();
			}
			
			str += this.toString().substring(3);
			
		}
		
		return str;
	}
		
	public static boolean isJanela(String classe){
		return classe.equals(GtkWindow.toString()) ||
		classe.equals(GtkDialog.toString()) ||
		classe.equals(GtkAboutDialog.toString()) ||
		classe.equals(GtkColorSelectionDialog.toString()) ||
		classe.equals(GtkFileChooserDialog.toString()) ||
		classe.equals(GtkFontSelectionDialog.toString()) ||
		classe.equals(GtkInputDialog.toString()) ||
		classe.equals(GtkMessageDialog.toString()) ||
		classe.equals(GtkRecentChooserDialog.toString()) ||
		classe.equals(GtkAssistant.toString());
	}	
	
}

