package com.digit.bankapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@WebServlet("/register")
public class register extends HttpServlet 
{
	public static Connection con;
	public static PreparedStatement pstmt;

     @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
     {
    	 int bank_id = Integer.parseInt(req.getParameter("bank_id"));

         String bank_name = req.getParameter("bank_name");

         String ifsc_code = req.getParameter("ifsc_code");

         int accno = Integer.parseInt(req.getParameter("accno"));

         int pin = Integer.parseInt(req.getParameter("pin"));

         int cust_id = Integer.parseInt(req.getParameter("cust_id"));

         String cust_name = req.getParameter("cust_name");

         int balance = Integer.parseInt(req.getParameter("balance"));

         String email = req.getParameter("email");

         long phone = Long.parseLong(req.getParameter("phone"));

  

         String url = "jdbc:mysql://localhost:3306/bankingapplication";

         String user = "root";

         String pwd = "cseibm";

         

         //Database connection

         try {

             Class.forName("com.mysql.cj.jdbc.Driver");

             con = DriverManager.getConnection(url, user, pwd);

             

             pstmt = con.prepareStatement("insert into bankapp values(?,?,?,?,?,?,?,?,?,?)");

             pstmt.setInt(1, bank_id);

             pstmt.setString(2, bank_name);

             pstmt.setString(3, ifsc_code);

             pstmt.setInt(4, accno);

             pstmt.setInt(5, pin);

             pstmt.setInt(6, cust_id);

             pstmt.setString(7, cust_name);

             pstmt.setInt(8, balance);

             pstmt.setString(9, email);

             pstmt.setLong(10, phone);

             

             int x = pstmt.executeUpdate();

             if(x>0) {

                 resp.sendRedirect("/BankingApplication/registersucces.html");

             }

             else {

                 resp.sendRedirect("/BankingApplication/registerfail.html");

             }

             

         }

         catch (Exception e) {

             e.printStackTrace();

         }

     }
    }

