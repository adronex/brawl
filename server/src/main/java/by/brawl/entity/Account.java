package by.brawl.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    private List<Character> characters;
    @OneToMany(mappedBy = "owner")
    private List<Squad> squads;

    @ManyToMany
    @JoinTable(name = "account_spell",
            joinColumns = @JoinColumn(name = "spell_id"),
            inverseJoinColumns= @JoinColumn(name = "account_id"))
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
}
