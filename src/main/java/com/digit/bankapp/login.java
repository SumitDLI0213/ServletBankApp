package com.digit.bankapp;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/login")
public class login extends HttpServlet
{
    private Connection con;
	private PreparedStatement pstmt;
	private ResultSet resultset;

	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
     {
         int cust_id = Integer.parseInt(req.getParameter("cust_id"));
         int pin = Integer.parseInt(req.getParameter("pin"));
         String url = "jdbc:mysql://localhost:3306/bankingapplication";

         String user = "root";

         String pwd = "cseibm";
          HttpSession session = req.getSession(true);
         

         //Database connection

         try {

             Class.forName("com.mysql.cj.jdbc.Driver");

             con = DriverManager.getConnection(url, user, pwd);

             

             pstmt = con.prepareStatement("select * from bankapp where cust_id=? and pin=?");

             pstmt.setInt(1, cust_id);
             pstmt.setInt(2, pin);
             resultset = pstmt.executeQuery();

             if(resultset.next()==true) {
                 session.setAttribute("accno", resultset.getInt("accno"));
                 session.setAttribute("cust_name", resultset.getString("cust_name"));
                 resp.sendRedirect("/ServletBankApp/homepage.jsp");

             }

             else {

                 resp.sendRedirect("/ServletBankApp/loginfail.html");
             }
         }

         catch (Exception e) 
         {

                 e.printStackTrace();
         }

    }
}
