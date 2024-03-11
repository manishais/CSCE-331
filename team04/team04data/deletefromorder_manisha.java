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
            orders_string += " " + orders.get(i).getOrderID();
            for(int j = 1; j <= 3-(orders.get(i).getOrderID()+"").length(); j++) {
                orders_string += " ";
            }
            orders_string += "   " + orders.get(i).getMenuItem();
            for(int j = 1; j <= 46-orders.get(i).getMenuItem().length(); j++) {
                orders_string += " ";
            }
            orders_string += "     " + orders.get(i).getPrice() + "\n";
        }
        orders_text.setText(orders_string);
    }
    orders_text.setText(employees_string);
    del_emp.setText("");
    orders.remove(del_index);
}
catch (Exception error) {
    // handle SQL exception
    error.printStackTrace();
}
close();