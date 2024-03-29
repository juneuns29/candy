package bio.controller.member;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import bio.controller.*;
import bio.dao.*;
import bio.vo.*;

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
		
		// VO에 데이터 담고
		MemberVO vo = new MemberVO();
		vo.setName(name);
		vo.setId(id);
		vo.setPw(pw);
		vo.setMail(mail);
		vo.setTel(tel);
		vo.setGen(gen);
		vo.setAno(avatar);
		
		// 데이터베이스 작업하고 결과 받고
		MemberDao mDao = new MemberDao();
		int cnt = mDao.addMember(vo);
		// 결과에따라 뷰 처리해주고
		if(cnt != 1) {
			// 회원가입에 실패한 경우
			view = "/member/join.candy";
		}
		
		// 뷰 반환해주고
		return view;
	}

}
