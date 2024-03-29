package bio.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bio.controller.CandyInter;

public class Join implements CandyInter {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 할일
		// 세션검사
		Object sid = req.getSession().getAttribute("SID");
		
		if(sid != null) {
			// 이미 로그인 되어있는 경우
			req.setAttribute("isRedirect", true);
			return "/main.candy";
		}
		// 뷰
		String view = "member/join";
		return view;
	}

}
