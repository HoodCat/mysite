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
	private static final int LIST_PAGE_SIZE = 10;
	public static final long startPage = 1;
	private static long endPage = 1;
	private static long currentPage = 1;
	
	public static long getEndPage() {
		return endPage;
	}

	public static void setEndPage(long endPage) {
		BoardDao.endPage = endPage;
	}

	public static long getCurrentPage() {
		return currentPage;
	}

	public static void setCurrentPage(long currentPage) {
		if(currentPage >= endPage) {
			BoardDao.currentPage = endPage;
			return;
		}
		
		if(currentPage <= startPage) {
			BoardDao.currentPage = startPage;
			return;
		}
		BoardDao.currentPage = currentPage;
		return;
	}

	private Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			String url = "jdbc:mysql://localhost/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
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
			result = (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	private Long getMaxGroupNo() {
		Long max = 0L;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select max(group_no)+1 from board";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			max = 0L;
			if (rs.next()) {
				max = rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null & !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return max;
	}
	
	public long getTotalCount() {
		long result = 0L;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select count(*) from board";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getLong(1);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null & !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private long getTotalCount(String kwd) {
		long result = 0L;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = getConnection();
			String sql = "select count(*) from board where title like ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+kwd+"%");
			rs = pstmt.executeQuery();
			if(rs.next()) {
				rs.getLong(1);
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null & !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
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

		try {
			conn = getConnection();
			String sql = "insert into board values(null, ?, ?, ?, 0, 0, now(), 0, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, getMaxGroupNo());
			pstmt.setLong(4, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	public boolean insertReply(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			updateForReply(vo);
			conn = getConnection();
			String sql = "insert into board values(null, ?, ?, ?, ?, ?, now(), 0, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getGroupNo());
			pstmt.setLong(4, vo.getOrderNo() + 1);
			pstmt.setLong(5, vo.getDepth() + 1);
			pstmt.setLong(6, vo.getUserNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	private void updateForReply(BoardVo vo) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		conn = getConnection();
		String sql = "update board " + 
		             "set order_no=order_no+1 " +
                     "where group_no=? and order_no>?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setLong(1, vo.getGroupNo());
		pstmt.setLong(2, vo.getOrderNo());
		pstmt.executeUpdate();
		if (pstmt != null & !pstmt.isClosed()) {
			pstmt.close();
		}
		if (conn != null & !conn.isClosed()) {
			conn.close();
		}
	}

	public boolean updateBoard(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update board set title=?, content=?, reg_date=now() where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setLong(3, vo.getNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
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
		long totCount = getTotalCount();
		endPage = (totCount%LIST_PAGE_SIZE==0)?
				  (totCount/LIST_PAGE_SIZE):
				  (totCount/LIST_PAGE_SIZE+1);
		endPage = (endPage==0)?1:endPage;
		
		System.out.println(currentPage);
		System.out.println(endPage);
				  
		try {
			conn = getConnection();
			String sql = "select " + "board.no, " + "title, " + "content, " + "group_no, " + "order_no, " + "depth, "
			        + "reg_date, " + "views, " + "user_no, " + "name " + "from board "
			        + "inner join `user` on user_no = `user`.no " + "order by group_no desc, order_no asc "
			        + "limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, LIST_PAGE_SIZE * (page - 1));
			pstmt.setInt(2, LIST_PAGE_SIZE);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Long groupNo = rs.getLong(4);
				Long orderNo = rs.getLong(5);
				Long depth = rs.getLong(6);
				String regDate = rs.getString(7);
				Long views = rs.getLong(8);
				Long userNo = rs.getLong(9);

				BoardVo vo = new BoardVo(no, title, content, groupNo, orderNo, depth, regDate, views, userNo);
				String name = rs.getString(10);
				Map<String, BoardVo> map = new HashMap<>();
				map.put(name, vo);
				result.add(map);
				// result.put(vo, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null & !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public List<Map<String, BoardVo>> getList(int page, String kwd) {
		List<Map<String, BoardVo>> result = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long totCount = getTotalCount(kwd);
		endPage = (totCount%LIST_PAGE_SIZE==0)?
				  (totCount/LIST_PAGE_SIZE):
				  (totCount/LIST_PAGE_SIZE+1);
				  
		try {
			conn = getConnection();
			String sql = "select board.no, title, content, group_no, order_no, depth, reg_date, views, user_no, name "
					   + "from board "
					   + "inner join `user` on user_no = `user`.no "
					   + "where title like ? "
					   + "order by group_no desc, order_no asc "
					   + "limit ?, ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+kwd+"%");
			pstmt.setInt(2, LIST_PAGE_SIZE * (page - 1));
			pstmt.setInt(3, LIST_PAGE_SIZE);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String content = rs.getString(3);
				Long groupNo = rs.getLong(4);
				Long orderNo = rs.getLong(5);
				Long depth = rs.getLong(6);
				String regDate = rs.getString(7);
				Long views = rs.getLong(8);
				Long userNo = rs.getLong(9);

				BoardVo vo = new BoardVo(no, title, content, groupNo, orderNo, depth, regDate, views, userNo);
				String name = rs.getString(10);
				Map<String, BoardVo> map = new HashMap<>();
				map.put(name, vo);
				result.add(map);
				// result.put(vo, name);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null & !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
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
			String sql = "select title, " + "content, " + "group_no, " + "order_no, " + "depth, " + "user_no "
			        + "from board where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				String title = rs.getString(1);
				String content = rs.getString(2);
				Long groupNo = rs.getLong(3);
				Long orderNo = rs.getLong(4);
				Long depth = rs.getLong(5);
				Long userNo = rs.getLong(6);

				result.setNo(no);
				result.setTitle(title);
				result.setContent(content);
				result.setGroupNo(groupNo);
				result.setOrderNo(orderNo);
				result.setDepth(depth);
				result.setUserNo(userNo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null & !rs.isClosed()) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	public boolean updateViews(BoardVo vo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			String sql = "update board set views=views+1 where no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, vo.getNo());

			int count = pstmt.executeUpdate();
			result = (count == 1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null & !pstmt.isClosed()) {
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (conn != null & !conn.isClosed()) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	public boolean updateViews(long no) {
		return updateViews(new BoardVo(no, null, null, null, null, null, null, null, null));
	}
}
