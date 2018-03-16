package com.cafe24.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cafe24.mysite.vo.BoardVo;
import com.cafe24.mysite.vo.UserVo;

public class BoardDao {
	private static final int LIST_PAGE_SIZE = 1000;
	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://localhost/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch(ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
		return conn;
	}
	
	public boolean delete(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			String sql = "delete from board where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());
			int count = pstmt.executeUpdate();
			result = (count==1);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	public boolean insert(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql1 = "select max(group_no)+1 from board";
			String sql2 = "insert into board values(null, ?, ?, ?, 0, 0, now(), 0, ?)";
			pstmt = conn.prepareStatement(sql1);
			rs = pstmt.executeQuery();
			Long max = 0L;
			if(rs.next()) {
				max = rs.getLong(1);
			}
			rs.close();
			pstmt.close();
			conn.close();
			
			conn = getConnection();
			pstmt = conn.prepareStatement(sql2);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, max);
			pstmt.setLong(4, vo.getUserNo());
			
			int count = pstmt.executeUpdate();
			result = (count==1);
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null & !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public List<Map<String, BoardVo>> getList(int page) {
		List<Map<String, BoardVo>> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select "
					       + "board.no, "
					       + "title, "
					       + "content, "
					       + "group_no, "
					       + "order_no, "
					       + "depth, "
					       + "reg_date, "
					       + "views, "
					       + "user_no, "
					       + "name "
					   + "from board "
					   + "inner join `user` on user_no = `user`.no "
					   + "order by group_no desc, order_no asc "
					   + "limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, LIST_PAGE_SIZE*(page-1));
			pstmt.setInt(2, LIST_PAGE_SIZE);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Long groupNo = rs.getLong(4);
				Long orderNo = rs.getLong(5);
				Long depth = rs.getLong(6);
				String regDate = rs.getString(7);
				Long views = rs.getLong(8);
				Long userNo = rs.getLong(9);
				
				BoardVo vo = new BoardVo(
						no, title, content, groupNo,
						orderNo, depth, regDate, views, userNo);
				String name = rs.getString(10);
				Map<String, BoardVo> map = new HashMap<>();
				map.put(name, vo);
				result.add(map);
//				result.put(vo, name);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null & !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public BoardVo getBoard(Long no) {
		BoardVo result = new BoardVo();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			String sql = "select title, content, user_no from board where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				Long userNo = rs.getLong(3);
				result.setNo(no);
				result.setTitle(title);
				result.setContent(content);
				result.setUserNo(userNo);
			}
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null & !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
