package bio.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bio.controller.CandyInter;

public class Logout implements CandyInter {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 리다이렉트 셋팅
		req.setAttribute("isRedirect", true);
		// 세션꺼내오고
		HttpSession session = req.getSession();
		// 뷰
		String view = "/main.candy";
		// 처리
		if(session.getAttribute("SID") != null) {
			session.removeAttribute("SID");
		}
		
		return view;
	}

}
