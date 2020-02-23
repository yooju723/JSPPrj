package com.newle.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/add2")
public class Add2 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");
		// req.setCharacterEncoding("UTF-8");

		String[] num_  = req.getParameterValues("num"); // �迭�� �ޱ����ؼ��� getParameterValues�� ����
		int result = 0;

		for (int i = 0; i < num_.length; i++) {
			int num = Integer.parseInt(num_[i]);
			result += num;
		}
		resp.getWriter().printf("result is %d\n", result); // string.format =  %d\n
		;
	}

}
