package org.example.services;

import org.example.dao.MemberDAO;
import org.example.models.Members;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class MembersService {
    Connection connection;
    private final MemberDAO memberDAO;

    public MembersService(Connection connection) {
        this.connection = connection;
        this.memberDAO = new MemberDAO(connection);
    }

    public void addMember(int memberId, String memberName, String memberGender, int memberAge) {
        if (memberDAO == null) {
            throw new IllegalStateException("MemberDAO not initialized");
        }
        Members member = new Members(memberId, memberName, memberGender, memberAge);
        try {
            boolean success = memberDAO.insertMember(member);
            if (success) {
                System.out.println("Member: " + memberId + " added successfully");
            } else {
                System.out.println("Failed to add member: " + memberId);
            }
        } catch (SQLException e) {
            System.err.println("Error inserting member: " + e.getMessage());
        }
    }

    public void deleteMember(int memberId) {
        try {
            boolean success = memberDAO.deleteMember(memberId);
            if (success) {
                System.out.println("Member: " + memberId + "deleted successfully");
            } else {
                System.out.println("Failed to delete member: " + memberId);
            }
        } catch (SQLException e) {
            System.err.println("Error deleting member: " + e.getMessage());
        }
    }

    public void editMember(int memberId, String memberName, String memberGender, int memberAge) {
        Members member = new Members();
        member.setMemberId(memberId);
        member.setMemberName(memberName);
        member.setMemberGender(memberGender);
        member.setMemberAge(memberAge);
        try {
            boolean success = memberDAO.updateMember(member);
            if (success) {
                System.out.println("Member: " + memberId + "edited successfully");
            } else {
                System.out.println("Failed to edit member: " + memberId);
            }
        } catch (SQLException e) {
            System.err.println("Error editing member: " + e.getMessage());
        }
    }

    public void searchMember(String memberName) {
        try {
            List<Members> members = memberDAO.findByName(memberName);
            if (members.isEmpty()) {
                System.out.println("Member not found");
            } else {
                for (Members member : members) {
                    System.out.println("id: " + member.getMemberId() + " name: " + member.getMemberName() + " gender: " + member.getMemberGender() + " age: " + member.getMemberAge());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding member: " + e.getMessage());
        }
    }

    public void showMember() {
        try {
            List<Members> members = memberDAO.findAll();
            if (members.isEmpty()) {
                System.out.println("Member not found");
            } else {
                for (Members member : members) {
                    System.out.println("id: " + member.getMemberId() + " name: " + member.getMemberName() + " gender: " + member.getMemberGender() + " age: " + member.getMemberAge());
                }
            }
        } catch (SQLException e) {
            System.err.println("Error finding member: " + e.getMessage());
        }
    }
}

