package br.com.btg.jokenpo.dto;

import java.util.Objects;

public class PlayerResponse {

    private String name;

    public PlayerResponse(){

    }

    public PlayerResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerResponse that = (PlayerResponse) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "PlayerResponse{" +
                "name='" + name + '\'' +
                '}';
    }
}
