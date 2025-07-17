package org.example.dao;
import org.example.models.Members;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection connection;


    public MemberDAO(Connection connection) {
    this.connection = connection;
    }

    public boolean insertMember(Members member) throws SQLException {
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                String createTable = "CREATE TABLE IF NOT EXISTS Members (id INTEGER, name TEXT, gender TEXT, age INTEGER, loaned_book_id INTEGER)";
                statement.executeUpdate(createTable);

                String insertSQL = "INSERT INTO Members (id, name, gender, age) VALUES (?, ? , ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);

                preparedStatement.setInt(1, member.getMemberId());
                preparedStatement.setString(2, member.getMemberName());
                preparedStatement.setString(3, String.valueOf(member.getMemberGender()));
                preparedStatement.setInt(4, member.getMemberAge());
                preparedStatement.executeUpdate();
                preparedStatement.close();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Connection Failed! Check output console");
            return false;
        }
    }

    public boolean updateMember(Members member) throws SQLException {
        if (connection != null) {
            try {
                String updateSQL = "UPDATE Members SET name = ?, gender = ?, age = ?  WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(updateSQL);
                preparedStatement.setString(1, member.getMemberName());
                preparedStatement.setString(2, member.getMemberGender());
                preparedStatement.setInt(3, member.getMemberAge());
                preparedStatement.setInt(4, member.getMemberId());
                int rows = preparedStatement.executeUpdate();
                if (rows > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Connection Failed! Check output console");
            return false;
        }
    }

    public boolean deleteMember(int memberId) throws SQLException {
        if (connection != null) {
            try {
                String deleteSQL = "DELETE FROM Members WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(deleteSQL);
                preparedStatement.setInt(1, memberId);
                int rows = preparedStatement.executeUpdate();

                if (rows > 0) {
                    return true;
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            System.out.println("Connection Failed! Check output console");
            return false;
        }
    }

    public List<Members> findByName(String memberName) throws SQLException {
        List<Members> members = null;
        if (connection != null) {
            try {
                String selectSQL = "SELECT id, name, gender, age FROM Members WHERE name LIKE ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setString(1, "%" + memberName + "%");
                ResultSet resultSet = preparedStatement.executeQuery();

                members = new ArrayList<>();
                while (resultSet.next()) {
                    int memberId = resultSet.getInt("id");
                    String mainMemberName = resultSet.getString("name");
                    String memberGender = resultSet.getString("gender");
                    int memberAge = resultSet.getInt("age");

                    members.add(new Members(memberId, mainMemberName, memberGender, memberAge));

                }
                if (members.isEmpty()) {
                    System.out.println("name: " + memberName + " not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
        return members;
    }

    public Members findById(int memberId) throws SQLException {
        if (connection != null) {
            try {
                String selectSQL = "SELECT name, gender, age FROM Members WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                preparedStatement.setInt(1, memberId);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String memberName = resultSet.getString("name");
                    String memberGender = resultSet.getString("gender");
                    int memberAge = resultSet.getInt("age");

                    Members member = new Members(memberId, memberName, memberGender, memberAge);
                    member.setMemberName(memberName);
                    member.setMemberGender(memberGender);
                    member.setMemberAge(memberAge);

                    return member;
                } else {
                    System.out.println("ID: " + memberId + " Data not found.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
        return null;
    }

    public List<Members> findAll () throws SQLException {
        List<Members> members = null;
        if (connection != null) {
            try {
                String selectSQL = "SELECT * FROM Members";
                PreparedStatement preparedStatement = connection.prepareStatement(selectSQL);
                ResultSet resultSet = preparedStatement.executeQuery();

                members = new ArrayList<>();
               while (resultSet.next()) {
                    int memberId = resultSet.getInt("id");
                    String memberName = resultSet.getString("name");
                    String memberGender = resultSet.getString("gender");
                    int memberAge = resultSet.getInt("age");

                    members.add(new Members(memberId, memberName, memberGender, memberAge));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Connection Failed! Check output console");
        }
        return members;
    }
}

