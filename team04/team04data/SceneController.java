package application;
import java.io.IOException;
import java.util.ArrayList;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class SceneController {
	private Stage stage;
	private Scene scene;
	private AnchorPane root;
	
	private Connection conn = null;
	
	public static ArrayList<Order> orders = new ArrayList<Order>();
	public static ArrayList<InvItem> inventory = new ArrayList<InvItem>();
	public static ArrayList<Employee> employees = new ArrayList<Employee>();
	public static ArrayList<MenuItem> menu = new ArrayList<MenuItem>();
	
	String orders_string = "";
	String sales_string = "";
	String inventory_string = "";
	String employees_string = "";
	String menu_string = "";
	int count = 1;
	
	@FXML private Label orders_text = new Label("");
	@FXML private Label sales_text = new Label("");
	@FXML private Label inventory_text = new Label("");
	@FXML private Label employees_text = new Label("");
	@FXML private Label menu_text = new Label("");
	
	//order text fields
	@FXML private TextField del_ord = new TextField();
	@FXML private TextField upd_ord = new TextField();
	@FXML private TextField upd_food = new TextField();
	@FXML private TextField upd_total = new TextField();
	
	//inventory text fields
	@FXML private TextField del_inv = new TextField();
	@FXML private TextField upd_inv = new TextField();
	@FXML private TextField add_item = new TextField();
	@FXML private TextField upd_item = new TextField();
	@FXML private TextField add_quant = new TextField();
	@FXML private TextField upd_quant = new TextField();
	@FXML private TextField add_cap = new TextField();
	@FXML private TextField upd_cap = new TextField();
	
	//employees text fields
	@FXML private TextField del_emp = new TextField();
	@FXML private TextField upd_emp = new TextField();
	@FXML private TextField add_name = new TextField();
	@FXML private TextField upd_name = new TextField();
	@FXML private TextField add_status = new TextField();
	@FXML private TextField upd_status = new TextField();
	
	//menu text fields
	@FXML private TextField del_menu = new TextField();
	@FXML private TextField upd_menu = new TextField();
	@FXML private TextField add_dish = new TextField();
	@FXML private TextField upd_dish = new TextField();
	@FXML private TextField add_price = new TextField();
	@FXML private TextField upd_price = new TextField();
	
    
    public void menuScreen(ActionEvent e) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("menu.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,979,581);
		stage.setScene(scene);
		stage.show();
	}
    public void managerScreen(ActionEvent e) throws IOException {
		root = (AnchorPane)FXMLLoader.load(getClass().getResource("manager.fxml"));
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		scene = new Scene(root,783,482);
		stage.setScene(scene);
		stage.show();
	}
    
    public void loadManagerTables(MouseEvent e) {
		connect();
		
		//querying for sales table
		try {
		    Statement stmt = conn.createStatement();
		    String sqlStatement = "SELECT month, year, SUM(sale) as totalSale FROM orders GROUP BY month, year ORDER BY year ASC, month ASC";
		    ResultSet result = stmt.executeQuery(sqlStatement);
		    while (result.next()) {
		        int month = result.getInt("month");
		        int year = result.getInt("year");
		        float sale = result.getFloat("totalSale");
		        sales_string += month;
		    	for(int i = 1; i <= 39-(month+"").length(); i++) {
		    		sales_string += " ";
		    	}
		    	sales_string += year;
		    	for(int i = 1; i <= 31-(year+"").length(); i++) {
		    		sales_string += " ";
		    	}
		    	sales_string += sale + "\n";
		    }
	    	sales_text.setText(sales_string);
		}
		catch(Exception error) {
			error.printStackTrace();
        	System.err.println(error.getClass().getName()+": "+error.getMessage());
		}
		
        //querying for inventory table
		try {
		    Statement stmt = conn.createStatement();
		    String sqlStatement = "SELECT inventoryID, ingredient, amount, capacity FROM inventory";
		    ResultSet result = stmt.executeQuery(sqlStatement);
		    while (result.next()) {
		    	int inventoryID = result.getInt("inventoryID");
		        String ingredient = result.getString("ingredient");
		        int amount = result.getInt("amount");
		        int capacity = result.getInt("capacity");
		    	inventory_string += " " + inventoryID;
		    	for(int i = 1; i <= 3-(inventoryID+"").length(); i++) {
		    		inventory_string += " ";
		    	}
		    	inventory_string += "   " + ingredient;
		    	for(int i = 1; i <= 35-(ingredient+"").length(); i++) {
		    		inventory_string += " ";
		    	}
		    	inventory_string += "        " + amount;
		    	for(int i = 1; i <= 3-(amount+"").length(); i++) {
		    		inventory_string += " ";
		    	}
		    	inventory_string += "            " + capacity + "\n";
		    	//adding to inventory ArrayList
			    inventory.add(new InvItem(inventoryID, ingredient, amount, capacity));
		    }
		    inventory_text.setText(inventory_string);
		}
		catch(Exception error) {
			error.printStackTrace();
        	System.err.println(error.getClass().getName()+": "+error.getMessage());
		}
		
		//querying for employees table
		try {
		    Statement stmt = conn.createStatement();
		    String sqlStatement = "SELECT employeeID, name, status FROM employees";
		    ResultSet result = stmt.executeQuery(sqlStatement);
		    while (result.next()) {
		    	int employeeID = result.getInt("employeeID");
		        String name = result.getString("name");
		        String status = result.getString("status");
		        employees_string += " " + employeeID;
		    	for(int i = 1; i <= 3-(employeeID+"").length(); i++) {
		    		employees_string += " ";
		    	}
		    	employees_string += "   " + name;
		    	for(int i = 1; i <= 46-name.length(); i++) {
		    		employees_string += " ";
		    	}
		    	employees_string += "     " + status + "\n";
		    	//adding to employees ArrayList
			    employees.add(new Employee(employeeID, name, status));
		    }
	    	employees_text.setText(employees_string);
		}
		catch(Exception error) {
			error.printStackTrace();
        	System.err.println(error.getClass().getName()+": "+error.getMessage());
        }
		
		//querying for menu table
		try {
		    Statement stmt = conn.createStatement();
		    String sqlStatement = "SELECT menu_itemID, menu_item, price FROM menu";
		    ResultSet result = stmt.executeQuery(sqlStatement);
		    while (result.next()) {
		    	int menu_itemID = result.getInt("menu_itemID");
		        String menu_item = result.getString("menu_item");
		        float price = result.getFloat("price");
		        menu_string += " " + menu_itemID;
		    	for(int i = 1; i <= 3-(menu_itemID+"").length(); i++) {
		    		menu_string += " ";
		    	}
		    	menu_string += "   " + menu_item;
		    	for(int i = 1; i <= 51-menu_item.length(); i++) {
		    		menu_string += " ";
		    	}
		    	menu_string += "     " + price + "\n";
		    	//adding to menu ArrayList
		    	menu.add(new MenuItem(menu_itemID, menu_item, price));
		    }
		    menu_text.setText(menu_string);
		}
		catch(Exception error) {
			error.printStackTrace();
        	System.err.println(error.getClass().getName()+": "+error.getMessage());
        }
		
		close();
    }
    
    public void addOrderItem(MouseEvent e) {
    	//getting name of menu item
    	String name = ((Node)e.getSource()).getId();
		name = name.replaceAll("_", " ");
		
		connect();
        
        //querying the database for price
        try {
			Statement stmt = conn.createStatement();
		    String sqlStatement = "SELECT price FROM menu WHERE menu_item='" + name + "' LIMIT 1";
		    ResultSet result = stmt.executeQuery(sqlStatement);
		    while (result.next()) {
		    	float price = result.getFloat("price");
		    	orders_string += "  " + count + "   " + name;
		    	for(int i = 1; i <= 41-name.length(); i++) {
		    		orders_string += " ";
		    	}
		    	orders_string += "$" + price + "\n";
		    	orders_text.setText(orders_string);
		    	orders.add(new Order(count, name, price));
		    	count += 1;
		    }
        } catch(Exception error) {
	    	
	    }
        close();
    }
    public void deleteOrderItem(MouseEvent e) throws IOException {
    	try {
    	    //deleting menu item from orders array
    	    int orderID = Integer.parseInt(del_ord.getText());

    	    int del_index = 0;
    	    orders_string = "";
    	    for(int i = 0; i < orders.size(); i++) {
    	        if (orders.get(i).getOrderID() == orderID) {
    	            del_index = i;
    	        }
    	        else {
                	orders_string += "  " + orders.get(i).getOrderID() + "   " + orders.get(i).getMenuItem();
    		    	for(int j = 1; j <= 41-orders.get(i).getMenuItem().length(); j++) {
    		    		orders_string += " ";
    		    	}
    		    	orders_string += "$" + orders.get(i).getPrice() + "\n";
    	        }
    	    }
    	    orders_text.setText(orders_string);
	        orders.remove(del_index);
	        del_ord.setText("");
    	}
    	catch (Exception error) {
    	    // handle SQL exception
    	    error.printStackTrace();
    	}
    	close();
    }
    public void updateOrderItem(MouseEvent e) throws IOException {
        try {
        	//updating order item
        	int orderID = Integer.parseInt(upd_ord.getText());
        	String menu_item = upd_food.getText();
            float price = Float.parseFloat(upd_total.getText());
            
            orders_string = "";
            for(Order item : orders) {
            	if (item.getOrderID() == orderID) {
            		item.setMenuItem(menu_item);
            		item.setPrice(price);
            	}
        		//changing employee table GUI
            	orders_string += "  " + item.getOrderID() + "   " + item.getMenuItem();
		    	for(int i = 1; i <= 41-item.getMenuItem().length(); i++) {
		    		orders_string += " ";
		    	}
		    	orders_string += "$" + item.getPrice() + "\n";
            }
            orders_text.setText(orders_string);
            upd_ord.setText("");
            upd_food.setText("");
            upd_total.setText("");
        }
        catch (Exception error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    
    public void addInventoryItem(MouseEvent e) throws IOException {
        try {
        	connect();
        	
        	//inserting inventory item into database
            String ingredient = add_item.getText();
            int amount = Integer.parseInt(add_quant.getText());
            int capacity = Integer.parseInt(add_cap.getText());
            
            Statement stmt = conn.createStatement();
            String sqlStatement = "INSERT INTO inventory(ingredient, amount, capacity) VALUES('" + ingredient + "', " + amount + ", " + capacity + ")";
            stmt.executeUpdate(sqlStatement);
            
            //retrieving inventoryID number
            sqlStatement = "SELECT inventoryID FROM inventory WHERE ingredient='" + ingredient + "'";
            ResultSet result = stmt.executeQuery(sqlStatement);
            
            while (result.next()) {
            	//changing inventory table GUI
		    	int inventoryID = result.getInt("inventoryID");
		    	inventory_string += " " + inventoryID;
		    	for(int i = 1; i <= 3-(inventoryID+"").length(); i++) {
		    		inventory_string += " ";
		    	}
		    	inventory_string += "   " + ingredient;
		    	for(int i = 1; i <= 35-(ingredient+"").length(); i++) {
		    		inventory_string += " ";
		    	}
		    	inventory_string += "        " + amount;
		    	for(int i = 1; i <= 3-(amount+"").length(); i++) {
		    		inventory_string += " ";
		    	}
		    	inventory_string += "            " + capacity + "\n";
		    	//adding to inventory ArrayList
			    inventory.add(new InvItem(inventoryID, ingredient, amount, capacity));
		    }
	    	inventory_text.setText(inventory_string);
	    	add_item.setText("");
	    	add_quant.setText("");
	    	add_cap.setText("");
        }
        catch (SQLException error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    public void deleteInventoryItem(MouseEvent e) throws IOException {
        try {
            connect();

            //deleting inventory item in database
            int inventoryID = Integer.parseInt(del_inv.getText());

            Statement stmt = conn.createStatement();
            String sqlStatement = "DELETE FROM inventory WHERE inventoryID=" + inventoryID;
            stmt.executeUpdate(sqlStatement);

            int del_index = 0;
            inventory_string = "";
            for(int i = 0; i < inventory.size(); i++) {
                if (inventory.get(i).getInventoryID() == inventoryID) {
                    del_index = i;
                }
                else {
                    //changing inventory table GUI
                    inventory_string += " " + inventory.get(i).getInventoryID();
                    for(int j = 1; j <= 3-(inventory.get(i).getInventoryID()+"").length(); j++) {
                        inventory_string += " ";
                    }
                    inventory_string += "   " + inventory.get(i).getIngredient();
                    for(int j = 1; j <= 35-(inventory.get(i).getIngredient()+"").length(); j++) {
                        inventory_string += " ";
                    }
                    inventory_string += "        " + inventory.get(i).getAmount();
                    for(int j = 1; j <= 3-(inventory.get(i).getAmount()+"").length(); j++) {
                        inventory_string += " ";
                    }
                    inventory_string += "            " + inventory.get(i).getCapacity() + "\n";
                }
            }
            inventory.remove(del_index);
            inventory_text.setText(inventory_string);
            del_inv.setText("");
        }
        catch (SQLException error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    public void updateInventoryItem(MouseEvent e) throws IOException {
        try {
        	connect();
        	
        	//updating inventory item from database
        	int inventoryID = Integer.parseInt(upd_inv.getText());
        	String ingredient = upd_item.getText();
            int amount = Integer.parseInt(upd_quant.getText());
            int capacity = Integer.parseInt(upd_cap.getText());
            
            Statement stmt = conn.createStatement();
            String sqlStatement = "UPDATE inventory SET ingredient='" + ingredient + "', amount=" + amount + ", capacity=" + capacity + " WHERE inventoryID=" + inventoryID;
            stmt.executeUpdate(sqlStatement);
            
            inventory_string = "";
            for(InvItem item : inventory) {
            	if (item.getInventoryID() == inventoryID) {
            		item.setIngredient(ingredient);
            		item.setAmount(amount);
            		item.setCapacity(capacity);
            	}
        		//changing inventory table GUI
		    	inventory_string += " " + item.getInventoryID();
		    	for(int i = 1; i <= 3-(item.getInventoryID()+"").length(); i++) {
		    		inventory_string += " ";
		    	}
		    	inventory_string += "   " + item.getIngredient();
		    	for(int i = 1; i <= 35-(item.getIngredient()+"").length(); i++) {
		    		inventory_string += " ";
		    	}
		    	inventory_string += "        " + item.getAmount();
		    	for(int i = 1; i <= 3-(item.getAmount()+"").length(); i++) {
		    		inventory_string += " ";
		    	}
		    	inventory_string += "            " + item.getCapacity() + "\n";
            }
            inventory_text.setText(inventory_string);
            upd_inv.setText("");
            upd_item.setText("");
            upd_quant.setText("");
            upd_cap.setText("");
        }
        catch (SQLException error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    
    public void addEmployeeItem(MouseEvent e) throws IOException {
        try {
        	connect();
        	
        	//inserting employee item into database
            String name = add_name.getText();
            String status = add_status.getText();
            
            Statement stmt = conn.createStatement();
            String sqlStatement = "INSERT INTO employees(name, status) VALUES('" + name + "', '" + status + "')";
            stmt.executeUpdate(sqlStatement);
            
            //retrieving employeeID number
            sqlStatement = "SELECT employeeID FROM employees WHERE name='" + name + "'";
            ResultSet result = stmt.executeQuery(sqlStatement);
            
            while (result.next()) {
            	//changing inventory table GUI
		    	int employeeID = result.getInt("employeeID");
            	employees_string += " " + employeeID;
		    	for(int i = 1; i <= 3-(employeeID+"").length(); i++) {
		    		employees_string += " ";
		    	}
		    	employees_string += "   " + name;
		    	for(int i = 1; i <= 46-name.length(); i++) {
		    		employees_string += " ";
		    	}
		    	employees_string += "     " + status + "\n";
		    	//adding to employees ArrayList
			    employees.add(new Employee(employeeID, name, status));
		    }
	    	employees_text.setText(employees_string);
	    	add_name.setText("");
	    	add_status.setText("");
        }
        catch (SQLException error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    public void deleteEmployeeItem(MouseEvent e) throws IOException {
        try {
        	connect();
        	
        	//deleting inventory item in database
        	int employeeID = Integer.parseInt(del_emp.getText());
        	
            Statement stmt = conn.createStatement();
            String sqlStatement = "DELETE FROM employees WHERE employeeID=" + employeeID;
            stmt.executeUpdate(sqlStatement);
            
            int del_index = 0;
            employees_string = "";
            for(int i = 0; i < employees.size(); i++) {
            	System.out.print(i + ": ");
            	if (employees.get(i).getEmployeeID() == employeeID) {
            		del_index = i;
            	}
            	else {
            		System.out.println("1");
                	employees_string += " " + employees.get(i).getEmployeeID();
                	System.out.println("2");
    		    	for(int j = 1; j <= 3-(employees.get(i).getEmployeeID()+"").length(); j++) {
    		    		employees_string += " ";
    		    	}
    		    	System.out.println("3");
    		    	employees_string += "   " + employees.get(i).getName();
    		    	System.out.println("4");
    		    	for(int j = 1; j <= 46-employees.get(i).getName().length(); j++) {
    		    		employees_string += " ";
    		    	}
    		    	System.out.println("5");
    		    	employees_string += "     " + employees.get(i).getStatus() + "\n";
    		    	System.out.println("6");
    		    }
            }
            employees_text.setText(employees_string);
            del_emp.setText("");
            employees.remove(del_index);
        }
        catch (SQLException error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    public void updateEmployeeItem(MouseEvent e) throws IOException {
        try {
        	connect();
        	
        	//updating employee item from database
        	int employeeID = Integer.parseInt(upd_emp.getText());
        	String name = upd_name.getText();
            String status = upd_status.getText();
            
            Statement stmt = conn.createStatement();
            String sqlStatement = "UPDATE employees SET name='" + name + "', status='" + status + "' WHERE employeeID=" + employeeID;
            stmt.executeUpdate(sqlStatement);
            
            employees_string = "";
            for(Employee item : employees) {
            	if (item.getEmployeeID() == employeeID) {
            		item.setName(name);
            		item.setStatus(status);
            	}
        		//changing employee table GUI
		    	employees_string += " " + item.getEmployeeID();
		    	for(int i = 1; i <= 3-(item.getEmployeeID()+"").length(); i++) {
		    		employees_string += " ";
		    	}
		    	employees_string += "   " + item.getName();
		    	for(int i = 1; i <= 46-(item.getName()+"").length(); i++) {
		    		employees_string += " ";
		    	}
		    	employees_string += "        " + item.getStatus() + "\n";
            }
            employees_text.setText(employees_string);
            upd_name.setText("");
            upd_status.setText("");
            upd_emp.setText("");
        }
        catch (SQLException error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    
    public void addMenuItem(MouseEvent e) throws IOException {
        try {
        	connect();
        	
        	//inserting menu item into database
            String menu_item = add_dish.getText();
            float price = Float.parseFloat(add_price.getText());
            
            Statement stmt = conn.createStatement();
            String sqlStatement = "INSERT INTO menu(menu_item, price) VALUES('" + menu_item + "', " + price + ")";
            stmt.executeUpdate(sqlStatement);
            
            //retrieving menu_itemID number
            sqlStatement = "SELECT menu_itemID FROM menu WHERE menu_item='" + menu_item + "'";
            ResultSet result = stmt.executeQuery(sqlStatement);
            
            while (result.next()) {
            	//changing menu table GUI
		    	int menu_itemID = result.getInt("menu_itemID");
            	menu_string += " " + menu_itemID;
		    	for(int i = 1; i <= 3-(menu_itemID+"").length(); i++) {
		    		menu_string += " ";
		    	}
		    	menu_string += "   " + menu_item;
		    	for(int i = 1; i <= 51-menu_item.length(); i++) {
		    		menu_string += " ";
		    	}
		    	menu_string += "     " + price + "\n";
		    	//adding to menu ArrayList
			    menu.add(new MenuItem(menu_itemID, menu_item, price));
		    }
	    	menu_text.setText(menu_string);
	    	add_dish.setText("");
	    	add_price.setText("");
        }
        catch (SQLException error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    public void deleteMenuItem(MouseEvent e) throws IOException {
        try {
            connect();

            //deleting menu item in database
            int menu_itemID = Integer.parseInt(del_menu.getText());

            Statement stmt = conn.createStatement();
            String sqlStatement = "DELETE FROM menu WHERE menu_itemID=" + menu_itemID;
            stmt.executeUpdate(sqlStatement);

            menu_string = "";
            int del_index = 0;
            for(int i = 0; i < menu.size(); i++) {
                if (menu.get(i).getMenu_ItemID() == menu_itemID) {
                    del_index = i;
                }
                else {
                    menu_string += " " + menu.get(i).getMenu_ItemID();
                    for(int j = 1; j <= 3-(menu.get(i).getMenu_ItemID()+"").length(); j++) {
                        menu_string += " ";
                    }
                    menu_string += "   " + menu.get(i).getMenu_Item();
                    for(int j = 1; j <= 51-menu.get(i).getMenu_Item().length(); j++) {
                        menu_string += " ";
                    }
                    menu_string += "     " + menu.get(i).getPrice() + "\n";
                }
            }
            menu_text.setText(menu_string);
            del_menu.setText("");
            menu.remove(del_index);
        }
        catch (SQLException error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    public void updateMenuItem(MouseEvent e) throws IOException {
        try {
        	connect();
        	
        	//updating employee item from database
        	int menu_itemID = Integer.parseInt(upd_menu.getText());
        	String menu_item = upd_dish.getText();
            float price = Float.parseFloat(upd_price.getText());
            
            Statement stmt = conn.createStatement();
            String sqlStatement = "UPDATE menu SET menu_item='" + menu_item + "', price=" + price + " WHERE menu_itemID=" + menu_itemID;
            stmt.executeUpdate(sqlStatement);
            
            menu_string = "";
            for(MenuItem item : menu) {
            	if (item.getMenu_ItemID() == menu_itemID) {
            		item.setMenu_Item(menu_item);
            		item.setPrice(price);
            	}
        		//changing employee table GUI
		    	menu_string += " " + item.getMenu_ItemID();
		    	for(int i = 1; i <= 3-(item.getMenu_ItemID()+"").length(); i++) {
		    		menu_string += " ";
		    	}
		    	menu_string += "   " + item.getMenu_Item();
		    	for(int i = 1; i <= 51-(item.getMenu_Item()+"").length(); i++) {
		    		menu_string += " ";
		    	}
		    	menu_string += "     " + item.getPrice() + "\n";
            }
            menu_text.setText(menu_string);
            upd_menu.setText("");
            upd_dish.setText("");
            upd_price.setText("");
        }
        catch (SQLException error) {
            // handle SQL exception
            error.printStackTrace();
        }
        close();
    }
    
    public void tender(MouseEvent e) throws IOException {
        try {
        	connect();
        	
        	//getting time
            LocalDateTime currentTime = LocalDateTime.now();
            int year = currentTime.getYear();
            int month = currentTime.getMonthValue();
            int day = currentTime.getDayOfMonth();
            
            //getting hour
            int military = currentTime.getHour();
            String period;
            if (military >= 12) {
                period = "PM";
                if (military > 12) {
                    military-= 12;
                }
            } else {
                period = "AM";
                if (military == 0) {
                    military = 12;
                }
            }
            String hour = military + period;
            
            // week will be 105 for now 
            int week = 105;
            
            //create a random number between 1 and 1000
            Statement stmt = conn.createStatement();
            int orderID = 1;
            try {
            	System.out.println("made it");
                String sqlQuery = "SELECT MAX(orderID) AS max_order FROM orders WHERE week = 0 AND day = 27";
                ResultSet result = stmt.executeQuery(sqlQuery);
                
                while (result.next()) {
                    orderID = result.getInt("max_order"); // Retrieve the value of the first column (MAX(orderID))
                }
                
            } catch (SQLException error1) {
                error1.printStackTrace(); // Handle exceptions appropriately
            }
            
            // SQL query to get the maximum orderID from the orders table
            
          
            
            // Iterate through each order and insert it into the database
            for (Order order : orders) {
                String menuItem = order.getMenuItem();
                float price = order.getPrice();
            	System.out.println(menuItem + ", " + price);
                String sqlStatement = "INSERT INTO orders VALUES ('" + hour + "', " + day + ", " + week + ", " + month + ", " + year + ", '" + menuItem + "', " + price + ", " + orderID + ")";
                
                stmt.executeUpdate(sqlStatement);
            }
            orders_text.setText("");
            orders.clear();
            count = 1;
        } catch (SQLException error) {
        	error.printStackTrace();
        }
    	close();
    }
    
    public void connect() {
    	//setting up database
		String database_name = "csce331_903_04_db";
        String database_user = "csce331_903_04_user";
        String database_password = "team04";
        String database_url = String.format("jdbc:postgresql://csce-315-db.engr.tamu.edu/%s", database_name);
        try {
          conn = DriverManager.getConnection(database_url, database_user, database_password);
        } catch (Exception error) {
        	error.printStackTrace();
        	System.err.println(error.getClass().getName()+": "+error.getMessage());
        	System.exit(0);
        }
    }
	public void close() {
		//closing the database
        try {
	        conn.close();
	    } catch(Exception error2) {
	    	System.out.println("Connection NOT Closed.");
	    }
	}
}

