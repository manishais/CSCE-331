package application;

public class InvItem {
	//variables
	private int inventoryID = 0;
    private String ingredient = null;
    private int amount = 0;
    private int capacity = 0;


    //initializer
    public InvItem(int inventoryID, String ingredient, int amount, int capacity) {
        this.inventoryID = inventoryID;
        this.ingredient = ingredient;
        this.amount = amount;
        this.capacity = capacity;
    }

    //getters
    public int getInventoryID() {
        return inventoryID;
    }
    public String getIngredient() {
        return ingredient;
    }
    public int getAmount() {
        return amount;
    }
    public int getCapacity() {
        return capacity;
    }
    
  //setters
    public void setInventoryID(int inventoryID) {
    	this.inventoryID = inventoryID;
    }
    public void setIngredient(String ingredient) {
    	this.ingredient = ingredient;
    }
    public void setAmount(int amount) {
    	this.amount = amount;
    }
    public void setCapacity(int capacity) {
    	this.capacity = capacity;
    }
}
