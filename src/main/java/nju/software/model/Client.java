package nju.software.model;

public class Client {
    private int client_num;
    private String client_short_name;

    private String client_full_name;

    public int getClient_num() {
        return client_num;
    }

    public void setClient_num(int client_num) {
        this.client_num = client_num;
    }

    public String getClient_short_name() {
        return client_short_name;
    }

    public void setClient_short_name(String client_short_name) {
        this.client_short_name = client_short_name;
    }

    public String getClient_full_name() {
        return client_full_name;
    }

    public void setClient_full_name(String client_full_name) {
        this.client_full_name = client_full_name;
    }

    @Override
    public String toString() {
        return "Client{" +
                "client_num=" + client_num +
                ", client_short_name='" + client_short_name + '\'' +
                ", client_full_name='" + client_full_name + '\'' +
                '}';
    }
}
