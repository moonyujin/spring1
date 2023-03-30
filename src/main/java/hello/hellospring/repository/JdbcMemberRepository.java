package hello.hellospring.repository;
import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
public class JdbcMemberRepository implements MemberRepository {
    private final DataSource dataSource; // dataSource를 스프링으로 주입받기 위해 사용한다.
    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource; }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)"; // member에 내용을 추가하기 위해 넣은 것이다.
        Connection conn = null;
//        Connection은 자바와 DB 사이의 길로 볼 수 있다.
        PreparedStatement pstmt = null;
//        PreparedStatement은 DB에서 동일하거나 비슷한 데이터베이스 문을 높은
//        효율성으로 반복적으로 실행하기 위해 사용되는 기능이다.
        ResultSet rs = null;
//        ResultSet은 결과값을 저장할 수 있게 해준다.
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
//            Statement은 프로그램 내에서 하나의 동작을 기술하는 것이다.
//            RETURN_GENERATED_KEYS는 생성된 키가 검색 가능하게 되는 것을 나타내는 정수이다.

            pstmt.setString(1, member.getName());
            pstmt.executeUpdate();
//            executeUpdate는 데이터베이스에서 데이터를 추가, 삭제, 수정하는 SQL문을 실행한다.
            rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                member.setId(rs.getLong(1));
            } else {
                throw new SQLException("id 조회 실패");
//                SQLException은 데이터베이스 컨텍스트에서 발생하는 실패에 관련되는 추가 정보를 제공합니다.
            }
            return member; // member를 반환해준다.
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?"; // member를 조회한다.
        Connection conn = null;
        PreparedStatement pstmt = null; ResultSet rs = null;
//        ResultSet은 명령에 대한 반환값
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
//                empty는 문자열의 길이가 0임을 의미한다.
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public List<Member> findAll() {
        String sql = "select * from member"; // member를 조회한다.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery(); List<Member> members = new ArrayList<>();
//            ArrayList는 크기를 조정할 수 있는 배열이다.
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
            }
            return members;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?"; // member, name을 조회한다.
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } return Optional.empty();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
//        DataSourceUtils.getConnection()는 트랜잭션 동기화 매니저가 관리하는 커넥션이 있으면 해당 커넥션을 반환한다.
//        커넥션은 어떤 것 또는 누군가와 관련이 있는 상태라는 뜻이다.

    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs)
    {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
//        releaseConnection은 한 프로세스 내의 한 쓰레드가 소유하고 있던
//        '데이터베이스 커넥션'을 돌려준다고 이해하시면 될것같습니다.
    }
}