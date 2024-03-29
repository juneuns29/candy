package bio.dao;

import java.sql.*;

import bio.db.*;
import bio.sql.*;
import bio.vo.*;

/**
 * 이 클래스는 회원관련 데이터베이스 작업을 전담해서 처리할 클래스
 * @author	전은석
 * @since	2024.03.28
 * @version v.1.0
 * 			2024.03.28 - 로그인처리 함수 제작 [ 담당자: 전은석 ]
 * 			
 * 			2024.03.29 - 아이디 카운트 조회
 * 						 회원데이터 입력
 * 												[ 담당자: 전은석 ]
 *
 */
public class MemberDao {
	private BioDBCP db;
	private Connection con;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private MemberSQL mSql;
	private String sql;
	
	public MemberDao() {
		// 데이터베이스 작업 준비
		db = new BioDBCP();
		mSql = new MemberSQL();
	}
	
	/**
	 * 로그인 처리 데이터베이스작업 전담 처리함수
	 */
	public int getLogin(String id, String pw) {
		// 할일
		// 반환값 변수
		int cnt = 0;
		// 커넥션 준비
		con = db.getCon();
		// 질의명령 준비
		sql = mSql.getSQL(mSql.SEL_LOGIN);
		// 명령전달도구 준비
		pstmt = db.getPstmt(sql, con);
		try{
			// 질의명령 완성하고
			pstmt.setString(1, id);
			pstmt.setString(2, pw);
			// 질의명령 보내고 결과받고
			rs = pstmt.executeQuery();
			// 결과 꺼내고
			// 결과가 한행이므로 한칸내리고
			rs.next();
			cnt = rs.getInt("cnt");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		// 결과 반환해주고
		return cnt;
	}
	
	/**
	 * 아이디 카운트 조회 전담 함수
	 */
	public int getIdCnt(String id) {
		// 반환값 변수
		int cnt = 0;
		// 커넥션 꺼내오고
		con = db.getCon();
		// 질의명령 가져오고
		sql = mSql.getSQL(mSql.SEL_ID_CNT);
		// 명령전달도구 준비하고
		pstmt = db.getPstmt(sql, con);
		try {
			// 질의명령 완성하고
			pstmt.setString(1, id);
			// 질의명령 보내고 결과 받고
			rs = pstmt.executeQuery();
			// 작업진행 행 한칸 내리고
			rs.next();
			// 데이터 꺼내고
			cnt = rs.getInt("cnt");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(rs);
			db.close(pstmt);
			db.close(con);
		}
		// 반환하고
		return cnt;
	}
	
	/**
	 * 회원 데이터 입력 데이터베이스 작업 전담 처리함수
	 */
	public int addMember(MemberVO vo) {
		// 할일
		// 반환값 변수
		int cnt = 0;
		// 커넥션
		con = db.getCon();
		// 질의명령
		sql = mSql.getSQL(mSql.ADD_MEMB);
		// 명령전달도구
		pstmt = db.getPstmt(sql, con);
		try {
			// 질의명령완성
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getId());
			pstmt.setString(3, vo.getPw());
			pstmt.setString(4, vo.getMail());
			pstmt.setString(5, vo.getTel());
			pstmt.setString(6, vo.getGen());
			pstmt.setInt(7, vo.getAno());
			// 질의명령 보내고 결과받고
			cnt = pstmt.executeUpdate();
			/*
				executeUpdate() 
				==> 변경된 행의 수 반환
					
					따라서 insert 명령의 결과는
						0 - 실패한 경우
						1 - 성공한 경우
			 */
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			db.close(pstmt);
			db.close(con);
		}
		// 결과 내보내고
		return cnt;
	}
}
