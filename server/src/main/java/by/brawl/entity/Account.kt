package by.brawl.entity

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.*
import javax.persistence.*

@Entity
class Account : IdEntity(), UserDetails {

    private lateinit var email: String
    private lateinit var password: String

    @OneToMany(mappedBy = "owner")
    val heroes: Set<Hero> = LinkedHashSet()
    @OneToMany(mappedBy = "owner")
    val squads: Set<Squad> = LinkedHashSet()

    @ManyToMany
    @JoinTable(name = "account_spell",
            joinColumns = arrayOf(JoinColumn(name = "account_id")),
            inverseJoinColumns = arrayOf(JoinColumn(name = "spell_id")))
    val availableSpells: Set<Spell> = LinkedHashSet()

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf()
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Account) return false
        val account = other as Account?
        return id == account!!.id
    }

    override fun hashCode(): Int {
        return Objects.hash(id, email, password)
    }

    override fun toString(): String {
        return "Account{" +
                "email='" + email + '\'' +
                '}'
    }

}