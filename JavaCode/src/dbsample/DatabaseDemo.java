package dbsample;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;


/**
 * Servlet implementation class DatabaseDemo
 */
@WebServlet("/DatabaseDemo")
public class DatabaseDemo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DatabaseDemo() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rsCount = null;
		Connection conn = null;
		Integer count = 1;
		PrintWriter out = response.getWriter();

		response.setContentType("text/html");
		out.println("<HTML>");
		out.println("<HEAD/>");
		out.println("<BODY>");	
		out.println("<H1>Movie Ratings</H1></p>");
		String mCount = request.getParameter("mCount");
		String localHost = System.getenv("HOSTNAME");
		out.println("Running on container: " + localHost + "</p>");
		
		try {
		    // The newInstance() call is a work around for some
		    // broken Java implementations
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
		} catch (Exception ex) {
		    // handle the error
		}
		
		
		try {
			conn = getConnection();
			stmt = conn.createStatement();
			// Get row count
			rsCount = stmt.executeQuery("SELECT count(*) FROM movie_ranking_top250");
			rsCount = stmt.getResultSet();
			while (rsCount.next()) {
				String countValue = rsCount.getString(1);
				out.println("Total number of movie ratings: " + countValue + "</br>");
			}
			out.println("Number of movies to show: " + mCount + "</p>");
			
		    rs = stmt.executeQuery("SELECT rank, title FROM movie_ranking_top250 ORDER BY rank LIMIT 0, " + mCount);
	        rs = stmt.getResultSet();
		    while (rs.next()){
		    	String rating = rs.getString(1);
		    	String title = rs.getString(2);
		    	out.println(count + ". " + title + " : " + rating + "</br>");
		    	count++;
		    }
		}
		catch (SQLException ex){
		    // handle any errors
		    System.out.println("SQLException: " + ex.getMessage());
		    System.out.println("SQLState: " + ex.getSQLState());
		    System.out.println("VendorError: " + ex.getErrorCode());
		} finally {
		    // it is a good idea to release
		    // resources in a finally{} block
		    // in reverse-order of their creation
		    // if they are no-longer needed
		    if (rs != null || rsCount !=null) {
		        try {
		            rs.close();
		            rsCount.close();
		        } catch (SQLException sqlEx) { } // ignore
		rs = null; 
		rsCount = null;}
		    if (stmt != null) {
		        try {
		            stmt.close();		    
		        } catch (SQLException sqlEx) { } // ignore
		stmt = null; }
		}
		out.println("</p></p>");
		out.println("<h5>Information courtesy of IMBD</br>");
		out.println("(<a href=http://www.imdb.com>http://www.imdb.com</a>).</br>");
		out.println("Used with permission.</h5></br>");
		out.println("</BODY></HTML>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public Connection getConnection() throws SQLException {
		Connection conn = null;
		String mySqlUser = System.getenv("MYSQL_USER");
		String mySqlPwd = System.getenv("MYSQL_PWD");
		String mySqlIP = System.getenv("MYSQL_IP");
		String mySqlPort = System.getenv("MYSQL_PORT");
		String mySqlDB = System.getenv("MYSQL_DB");
//	    conn = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/test_01?user=root&password=ChangeMe!&useSSL=false&disconnectOnExpiredPasswords=true");
//		conn = DriverManager.getConnection("jdbc:mysql://172.17.0.2:3306/test_01?user=root&password=ChangeMe!&useSSL=false");
		conn = DriverManager.getConnection("jdbc:mysql://" + mySqlIP + ":" + mySqlPort + "/" + mySqlDB + "?useSSL=false&user=" + mySqlUser + "&password=" + mySqlPwd);
		System.out.println("Connected to database");
		
		return conn;
	}

}
