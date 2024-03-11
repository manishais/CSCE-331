package application;

public class MenuItem {
	//variables
    private int menu_itemID = 0;
    private String menu_item = "";
    private float price = 0.0f;

    //initializer
    public MenuItem(int menu_itemID, String menu_item, float price) {
        this.menu_itemID = menu_itemID;
        this.menu_item = menu_item;
        this.price = price;
    }

    //getters
    public int getMenu_ItemID() {
        return menu_itemID;
    }
    public String getMenu_Item() {
        return menu_item;
    }
    public float getPrice() {
        return price;
    }
    
  //setters
    public void setMenu_ItemID(int menu_itemID) {
        this.menu_itemID = menu_itemID;
    }
    public void setMenu_Item(String menu_item) {
        this.menu_item =  menu_item;
    }
    public void setPrice(float price) {
        this.price = price;
    }
}
