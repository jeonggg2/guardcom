import gc.crypto.CryptoMgr;
import gc.db.DbCon;

//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
//import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static int ref = 0;
	private int count;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MainServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        CryptoMgr cryptoMgr = new CryptoMgr();
        cryptoMgr.init();
        cryptoMgr.terminate();
        
        
        //String ConfigFilePath;
        //ConfigFilePath = System.getProperty("user.home") + System.getProperty("file.separator") + "GuardComServer" + System.getProperty("file.separator") + "Config.data";
		
       // getServletContext().log(ConfigFilePath);
        
		//File file = null;
		//InputStream fio = null;
		//byte[] version = new byte[4];
		
		//file = new File(ConfigFilePath);
		
		//try {
		//	try {
				
		//		fio = new FileInputStream(file);
		//	
		//	} catch (FileNotFoundException e1) {
				
		//		e1.printStackTrace();		
				
		//		getServletContext().log(e1.toString());
		//	}	
		//	
		//	fio.read(version);
			
			
		//} catch (Exception e) {
			
		//	getServletContext().log(e.toString());	
		//}        

        
        getServletContext().log("GcMgmt init() called");
        
        ref++;
        if (ref == 1) {        	
	    	Connection con;
			PreparedStatement pstmta;
			try {
				con = DbCon.getConnection();
				pstmta = con.prepareStatement("INSERT INTO guardcom.server_audit (ip, id, action_id, parameter, description, status) VALUES (?,?,'1000', ?, '서버 시작', '성공')");
				if (pstmta != null) {
					InetAddress inetAddress;
					String strIpAdress = "localhost";
					try {
						inetAddress = InetAddress.getLocalHost();
						strIpAdress = inetAddress.getHostAddress();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					pstmta.setString(1,strIpAdress);
					pstmta.setString(2, "Server Agent");
					pstmta.setString(3, "서버 작동이 시작되었습니다." );
					pstmta.execute();
					pstmta.close();
				}	
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
        count = 0;
    }
 
    protected void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().log("service() called");
        count++;
        response.getWriter().write("Incrementing the count: count = " + count);
    }
 
    public void destroy() {
        getServletContext().log("GcMgmt destroy() called");
        
        ref--;
	    if (ref == 0) {
	    	Connection con;
			PreparedStatement pstmta;
			try {
				con = DbCon.getConnection();
				pstmta = con.prepareStatement("INSERT INTO guardcom.server_audit (ip, id, action_id, parameter, description, status) VALUES (?,?,'1000', ?, '서버 종료', '성공')");
				if (pstmta != null) {
					InetAddress inetAddress;
					String strIpAdress = "localhost";
					try {
						inetAddress = InetAddress.getLocalHost();
						strIpAdress = inetAddress.getHostAddress();
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}					
					pstmta.setString(1,strIpAdress);
					pstmta.setString(2, "Server Agent");
					pstmta.setString(3, "서버 작동이 중지되었습니다." );
					pstmta.execute();
					pstmta.close();
				}	
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	       }
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
