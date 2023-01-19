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
    public boolean Find_block(String block, String xyz){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            ResultSet result = c.createStatement().executeQuery("SELECT * FROM altars WHERE xyz = '" + xyz + "';");
            if(result.isClosed())
                return false;

            if(Objects.equals(result.getString(3), block)){
                s.close();
                c.close();
                return true;
            }
            return false;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
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
