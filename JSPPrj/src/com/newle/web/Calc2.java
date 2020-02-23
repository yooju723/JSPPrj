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
		 * application = 저장소, 다른 브라우저라도 같은 공간으로 인식. HttpSession 현재 접속한 사용자 - 사용자별로 공간이
		 * 달라짐.(=사물함) 에를들어 브라우저를 달리 동시작업을 할 경우 서로 다른 공간으로 인식함. 같은 브라우저로 여러개 띄울경우에는 같은
		 * 공간으로 인식함. --> 같은 브라우저일 경우 프로세스가 아닌 스레드 개념으로 인식함.
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
			// application == 저장소 ,getAttribute == 저장소에 있는 값 반환
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

		else { // value에 v 값 담기.
				// op에 op값 담기.
				// application.setAttribute("value", v);
				// application.setAttribute("op", op);
				// session.setAttribute("value", v);
				// session.setAttribute("op", op);

			// 쿠키보내기
			Cookie valueCookie = new Cookie("value", String.valueOf(v));
			Cookie opCookie = new Cookie("op", op);
			
			//setPath("/notice/") --> notice라는 주소로 요청할때만 쿠키를 가지고 와라.
			valueCookie.setPath("/calc2"); // "/"-->모든페이지를 요청할떄마다 valueCookie를 가지고와라
			valueCookie.setMaxAge(24*60*60); //setMaxAge : 쿠키 만료시기 초단위로 감. --> 쿠키 만료기간을 설정한 것임.
			opCookie.setPath("/calc2");//Calc2페이지를 요청할떄마다 opCookie를 가지고와라
			resp.addCookie(valueCookie);
			resp.addCookie(opCookie);
			
			resp.sendRedirect("calc2.html"); //페이지 전환.
		}
	}

}
