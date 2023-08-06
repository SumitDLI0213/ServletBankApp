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
@WebServlet("/checkbalance")
public class checkbalance extends HttpServlet
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
        int accno =(int)session.getAttribute("accno");


       //Database connection

       try {

           Class.forName("com.mysql.cj.jdbc.Driver");

           con = DriverManager.getConnection(url, user, pwd);

           

           pstmt = con.prepareStatement("select balance from bankapp where accno=?");

           pstmt.setInt(1, accno);
           resultset = pstmt.executeQuery();

           if(resultset.next()==true) {
               session.setAttribute("balance", resultset.getInt("balance"));
               resp.sendRedirect("/ServletBankApp/balance.jsp");

           }

           else {

               resp.sendRedirect("/ServletBankApp/balancefail.jsp");
           }
       }

       catch (Exception e) 
       {

               e.printStackTrace();
       }

}
}
