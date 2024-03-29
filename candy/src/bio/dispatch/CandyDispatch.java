package bio.dispatch;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import bio.controller.*;

/**
 * 이 클래스는 .ania로 클라이언트가 요청을 하면
 * 요청내용을 분석해서 실행해야할 클래스를 선택해서 실행시킬 
 * (Dispatch시킬) 서블릿 클래스이다.
 * 
 * @author 전은석
 * @since	2024/03/28
 * @version	V.1.0
 * 			2024.03.28 - dispatch servelt 작성 [ 담당자 : 전은석 ]
 */
@WebServlet("*.candy")
public class CandyDispatch extends HttpServlet {
	
	/*
		할일 ]
			1. 요청내용과 클래스의 인스턴스를 매핑할 맵이 필요하다.
				우리는 요청내용과 해당 요청에 처리에 필요한 클래스 경로를
				Properties 파일에 저장해 놓았다.
				Properties 는 맵의 일종이므로
				키값을 알면 처리에 필요한 클래스 경로를 알아낼 수 있다.
	 */
	public HashMap<String, CandyInter> map;
	
	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		/*
			이 함수는 서버가 실행된 이후
			요청 내용이 .ania로 요청되는 경우
			처음 한번만 실행되는 함수이다.
			
			따라서 처음 .ania로 요청이 오면
			우리 준비해둔 ania.properties 파일을 읽어서
			요청들의 실행할 클래스 경로를 이용해서
			맵에 요청내용과 그 클래스의 객체를 저장해 놓아야 한다.
		 */
		
		// 파일에 저장된 데이터를 읽어야 하므로...
		// Properties 준비
		Properties prop = new Properties();
		
		// 스트림 준비
		FileInputStream fin = null;
		try {
			// 현재 실행중인 이 클래스의 경로 알아내고
			String path = this.getClass().getResource("").getPath();
			// 스트림 만들고
			fin = new FileInputStream(path + "../resources/candy.properties");
			// prop에 파일의 데이터 기억
			prop.load(fin);
			
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				fin.close();
			} catch(Exception e) {}
		}
		
		// map에 요청키값과 실행 클래스를 기억시킨다.
		map = new HashMap<String, CandyInter>();
		// 키값만 모두 꺼내온다.
		Enumeration en = prop.keys();
		// 키값이 몇개가 있는지 알 수 없으므로 
		while(en.hasMoreElements()) {
			// 하나씩 꺼내서
			String key = (String) en.nextElement();
			// 클래스 경로 꺼내고
			String path = prop.getProperty(key);
			// 꺼낸 경로의 클래스를 실행가능한 클래스로 변환해준다.
			try {
				Class tmp = Class.forName(path); // 클래스 정보 꺼내오고
				// 인스턴스 만들고
				CandyInter val = (CandyInter) tmp.newInstance();
				
				map.put(key, val);
			} catch(Exception e) {}
		}
		
		
	}


	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	/*
	 * 실제 요청 내용에 맞는 처리를 담당하는 함수
	 * .ania로 요청이 올때마다 실행되는 함수
	 */
	protected void service(HttpServletRequest req, HttpServletResponse resp) 
												throws ServletException, IOException {
		// 할일
		// 1. 요청 내용을 알아낸다.
		String full = req.getRequestURI(); // http://localhost/member/login.ania
		// 2. 도메인을 알아낸다.
		String domain = req.getContextPath(); // http://localhost
		// 3. 실제요청내용만 알아낸다.
		String realReq = full.substring(domain.length()); // ==> /member/login.ania
		// 4. 실제 실행해야할 클래스 꺼내오고 실행하고
		CandyInter val = map.get(realReq);
		
		// 실행전 요청객체에 속성추가(비동기 통신 처리를 위해서...)
		Boolean bool = false;
		
		// 응답 객체 인코딩 처리
		resp.setCharacterEncoding("UTF-8");
		
		// 응답 방식 속성 입력
		req.setAttribute("isRedirect", bool);
		// 5. 클래스의 실행결과 처리하고
		String view = val.exec(req, resp);
	
		// 요청객체의 속성값 꺼내고
		bool = (Boolean) req.getAttribute("isRedirect");
		
		if(bool ==  null) {
			// 비동기 통신 처리해야할 경우
			PrintWriter pw = resp.getWriter();
			pw.print(view);
		} else if(bool) {
			// 리다이렉트 처리해야할 경우
			resp.sendRedirect(view);
		} else if(!bool) {
			// 포워딩 처리해야하는 경우
			String prefix = "/WEB-INF/views/";
			String suffix = ".jsp";
			RequestDispatcher rd = req.getRequestDispatcher(prefix + view + suffix);
			rd.forward(req, resp);
		}
	}

}
