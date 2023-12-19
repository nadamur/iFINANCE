package se2203b.assignments.ifinance;

import java.sql.*;

public class GroupAdapter {
    Connection connection;

    public GroupAdapter(Connection conn, Boolean reset) throws SQLException {
        connection = conn;
        Statement stmt = connection.createStatement();
        if (reset) {
            try {
                // Remove tables if database tables have been created.
                // This will throw an exception if the tables do not exist
                stmt.execute("DROP TABLE Groups");
            } catch (SQLException ex) {
                // No need to report an error.
                // The table simply did not exist.
            }
        }
        try {
            // Create the table
            stmt.execute("CREATE TABLE Groups ("
                    + "id INT NOT NULL,"
                    + "name VARCHAR(60) NOT NULL,"
                    + "parent INT,"
                    + "element VARCHAR(50) NOT NULL"
                    + ")");
            initializeTable();
        } catch (SQLException ex) {
            // No need to report an error.
            // The table exists and may have some data.
        }
    }

    // adds new record, reads from Administrator object
    public void insertGroup(String name, int parent, String element) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO Groups ( id, name, parent, element) "
                + "VALUES ("
                + getNewID() + ", '"
                + name + "', "
                + parent + ", '"
                + element + "'"
                + ")"
        );
    }
    public boolean checkID(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        boolean found = false;
        rs = stmt.executeQuery("SELECT id FROM Groups");
        while(rs.next()){
            int i = rs.getInt(1);
            if(i == id){
                found = true;
            }
        }
        return found;
    }
    public int getNewID() throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT MAX(id) FROM Groups");
        rs.next();
        int max = rs.getInt(1)+1;
        return max;
    }
    public int getID(String name) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        int id = 0;
        rs = stmt.executeQuery("SELECT id FROM Groups WHERE name = '" + name + "'");
        rs.next();
        id = rs.getInt(1);
        return id;
    }
    public void initializeTable() throws SQLException{
        insertGroup("Fixed Assets", 0, "Assets");
        insertGroup("Investments", 0, "Assets");
        this.insertGroup("Branch/divisions", 0, "Assets");
        this.insertGroup("Cash in hand", 0, "Assets");
        this.insertGroup("Bank accounts", 0, "Assets");
        this.insertGroup("Deposits (assets)", 0, "Assets");
        this.insertGroup("Advances (assets)", 0, "Assets");
        this.insertGroup("Capital account", 0, "Liabilities");
        this.insertGroup("Long term loans", 0, "Liabilities");
        this.insertGroup("Current liabilities", 0, "Liabilities");
        this.insertGroup("Reserves and surplus", 0, "Liabilities");
        this.insertGroup("Sales account", 0, "Income");
        this.insertGroup("Purchase account", 0, "Expenses");
        this.insertGroup("Expenses (direct)", 0, "Expenses");
        this.insertGroup("Expenses (indirect)", 0, "Expenses");
        this.insertGroup("Secured loans", 9, "Liabilities");
        this.insertGroup("Unsecured loans", 9, "Liabilities");
        this.insertGroup("Duties taxes payable", 10, "Liabilities");
        this.insertGroup("Provisions", 10, "Liabilities");
        this.insertGroup("Sundry creditors", 10, "Liabilities");
        this.insertGroup("Bank od & limits", 10, "Liabilities");
    }

    // Modify one record based on the given object
    public void changeName(String oldName, String newName) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("UPDATE Groups "
                + "SET name = '" + newName + "' "
                + "WHERE name = '" + oldName + "'");
    }


    // Delete one record based on the given object
    public void deleteGroup(String name) throws SQLException {
        Statement stmt = connection.createStatement();
        // user profile
        stmt.executeUpdate("DELETE FROM Groups WHERE name = '" + name + "'" );
    }

    public String getElement(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        String element = "not found";
        rs = stmt.executeQuery("SELECT element FROM Groups WHERE id = " + id);
        rs.next();
        element = rs.getString(1).trim();
        return element;
    }

    public String getName(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        String name = "not found";
        rs = stmt.executeQuery("SELECT name FROM Groups WHERE id = " + id);
        rs.next();
        name = rs.getString(1).trim();
        return name;
    }
    public int getParent(int id) throws SQLException{
        Statement stmt = connection.createStatement();
        ResultSet rs;
        int parent = 111;
        rs = stmt.executeQuery("SELECT parent FROM Groups WHERE id = " + id);
        rs.next();
        parent = rs.getInt(1);
        return parent;
    }

    public int getMaxId() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs;
        rs = stmt.executeQuery("SELECT MAX(id) FROM Groups");
        rs.next();
        int max = rs.getInt(1);
        return max;
    }

}
