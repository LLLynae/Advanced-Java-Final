 /**
 *Lauren Lira - lllira1048
 *CIT 4423 01
 *Nov 13,2022
 *Windows 11
 */

package lauren1048;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.github.javafaker.Faker;

public class CustomerData {/*Class to populate and store customer data */

    Faker fake = new Faker();
    //Faker object instantiated

    private String name;
    private String residentialAddress;
    private String billingAddress;
    private long creditCard;
    //Datatypes and access modifiers

    public CustomerData() {/*Constructor */
        this.name = fake.name().fullName();
        this.residentialAddress = fake.address().streetAddress();
        this.billingAddress = fake.address().streetAddress();
        this.creditCard = fake.number().randomNumber(16, true);
    }

    public void persistData() {/*Populates and stores data in the database, persistence */
        try {
            String url = "jdbc:mysql://127.0.0.1:3306/adv_java_data";
            String user = "root";
            String password = "F10w3r8798*";
            StringBuilder bld = new StringBuilder(
                    "Insert into customer_information(BuyerName, Residential_Address, BillingAddress, Credit_Card_Information)");
            bld.append(String.format(" values('%s', '%s', '%s', '%s')", this.name, this.residentialAddress,
                    this.billingAddress, String.valueOf(this.creditCard)));
            String query = bld.toString();
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

}//End of class
