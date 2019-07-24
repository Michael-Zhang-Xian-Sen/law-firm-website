package nju.software.model;

public class Team {
    private String leader_name;
    private String team_name;
    private String mobile_phone;
    private String email;

    public String getLeader_name() {
        return leader_name;
    }

    public void setLeader_name(String leader_name) {
        this.leader_name = leader_name;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getMobile_phone() {
        return mobile_phone;
    }

    public void setMobile_phone(String mobile_phone) {
        this.mobile_phone = mobile_phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Team{" +
                "leader_name='" + leader_name + '\'' +
                ", team_name='" + team_name + '\'' +
                ", mobile_phone='" + mobile_phone + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
