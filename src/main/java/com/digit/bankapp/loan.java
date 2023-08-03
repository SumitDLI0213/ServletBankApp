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
@WebServlet("/loan")
public class loan extends HttpServlet
{
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet resultset;

	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
    {
		//int choice = Integer.parseInt(req.getParameter("choice"));
		int lid = Integer.parseInt(req.getParameter("lid"));

        String url = "jdbc:mysql://localhost:3306/bankingapplication";

        String user = "root";

        String pwd = "cseibm";
         HttpSession session = req.getSession(true);
        

        //Database connection

        try {

            Class.forName("com.mysql.cj.jdbc.Driver");

            con = DriverManager.getConnection(url, user, pwd);

            
           
            pstmt = con.prepareStatement("select * from loan where lid=?");

            pstmt.setInt(1, lid);
            resultset = pstmt.executeQuery();

            if(resultset.next()==true) {
                session.setAttribute("lid", resultset.getInt("lid"));
                session.setAttribute("l_type", resultset.getString("l_type"));
                session.setAttribute("tenure", resultset.getInt("tenure"));
                session.setAttribute("interest", resultset.getFloat("interest"));
                session.setAttribute("description", resultset.getString("description"));

                resp.sendRedirect("/BankingApplication/loandetails.jsp");

            }

            else {

                resp.sendRedirect("/BankingApplication/loandetailsfail.html");
            }
        }

        catch (Exception e) 
        {

                e.printStackTrace();
        }

    }
}
