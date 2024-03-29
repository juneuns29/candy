package bio.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 이 클래스는 bio 프로젝트 메인페이지 폼보기 요청 처리 클래스
 * @author	전은석
 * @since	2024.03.28
 * @version	v.1.0
 * 			2024.03.28 - 요청 처리 클래스 작성 [ 작성자: 전은석 ]
 */
public class CandyMain implements CandyInter {

	@Override
	public String exec(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setAttribute("isRedirect", false);
		// 뷰
		String view = "main";
		return view;
	}

}
