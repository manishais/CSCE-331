import java.sql.Statement;

public class Employees {
    private String name = null;
    private int id = 0;
    private String status = null;

    public Employees(String name, int id, String status) {
        this.name = name;
        this.id = id;
        this.status = status;
    }

    public String getName() {            // returns the name of each employee
        return name;
    }
    public int getid() {             // returns each employee id
        return id;
    }
    public String getStatus()          // returns whether the employee is a cashier or a manager
    {
        return status;
    }
}




@FXML private TableView<employees> employeeTable;
@FXML private TableColumn<employees, String> nameColumn;
@FXML private TableColumn<employees, int> idColumn;
@FXML private TableColumn<employees, String> statusColumn;

TableColumn<Employees, String> employeeTable = new TableColumn<>("Employees");
nameColumn.setCellValueFactory(new PropertyValueFactory<Employees, String>("Name"));
idColumn.setCellValueFactory(new PropertyValueFactory<Employees, int>("ID"));
statusColumn.setCellValueFactory(new PropertyValueFactory<Employees, String>("Status"));




try {
    Statement stmt = conn.createStatement();
    String sqlStatement = "SELECT * FROM employees";        // returns all the employees
    ResultSet result = stmt.executeQuery(sqlStatement);
    while (result.next()) {
        String name = result.getString("name");
        int id = result.getInt("id");
        String status = result.getString("status");
        employeeTable.getItems().add(new Employee(name, id, status));
    }

}

adding employee to database
try {
    Statement stmt = conn.createStatement();
    // String sqlStatement =  "INSERT INTO employees (name, id, status) VALUES ('John Doe', 1234, 'manager')";    // THIS WORKS IN SQL
   
    //Running a query
   String _name = "John Doe";
   int _id = 1234;
   String _status = "manager";
 
   String sqlStatement = "INSERT INTO orders VALUES ('" + _name + "', " + _id + ", " + _status + ")";
   stmt.executeUpdate(sqlStatement);
}
catch(Exception error) {}



// WHAT IT SHOULD LOOK LIKE
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