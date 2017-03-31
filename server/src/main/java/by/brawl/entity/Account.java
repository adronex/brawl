package by.brawl.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

/**
 * Default class description.
 *
 * @author P.Sinitsky.
 *         Created on 25.03.2017.
 */
@Entity
public class Account extends IdEntity implements UserDetails {

    private String email;
    private String password;

    @OneToMany(mappedBy = "owner")
    private Set<Hero> heroes = new LinkedHashSet<>();
    @OneToMany(mappedBy = "owner")
    private Set<Squad> squads = new LinkedHashSet<>();

    @ManyToMany
    @JoinTable(name = "account_spell",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns= @JoinColumn(name = "spell_id"))
    private Set<Spell> availableSpells = new LinkedHashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Set<Spell> getAvailableSpells() {
        return availableSpells;
    }

    public Set<Squad> getSquads() {
        return squads;
    }

    public Set<Hero> getHeroes() {
        return heroes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        Account account = (Account) o;
        return Objects.equals(getId(), account.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), email, password, heroes, squads, availableSpells);
    }

    @Override
    public String toString() {
        return "Account{" +
                "email='" + email + '\'' +
                '}';
    }
}
