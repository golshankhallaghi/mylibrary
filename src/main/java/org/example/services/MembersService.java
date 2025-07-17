package org.example.services;

import org.example.dao.MemberDAO;
import org.example.exception.MyConnectionException;
import org.example.exception.MySQLException;
import org.example.models.Members;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MembersService {
    Connection connection;
    private final MemberDAO memberDAO;

    public MembersService(Connection connection) throws MyConnectionException {
        try {
            this.connection = connection;
            this.memberDAO = new MemberDAO(connection);
        } catch (Exception e) {
            throw new MyConnectionException("error connecting to member dao" + e.getMessage());
        }
    }

    public void addMember(int memberId, String memberName, String memberGender, int memberAge) throws MySQLException {
        Members member = new Members(memberId, memberName, memberGender, memberAge);
        boolean success = memberDAO.insertMember(member);
        if (success) {
            System.out.println("Member: " + memberId + " added successfully");
        } else {
            System.out.println("Failed to add member: " + memberId);
        }
    }

    public void deleteMember(int memberId) throws MySQLException {
        boolean success = memberDAO.deleteMember(memberId);
        if (success) {
            System.out.println("Member: " + memberId + "deleted successfully");
        } else {
            System.out.println("Failed to delete member: " + memberId);
        }
    }
    public void editMember(int memberId, String memberName, String memberGender, int memberAge) throws MySQLException {
        Members member = new Members();
        member.setMemberId(memberId);
        member.setMemberName(memberName);
        member.setMemberGender(memberGender);
        member.setMemberAge(memberAge);
        boolean success = memberDAO.updateMember(member);
        if (success) {
            System.out.println("Member: " + memberId + "edited successfully");
        } else {
            System.out.println("Failed to edit member: " + memberId);
        }
    }

    public void searchMember(String memberName) throws MySQLException {
        List<Members> members = memberDAO.findByName(memberName);
        if (members.isEmpty()) {
            System.out.println("name: " + memberName+ "Member not found");
        } else {
            for (Members member : members) {
                System.out.println("id: " + member.getMemberId() + " name: " + member.getMemberName() + " gender: " + member.getMemberGender() + " age: " + member.getMemberAge());
            }
        }
    }

    public void showMember() throws MySQLException {
        List<Members> members = memberDAO.findAll();
        if (members.isEmpty()) {
            System.out.println("Member not found");
        } else {
            for (Members member : members) {
                System.out.println("id: " + member.getMemberId() + " name: " + member.getMemberName() + " gender: " + member.getMemberGender() + " age: " + member.getMemberAge());
            }
        }
    }
}

