package clean_code.patterns_java.abstract_fabric_gui;

public class WindowsGuiFabric implements GuiAbstractFabric {

    @Override
    public Button getButton() {
        return new WindowsButton();
    }

    @Override
    public Menu getMenu() {
        return new WindowsMenu();
    }

    @Override
    public Window getWindow() {
        return new WindowsWindow();
    }
}
