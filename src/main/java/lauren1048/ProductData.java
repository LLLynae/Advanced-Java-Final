 /**
 *Lauren Lira - lllira1048
 *CIT 4423 01
 *Nov 13,2022
 *Windows 11
 */

package lauren1048;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.javafaker.Faker;

public class ProductData {/*Class to access MySQL database in order to populate it with
    product data using Faker */

    Faker fake = new Faker();//Instantiates faker object

    private String name;
    private String description;
    private int quantity;
    private int price;
    //encapsulation

    public ProductData() {/*Constructor, uses faker */
        this.name = fake.commerce().productName();
        this.description = fake.book().genre();
        this.quantity = (int) fake.number().randomNumber(5, false);
        this.price = (int) fake.number().randomNumber(8, false);
    }

    public ProductData(String name) {/*Collects product name data and returns it*/
        String query = "SELECT * FROM products_information WHERE Names='" + name + "'";//database query
        String url = "jdbc:mysql://127.0.0.1:3306/adv_java_data";
        String user = "root";
        String password = "F10w3r8798*";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            ResultSet result = statement.executeQuery(query);
            result.next();
            this.name = result.getString("names");
            this.description = result.getString("description");
            this.price = Integer.parseInt(result.getString("price"));
            this.quantity = Integer.parseInt(result.getString("quantity"));
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void persistProduct() {/*Populates and stores data in the database, persistence */
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/adv_java_data";
            String user = "root";
            String password = "F10w3r8798*";
            StringBuilder build = new StringBuilder(
                    "Insert into products_information(Names, Description, Quantity, Price)");
            build.append(String.format(" values('%s', '%s', '%s', '%s')", this.name, this.description,
                    this.quantity, this.price));
            String query = build.toString();
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, password);
            Statement statement = con.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //Getters
    public String getName() {
        return this.name;
    }

    public String getPriceAsString() {
        return String.format("$%,.2f", (Double.valueOf(this.price / 100)));
    }

    public double getPrice(){
        return Double.valueOf(this.price / 100);
    }

}//End of class
