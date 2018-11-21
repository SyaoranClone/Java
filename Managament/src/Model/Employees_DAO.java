/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import static Model.DAO.DB_URL;
import static Model.DAO.DRIVER;
import static Model.DAO.PASS;
import static Model.DAO.USER;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.omg.PortableServer.IdAssignmentPolicy;

/**
 *
 * @author MyPC
 */
public class Employees_DAO implements DAO{
    private Employee createEmployee(ResultSet rs) {
        Employee e = new Employee();
        try {
            e.setId(rs.getInt("id"));
            e.setName(rs.getString("name"));
            e.setEmail(rs.getString("email"));
            e.setPassword(rs.getString("password"));
            e.setGender(rs.getInt("gender"));
            e.setId_card_number(rs.getInt("id_card_number"));
            e.setProvince_id(rs.getString("provinceName"));
            e.setAddress(rs.getString("address"));

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Can't load database!");
        }
        return e;
 }
 
public ObservableList<Employee> getEmployee() {
       // String sql = "SELECT employees.id, employees.name, employees.email, employees.gender, employees.id_card_number,provinces.name as province, employees.address FROM `employees` INNER JOIN `provinces` on employees.province_id = provinces.id ";
       String sql = "SELECT employees.id, employees.name, employees.email, employees.password, "
               + "employees.gender, employees.id_card_number, provinces.name as provinceName,"
               + " employees.address FROM employees INNER JOIN provinces "
               + "on employees.province_id = provinces.id"; 
       ObservableList<Employee> list = FXCollections.observableArrayList();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Employee e = createEmployee(rs);
                list.add(e);
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can't load database!");
        }

        return list;
    }
 private Province createProvince(ResultSet rs) {
        Province p = new Province();
        try {
            p.setIdProvince(rs.getInt("id"));
            p.setNameProvinceString(rs.getString("name"));

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Can't load database!");
        }
        return p;
 }
 
public ObservableList<Province> getProvinces() {
       // String sql = "SELECT employees.id, employees.name, employees.email, employees.gender, employees.id_card_number,provinces.name as province, employees.address FROM `employees` INNER JOIN `provinces` on employees.province_id = provinces.id ";
       String sql = "select id, name from provinces"; 
      
       ObservableList<Province> list = FXCollections.observableArrayList();
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Province p = createProvince(rs);
                list.add(p);
            }
            rs.close();
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can't load database!");
        }

        return list;
    }

public void AddEmployee(Employee e, int p){
        String sql = "insert into employees(id, name, email, password, gender, id_card_number, province_id, address) "
                + "values ( " +  e.getId() + ", '" + e.getName() + "', '"+ e.getEmail() + "', '" + e.getPassword() + 
                "', " + e.getGender() +", "+ e.getId_card_number() + ", " + p + ", '" + e.getAddress() + "')";
        //System.out.println("sql insert : " + sql);
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected");
            int stmt = con.createStatement().executeUpdate(sql);
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can't load database!");
        }
        
    }
public void UpdateEmployee(Employee e, int p){
        String sql = "update employees set  "
                + "name =  '" + e.getName() + "', email = '"+ e.getEmail() + "', "  + 
                " gender = " + e.getGender() +", id_card_number = "+ e.getId_card_number() + ", province_id = " + p + ", address = '" + e.getAddress() +"' where id = " + e.getId();
        System.out.println("sql update : " + sql);
        int stmt = 0;
        try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected");
             stmt = con.createStatement().executeUpdate(sql);
            
            
            
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can't load database!");
        }
        
    }
public void DeleteEmployee(int  id)
{
    String sql = "delete from employees where employees.id = " + id;
    try {
            Class.forName(DRIVER);
            Connection con = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected");
            int stmt = con.createStatement().executeUpdate(sql);
            con.close();
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
            System.out.println("Can't load database!");
        }
}
}
