package com.newle.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/calc2")
public class Calc2 extends HttpServlet {

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ServletContext application = req.getServletContext();
		/*
		 * application = �����, �ٸ� �������� ���� �������� �ν�. HttpSession ���� ������ ����� - ����ں��� ������
		 * �޶���.(=�繰��) ������� �������� �޸� �����۾��� �� ��� ���� �ٸ� �������� �ν���. ���� �������� ������ ����쿡�� ����
		 * �������� �ν���. --> ���� �������� ��� ���μ����� �ƴ� ������ �������� �ν���.
		 */
		HttpSession session = req.getSession();
		Cookie[] cookies = req.getCookies();

		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html; charset=UTF-8");

		String op = req.getParameter("operator");
		String v_ = req.getParameter("v");

		int v = 0;
		if (!v_.equals(""))
			v = Integer.parseInt(v_);

		if (op.equals("=")) {
			// application == ����� ,getAttribute == ����ҿ� �ִ� �� ��ȯ
			// int x = (Integer) application.getAttribute("value");
			// int x = (Integer) session.getAttribute("value");
			int x = 0;
			for (Cookie c : cookies) {
				if (c.getName().equals("value")) {
					x = Integer.parseInt(c.getValue());
					break;
				}
			}

			int y = v;

			// String operator = (String) application.getAttribute("op");
			// String operator = (String) session.getAttribute("op");

			String operator = "";
			for (Cookie c : cookies) {
				if (c.getName().equals("op")) {
					operator = c.getValue();
					break;
				}
			}

			int result = 0;

			if (operator.equals("+"))
				result = x + y;
			else
				result = x - y;
			resp.getWriter().printf("result is %d\n", result);
		}

		else { // value�� v �� ���.
				// op�� op�� ���.
				// application.setAttribute("value", v);
				// application.setAttribute("op", op);
				// session.setAttribute("value", v);
				// session.setAttribute("op", op);

			// ��Ű������
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			Cookie opCookie = new Cookie("op", op);
			
			//setPath("/notice/") --> notice��� �ּҷ� ��û�Ҷ��� ��Ű�� ������ �Ͷ�.
			valueCookie.setPath("/calc2"); // "/"-->����������� ��û�ҋ����� valueCookie�� ������Ͷ�
			valueCookie.setMaxAge(24*60*60); //setMaxAge : ��Ű ����ñ� �ʴ����� ��. --> ��Ű ����Ⱓ�� ������ ����.
			opCookie.setPath("/calc2");//Calc2�������� ��û�ҋ����� opCookie�� ������Ͷ�
			resp.addCookie(valueCookie);
			resp.addCookie(opCookie);
			
			resp.sendRedirect("calc2.html"); //������ ��ȯ.
		}
	}

}
