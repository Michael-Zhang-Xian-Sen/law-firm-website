package nju.software.model;

public class Role {
    private String nickname;
    private String true_name;
    private String part;
    private String module;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTrue_name() {
        return true_name;
    }

    public void setTrue_name(String true_name) {
        this.true_name = true_name;
    }

    public String getPart() {
        return part;
    }

    public void setPart(String part) {
        this.part = part;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    @Override
    public String toString() {
        return "Role{" +
                "nickname='" + nickname + '\'' +
                ", true_name='" + true_name + '\'' +
                ", part='" + part + '\'' +
                ", module='" + module + '\'' +
                '}';
    }
}
