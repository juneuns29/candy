package bio.controller.member;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bio.controller.CandyInter;

public class JoinProc implements CandyInter {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 할일
		// 응답방식 설정
		req.setAttribute("isRedirect", true);
		// 뷰
		String view = "/main.candy";
		
		// 파라미터 받고
		String name = req.getParameter("name");
		String id = req.getParameter("id");
		String pw = req.getParameter("pw");
		String mail = req.getParameter("mail");
		String tel = req.getParameter("tel");
		String gen = req.getParameter("gen");
		String avt = req.getParameter("avt");
		int avatar = Integer.parseInt(avt);
		
		// 데이터베이스 작업하고 결과 받고
		
		// 결과에따라 뷰 처리해주고
		
		// 뷰 반환해주고
		return view;
	}

}
