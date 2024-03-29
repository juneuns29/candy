package bio.db;

import java.sql.*;
import javax.sql.*;
import javax.naming.*;

/**
 * 이 클래스는 커넥션 풀에 있는 커넥션을 이용해서
 * 데이터베이스 작업을 할 유틸리티 클래스이다.
 * 따라서 bio 프로젝트에서 데이터베이스 작업이 필요하면
 * 이 클래스를 new 시켜서 멤버를 사용하면 된다.
 * 
 * @author	전은석
 * @since	2024/03/27
 * @version	v1.0
 * 				2024/03/27 - 클래스 생성 [ 담당자 : 전은석 ]
 *
 */
public class BioDBCP {
	// 커넥션 풀을 관리할 변수
	public DataSource ds;
	
	public BioDBCP() {
		/*
			이 클래스는 누군가 new 시키면
			context.xml 파일에 등록된 자원을 사용할 수 있도록
			가지고 오도록 한다.
			이처럼 context.xml 파일에 등록된 자원을 가지고 오는 방법을
			JNDI(Java Naming and Directory Interface) 라고 부른다.
		 */
		try {
			// 1. context.xml 에 등록된 자원을 알아낸다.
			InitialContext context = new InitialContext();
			// 2. 그중에서 필요한 자원을 꺼낸다.
			ds = (DataSource) context.lookup("java:/comp/env/jdbc/TestDB");
			/*
				등록된 자원중 필요한 자원을 꺼내는 규칙
					
					java:/comp/env/찾을이름
			 */
			
			// 이 행을 실행하게 되면 필요한 커넥션 풀이 확보되어있는 상태가 된다.
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 커넥션을 요청하면 커넥션풀에서 커넥션 한개를 꺼내주는 함수
	 */
	public Connection getCon() {
		Connection con = null;
		try {
			con = ds.getConnection();
			// 커넥션을 모아놓은 객체가 ds이므로 
			// 그곳에서 하나 꺼내서 반환해준다.
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return con;
	}
	
	/**
	 * Statement 생성해서 반환해주는 함수
	 */
	public Statement getStmt(Connection con) {
		Statement stmt = null;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return stmt;
	}
	
	/**
	 * PreparedStatement 만들어서 반환해주는 함수
	 */
	public PreparedStatement getPstmt(String sql, Connection con) {
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(sql, 
										ResultSet.TYPE_SCROLL_INSENSITIVE, 
										ResultSet.CONCUR_READ_ONLY);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return pstmt;
	}
	
	/**
	 * 사용이 끝난 자원 반환해주는 함수
	 */
	public void close(Object o) {
		try {
			if(o instanceof Connection) {
				((Connection) o).close();
			} else if(o instanceof Statement) {
				((Statement) o).close();
			} else if(o instanceof PreparedStatement) {
				((PreparedStatement) o).close();
			} else if(o instanceof ResultSet) {
				((ResultSet) o).close();
			}
		} catch(Exception e) {}
	}

}
