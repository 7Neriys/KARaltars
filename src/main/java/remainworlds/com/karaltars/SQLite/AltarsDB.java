package remainworlds.com.karaltars.SQLite;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class AltarsDB {
    private String url;
    public AltarsDB() throws Exception{
        url = "jdbc::sqlite:plugins/KARaltars/Altars.db";
        Class.forName("org.sqlite.JDBC").newInstance();

        Connection c = getConnection();
        Statement s = c.createStatement();

        s.executeUpdate("CREATE TABLE IF NOT EXISTS users ('nickname' TEXT, 'date' TEXT)");

        s.close();
        c.close();

    }

    public Connection getConnection() throws Exception{
        return DriverManager.getConnection(url);
    }

    public  void add(String nickname, String date){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO users VALUES ('" + nickname + "', '" + date + "')");
            s.close();
            c.close();

        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
    public  int count(String nickname){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            ResultSet result = c.createStatement().executeQuery("SELECT COUNT(date) FROM users WHERE nickname = '" + nickname + "'");
            int temp = result.getInt(1);
            s.close();
            c.close();
            return temp;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public  void remove(String nickname){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();

            s.executeUpdate("DELETE FROM users WHERE nickname = '" + nickname + "'");
            s.close();
            c.close();

        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
}
