package com.digit.bankapp;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Random;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/transfer")
public class transfer extends HttpServlet 
{
     private Connection con;
	private PreparedStatement pstmt;
	private ResultSet res1;
	private ResultSet res2;
	private ResultSet res3;
	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
     {
    	 int cust_id = Integer.parseInt(req.getParameter("cust_id"));
    	 String bank_name = req.getParameter("bank_name");
    	 String ifsc_code = req.getParameter("ifsc_code");
    	 int sender_accno = Integer.parseInt(req.getParameter("sender_accno"));
    	 String reciever_ifsc = req.getParameter("reciever_ifsc");
    	 int reciever_accno = Integer.parseInt(req.getParameter("reciever_accno"));
    	 int amount = Integer.parseInt(req.getParameter("amount"));
    	 int pin = Integer.parseInt(req.getParameter("pin"));
         int random=new Random().nextInt(90000)+10000;
         String url = "jdbc:mysql://localhost:3306/bankingapplication";

         String user = "root";

         String pwd = "cseibm";
          HttpSession session = req.getSession(true);
         

         //Database connection

         try {

             Class.forName("com.mysql.cj.jdbc.Driver");

             con = DriverManager.getConnection(url, user, pwd);

             
            
             pstmt = con.prepareStatement("select * from bankapp where cust_id=? and ifsc_code=? and accno=? and pin=?");

             pstmt.setInt(1, cust_id);
             pstmt.setString(2,ifsc_code);
             pstmt.setInt(3, sender_accno);
             pstmt.setInt(4, pin);

             res1 = pstmt.executeQuery();

             if(res1.next()==true) 
             {
                 pstmt = con.prepareStatement("select * from bankapp where ifsc_code=? and accno=?");
                 pstmt.setString(1, reciever_ifsc);
                 pstmt.setInt(2,reciever_accno);
                 res2 = pstmt.executeQuery();
                 if(res2.next()==true)
                 {
                	 pstmt = con.prepareStatement("select balance from bankapp where accno=?");
                     pstmt.setInt(1,sender_accno);
                     res3 = pstmt.executeQuery();
                     res3.next();
                     int bal=res3.getInt(1);
                     if(bal>amount)
                     {
                    	 pstmt = con.prepareStatement("Update bankapp set balance=balance-? where accno=?");
                         pstmt.setInt(1,amount);
                         pstmt.setInt(2,sender_accno);
                         int x1=pstmt.executeUpdate();
                         if(x1>0)
                         {
                        	 pstmt = con.prepareStatement("Update bankapp set balance=balance+? where accno=?");
                             pstmt.setInt(1,amount);
                             pstmt.setInt(2,reciever_accno);
                             int x2=pstmt.executeUpdate();
                             if(x2>0)
                             {
                            	 pstmt = con.prepareStatement("insert into transferstatus values(?,?,?,?,?,?,?,?)");
                                 pstmt.setInt(1,(int) Math.random());
                            	 pstmt.setInt(2,cust_id);
                                 pstmt.setString(3,bank_name);
                                 pstmt.setString(4,ifsc_code);
                                 pstmt.setInt(5,sender_accno);
                                 pstmt.setString(6,reciever_ifsc);
                                 pstmt.setInt(7,reciever_accno);
                                 pstmt.setInt(8,amount);
                                 
                                 int x3=pstmt.executeUpdate();
                                 if(x3>0)
                                 {
                                     resp.sendRedirect("/ServletBankApp/transfersucces.html"); 
                                 }
                                 else
                                 {
                                     resp.sendRedirect("/ServletBankApp/transferfail.jsp"); 
                                 }

                             }
                             else
                             {
                                 resp.sendRedirect("/ServletBankApp/transferfail.jsp"); 
                             }
                         }
                         else
                         {
                             resp.sendRedirect("/ServletBankApp/transferfail.jsp"); 
                         }
                     }
                     else
                     {
                         resp.sendRedirect("/ServletBankApp/transferfail.jsp"); 
                     }
                 }
                 else
                 {
                     resp.sendRedirect("/ServletBankApp/transferfail.jsp"); 
                 }
             }

             else 
             {
                 resp.sendRedirect("/ServletBankApp/transferfail.jsp"); 
             }
         }

         catch (Exception e) 
         {

                 e.printStackTrace();
         }
    }
}
