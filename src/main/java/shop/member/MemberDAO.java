package shop.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import shop.util.DBManager;

public class MemberDAO {
	private MemberDAO() {}
	private static MemberDAO instance=new MemberDAO();
	public static MemberDAO getInstance() {
		return instance;
	}
	
	Connection conn=null;
	PreparedStatement pstmt=null;
	ResultSet rs=null;
	
	public List<MemberDTO> memberList(){
		List<MemberDTO> list=new ArrayList<>();
		String sql="select * from tbl_member order by first_time";
		try {
			conn=DBManager.getConnection();
			pstmt=conn.prepareStatement(sql);
			rs=pstmt.executeQuery();
			while(rs.next()) {
				MemberDTO dto=new MemberDTO();
				dto.setUserID(rs.getString("userID"));
				dto.setName(rs.getString("name"));
				dto.setAdd(rs.getString("add"));
				dto.setTel(rs.getString("tel"));
				dto.setEmail(rs.getString("email"));
				dto.setSubdate(rs.getString("subdate"));
				//dto.setPass(rs.getString("pass"));

				list.add(dto);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return list;
	}

	
	public int useridSelect(String userid) {
		int count=0;
		String sql="select count(*) from tbl_member where userid=?";
		try {
			conn=DBManager.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			if(rs.next()) {
				count=rs.getInt(1);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return count;
	}
	
	//로그인
	public int memberLogin(String userid, String passwd) {
		String sql="select passwd from tbl_member where userid=?";
		int row=0;
		try {
			conn=DBManager.getConnection();
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, userid);
			rs=pstmt.executeQuery();
			if(rs.next()) { //아이디가 존재하는 경우
				String dbpass=rs.getString("passwd");
				if(dbpass.equals(passwd)) {//로그인에 성공하면 최근접속일자 지정
					sql="update tbl_member set last_time=sysdate where userid=?";
					pstmt=conn.prepareStatement(sql);
					pstmt.setString(1, userid);
					pstmt.executeUpdate();
					row=1;
				}else { //비밀번호가 다른 경우
					row=0;
				}
			}else { //아이디가 없는 경우
				row=-1;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			DBManager.close(conn, pstmt, rs);
		}
		return row;
	}
	
}
