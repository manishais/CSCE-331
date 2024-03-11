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
