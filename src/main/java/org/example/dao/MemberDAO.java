package org.example.dao;
import org.example.exception.MyConnectionException;
import org.example.exception.MySQLException;
import org.example.models.Members;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MemberDAO {
    private Connection connection;


    public MemberDAO(Connection connection) throws MyConnectionException {
        if (connection == null) {
            throw new MyConnectionException("connection is null");
        }
        this.connection = connection;
    }

    public boolean insertMember(Members member) throws MySQLException {
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
            throw new MySQLException("Error insert member" + e.getMessage());
        }
    }

    public boolean updateMember(Members member) throws MySQLException {
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
            throw new MySQLException("Error update member" + e.getMessage());
        }
    }

    public boolean deleteMember(int memberId) throws MySQLException {
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
            throw new MySQLException("Error delete member" + e.getMessage());
        }
    }

    public List<Members> findByName(String memberName) throws MySQLException {
        List<Members> members = null;
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

        } catch (SQLException e) {
            throw new MySQLException("Error find member by name" + e.getMessage());
        }
        return members;
    }

    public Members findById(int memberId) throws MySQLException {
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
            throw new MySQLException("Error find member by id" + e.getMessage());
        }
        return null;
    }

    public List<Members> findAll () throws MySQLException {
        List<Members> members = null;
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
            throw new MySQLException("Error find all member" + e.getMessage());
        }
        return members;
    }
}

