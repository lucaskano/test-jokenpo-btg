package br.com.btg.jokenpo.entity;

import javax.persistence.Entity;
import java.util.Objects;

/**
 * OBS.: Não será utilizado a geração de ID para a entidade pois não a aplicação
 * não terá banco de dados, por esse motivo foi optado não inserir esse atributo.
 *
 * */


@Entity
public class Player {

    private String name;

    public Player(){

    }

    public Player(String name) {
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
        Player player = (Player) o;
        return Objects.equals(name, player.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
