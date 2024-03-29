package bio.sql;

public class MemberSQL {
	public final int SEL_LOGIN 		= 1001;
	public final int SEL_ID_CNT 	= 1002;
	
	public final int ADD_MEMB 		= 3001;
	
	public String getSQL(int code) {
		StringBuffer buff = new StringBuffer();
		
		switch(code) {
		case SEL_LOGIN:
			buff.append("SELECT ");
			buff.append("	COUNT(*) cnt ");
			buff.append("FROM ");
			buff.append("	member ");
			buff.append("WHERE ");
			buff.append("	id = ? ");
			buff.append("	AND pw = ? ");
			break;
		case SEL_ID_CNT:
			buff.append("SELECT ");
			buff.append("	COUNT(*) cnt ");
			buff.append("FROM ");
			buff.append("	member ");
			buff.append("WHERE ");
			buff.append("	id = ? ");
			break;
		case ADD_MEMB:
			buff.append("INSERT INTO ");
			buff.append("	member(mno, name, id, pw, mail, tel, gen, avatar) ");
			buff.append("VALUES( ");
			buff.append("	mnoseq.NEXTVAL, ?, ?, ?, ?, ?, ?, ? ");
			buff.append(") ");
			break;
		}
		
		return buff.toString();
	}
}
