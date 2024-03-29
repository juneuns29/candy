package bio.controller.member;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import bio.controller.*;
import bio.dao.*;

public class IdCheck implements CandyInter {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 할일
		// 응답방식 셋팅
		req.setAttribute("isRedirect", null);
		/*
			isRedirect
				true : 리다이렉트
				false : 포워딩 처리
				null  : 비동기 통신 처리
		 */
		// 데이터 받고
		String sid = req.getParameter("id");
		// 데이터베이스 작업 결과 받고
		MemberDao mDao = new MemberDao();
		int cnt = mDao.getIdCnt(sid);
		// 응답 문서 만들고
		String view = "YES";
		if(cnt != 0) {
			// cnt == 0은 이미 누군가 사용하는 아이디의 의미
			view = "NO";
		}
		
		return view;
	}

}
