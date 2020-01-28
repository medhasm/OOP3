package gameClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.mysql.jdbc.ResultSetMetaData;

/**
 * This class represents a simple example of using MySQL Data-Base. Use this
 * example for writing solution.
 *
 * @author boaz.benmoshe
 *
 */
public class My_Data_Base {
    public static final String jdbcUrl = "jdbc:mysql://db-mysql-ams3-67328-do-user-4468260-0.db.ondigitalocean.com:25060/oop?useUnicode=yes&characterEncoding=UTF-8&useSSL=false";
    public static final String jdbcUser = "student";
    public static final String jdbcUserPassword = "OOP2020student";
    private static Connection connection = null;
    private static Statement statement = null;

    private static ResultSet doQuery(String query) {
        ResultSet resultSet = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcUserPassword);
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);
        }

        catch (SQLException sqle) {
            System.out.println("SQLException: " + sqle.getMessage());
            System.out.println("Vendor Error: " + sqle.getErrorCode());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return resultSet;
    }


    /**
     * Close query function
     * @param resultSet
     */
    private static void closeQuery(ResultSet resultSet) {
        try {
            resultSet.close();
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Count the number of games that played by user ID
     * @param id
     * @return
     */
    public static int gamesPlayed(int id) {
        ResultSet resultSet = doQuery("select * from Logs where userID = " + id);
        int count = 0;
        try {
            while (resultSet.next()) {
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        closeQuery(resultSet);
        return count;
    }

    /**
     * Function that returns the best results of the user in each game stage.
     * Group by to exclude the highest result and treeMap to remove duplicates.
     * @param id
     * @return
     */
    public static HashMap<Integer, String> myBestResults(int id) {
        String query = "SELECT * FROM Logs as logs inner join (" + "SELECT max(score) as score, levelID FROM Logs"
                + " where userID = " + id + " group by levelID" + ") as groupedLogs"
                + " on logs.levelID = groupedLogs.levelID and logs.score = groupedLogs.score" + " where userID = " + id
                + " order by logs.levelID asc";
        ResultSet resultSet = doQuery(query);
        HashMap<Integer, String> tp = new HashMap<Integer, String>();
        try {

            while (resultSet.next()) {
                String value = "" + resultSet.getInt("userID") + "," + resultSet.getInt("levelID") + ","
                        + resultSet.getInt("score") + "," + resultSet.getInt("moves") + "," + resultSet.getDate("time");
                tp.put(resultSet.getInt("levelID"), value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeQuery(resultSet);
        return tp;
    }

    /**
     * Function that returns the best results of all user for 11 stages.
     * Group by to exclude the highest result and treeMap to remove duplicates.
     * @param id
     * @return
     */
    public static HashMap<String, String> gameBestResults() {
        String query = "SELECT * FROM Users;";
        ResultSet resultSet = doQuery(query);
       HashMap<String, String> tp = new HashMap<String, String>();
        try {

            while (resultSet.next()) {
                String value = "" + resultSet.getInt("userID\n") + "," + resultSet.getInt("levelID") + ","
                        + resultSet.getInt("score") + "," + resultSet.getInt("moves") + "," + resultSet.getDate("time");
                tp.put(resultSet.getInt("userID") + "," + resultSet.getInt("levelID"), value);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closeQuery(resultSet);
        return tp;
    }
    public static void main(String[] args) {
		My_Data_Base db=new My_Data_Base();
		int x=db.gamesPlayed(206953127);
		HashMap<Integer, String> n=db.myBestResults(206953127);
		//HashMap<String, String> b=db.gameBestResults();
		//System.out.println(b.get(206953127));
		System.out.println(x);
		System.out.println(n);
	}
}