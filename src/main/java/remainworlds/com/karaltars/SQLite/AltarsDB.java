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

        s.executeUpdate("CREATE TABLE IF NOT EXISTS altars ('altarName' TEXT, 'blockID' INTEGER, 'block' TEXT, 'world' TEXT, 'xyz' TEXT, 'item' TEXT, 'lore' TEXT);");

        s.close();
        c.close();

    }

    public Connection getConnection() throws Exception{
        return DriverManager.getConnection(url);
    }

    public  void add(String altarName, int blockID, String block, String world, String xyz, String item, String lore){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            s.executeUpdate("INSERT INTO altars VALUES ('" + altarName + "', '" + blockID + "', '" + block + "', '" + world + "', '" + xyz + "', '" + item + "', '" + lore + "')");
            s.close();
            c.close();
        }
        catch (Exception e){
            e.printStackTrace();

        }
    }


    public String GetAltarName(String xyz, String world){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            ResultSet result = c.createStatement().executeQuery("SELECT * FROM altars WHERE xyz = '" + xyz + "' AND world = '" + world + "';");
            if(result.isClosed()){
                s.close();
                c.close();
                return null;
            }

            String R = result.getString(1) + "; " + result.getString(2);
            s.close();
            c.close();
            return R;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public String Find_block(String block, String xyz, String world){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            ResultSet result = c.createStatement().executeQuery("SELECT * FROM altars WHERE xyz = '" + xyz + "' AND world = '" + world + "';");
            if(result.isClosed()){
                s.close();
                c.close();
                return null;
            }



            if(Objects.equals(result.getString(3), block)){


                String R = result.getString(1) + "; " + result.getString(2) + "; " + result.getString(6) + "; " + result.getString(7);

                s.close();
                c.close();
                return R + "; yes";
            }
            String R = result.getString(1) + "; " + result.getString(2) + "; " + result.getString(6) + "; " + result.getString(7);
            s.close();
            c.close();
            return R + "; NOT";
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    public void removeAltar(String altarName){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            s.executeUpdate("DELETE FROM altars WHERE altarName = '" + altarName + "'");

            s.close();
            c.close();

        }
        catch (Exception e){
            e.printStackTrace();

        }
        //s.executeUpdate("DELETE FROM altars WHERE nickname = '" + nickname + "'");
    }



    public void addItemToAltar(String altarName, String BlockID, String item, String lore){
        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();

            ResultSet result = c.createStatement().executeQuery("SELECT * FROM altars WHERE altarName = '" + altarName + "' AND blockID = '" + BlockID + "';");

            s.executeUpdate("DELETE FROM altars WHERE altarName = '" + altarName + "' AND blockID = '" + BlockID + "'");

            s.executeUpdate("INSERT INTO altars VALUES ('" + altarName + "', '" + BlockID + "', '" + result.getString(3) + "', '" + result.getString(4) + "', '" + result.getString(5) + "', '" + item + "', '" + lore + "')");

            s.close();
            c.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void clearBD(){

        try {
            Connection c = this.getConnection();
            Statement s = c.createStatement();
            s.executeUpdate("delete from altars");
            s.close();
            c.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
