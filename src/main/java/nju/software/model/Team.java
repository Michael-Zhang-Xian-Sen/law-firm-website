package nju.software.model;

public class Team {
    private String leader_name;
    private String team_name;

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

    @Override
    public String toString() {
        return "Team{" +
                "leader_name='" + leader_name + '\'' +
                ", team_name='" + team_name + '\'' +
                '}';
    }
}
