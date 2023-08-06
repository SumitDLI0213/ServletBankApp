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
@WebServlet("/changepassword")
public class changepassword extends HttpServlet

{
      private Connection con;
	private PreparedStatement pstmt;
	private ResultSet resultset;
	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
      {
    	  String url = "jdbc:mysql://localhost:3306/bankingapplication";

          String user = "root";

          String pwd = "cseibm";
           HttpSession session = req.getSession(true);
           int opin =Integer.parseInt(req.getParameter("opin"));
           int accno =(int)session.getAttribute("accno");

           //int oldpass =Integer.parseInt(req.getParameter("oldpass"));
           int npin =Integer.parseInt(req.getParameter("npin"));
           int cpin =Integer.parseInt(req.getParameter("cpin"));


          //Database connection

          try {

              Class.forName("com.mysql.cj.jdbc.Driver");

              con = DriverManager.getConnection(url, user, pwd);

              if(npin==cpin)
              {
            	  pstmt = con.prepareStatement("update bankapp set pin=? where accno=? and pin=?");

                  pstmt.setInt(1, npin);
                  pstmt.setInt(2,accno);
                  pstmt.setInt(3, opin);
                  int x = pstmt.executeUpdate();
                  if(x>0) {
//                      session.setAttribute("newpassword", resultset.getString("newpassword"));
                      resp.sendRedirect("/ServletBankApp/passwordchangesucess.html");

                  }

                  else {

                      resp.sendRedirect("/ServletBankApp/passwordchangefail.html");
                  }
            	  
              }
              else
              {
                  //resp.sendRedirect("/BankingApplication/passwordchangefail.jsp");

              }
             
          }

          catch (Exception e) 
          {

                  e.printStackTrace();
          }
    }
}
