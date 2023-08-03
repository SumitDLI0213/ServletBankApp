package com.digit.bankapp;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
@WebServlet("/logout")
public class logout extends HttpServlet
{
     private HttpSession session;

	@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException 
     {
    	 session=req.getSession();
    	 session.invalidate();
         resp.sendRedirect("/BankingApplication/welcome.html");

    }
}
