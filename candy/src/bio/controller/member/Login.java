package bio.controller.member;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import bio.controller.*;

/**
 * 이 클래스는 bio 프로젝트 로그인 폼보기 요청 처리 클래스
 * @author	전은석
 * @since	2024.03.28
 * @version	v.1.0
 * 			2024.03.28 - 클래스 작성 및 요청 처리 [ 담당자 : 전은석 ]
 *
 */
public class Login implements CandyInter {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 세션에 기억된 SID 라는 키값으로 데이터가 있는지 검사
		// 세션 꺼내고
		HttpSession session = req.getSession();
		// 데이터 꺼내고
		Object sid = session.getAttribute("SID");
		if(sid != null ) {
			// 이미 로그인 되어있는 상태이므로 메인페이지로 돌려보낸다.
			// 이것은 서버에서 클라이언트의 요청을 강제로 바꾸는 작업이다.
			// ==> redirect 처리...
			req.setAttribute("isRedirect", true);
			return "/main.candy";
		}
		
		String view = "member/login";
		req.setAttribute("isRedirect", false);
//		System.out.println("************** ania login");
		return view;
	}

}
