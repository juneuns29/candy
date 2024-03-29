package bio.controller.member;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import bio.controller.CandyInter;
import bio.dao.*;

public class LoginProc implements CandyInter {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 리다이렉트 설정
		req.setAttribute("isRedirect", true);
		String view = "/main.candy";
		
		// 파라미터 꺼내고
		String sid = req.getParameter("id");
		String spw = req.getParameter("pw");
		
		// 데이터베이스 작업
		MemberDao mDao = new MemberDao();
		int cnt = mDao.getLogin(sid, spw);
		
		// 결과에 따라 조건 처리
		if(cnt != 1) {
			view = "/member/login.candy";
			return view;
		}
		
		// 로그인 처리를 해줘야 하는 경우
		// 로그인 처리는 세션에 SID 키값으로 아이디를 기억시키기로 했다.
		req.getSession().setAttribute("SID", sid);
		
		return view;
	}

}
