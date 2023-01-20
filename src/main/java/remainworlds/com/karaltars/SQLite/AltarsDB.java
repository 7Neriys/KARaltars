package remainworlds.com.karaltars.SQLite;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.logging.Logger;

public class AltarsDB {
    Logger log = Logger.getLogger("Minecraft");
    private String url;
    public AltarsDB() throws Exception{
        url = "jdbc:sqlite:plugins/KARaltars/altars.db";
        Class.forName("org.sqlite.JDBC").newInstance();

        Connection c = getConnection();
        Statement s = c.createStatement();

        s.executeUpdate("CREATE TABLE IF NOT EXISTS altars ('altarName' TEXT, 'blockID' INTEGER, 'block' TEXT, 'world' TEXT, 'xyz' TEXT);");

        s.close();
        c.close();

    }

    public Connection getConnection() throws Exception{
        return DriverManager.getConnection(url);
    }

    public  void add(String altarName, int blockID, String block, String world, String xyz){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO altars VALUES ('" + altarName + "', '" + blockID + "', '" + block + "', '" + world + "', '" + xyz + "')");
            s.close();
            c.close();
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
    public String Find_block(String block, String xyz){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            ResultSet result = c.createStatement().executeQuery("SELECT * FROM altars WHERE xyz = '" + xyz + "';");
            if(result.isClosed()){
                s.close();
                c.close();
                return null;
            }


            if(Objects.equals(result.getString(3), block)){


                String R = result.getString(1) + "; " + result.getString(2);

                s.close();
                c.close();
                return R;
            }
            String R = result.getString(1) + "; " + result.getString(2);
            s.close();
            c.close();
            return "NOT; " + R;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public  void remove(String nickname){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();

            s.executeUpdate("DELETE FROM altars WHERE nickname = '" + nickname + "'");
            s.close();
            c.close();

        }
        catch (Exception e){
            e.printStackTrace();

        }
    }
}
