package org.example.models;

public class Members {
    private int memberId;
    private String memberName;
    private String memberGender;
    private int memberAge;

    public Members() {
    }

    public Members(int memberId, String name, String gender, int age) {
        this.memberId = memberId;
        this.memberName = name;
        this.memberGender = gender;
        this.memberAge = age;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getMemberGender() {
        return memberGender;
    }

    public void setMemberGender(String memberGender) {
        this.memberGender = memberGender;
    }

    public int getMemberAge() {
        return memberAge;
    }

    public void setMemberAge(int memberAge) {
        this.memberAge = memberAge;
    }
}
