package fcmb.com.good.security;

import fcmb.com.good.model.entity.user.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class GroupUserDetails implements UserDetails {

    private String usersCategory;
    private String userName;
    private UUID uuid;
    private String password;
    private String name;
    private String email;
    private String phone;
    private String photo;
    private String address;
    private String gender;
    private String city;
    private String roles;

    private boolean isActive;
    private List<GrantedAuthority> authorities;

    public GroupUserDetails(Users user) {
        this.userName = user.getUsername();
        this.password = user.getPassword();
        this.usersCategory = user.getUsersCategory();
        this.name = user.getName();
        this.phone = user.getPhone();
        this.photo = user.getPhoto();
        this.address = user.getAddress();
        this.gender = user.getGender();
        this.city = user.getCity();
        this.email = user.getEmail();
        this.uuid = user.getUuid();
        this.roles = user.getRoles();
        this.isActive = user.isActive();
        this.authorities = Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getUsersCategory() {return usersCategory;}
    public String getName() {return name;}
    public String getEmail() {
        return email;
    }
    public String getPhone() {
        return phone;
    }
    public String getPhoto() {return photo;}
    public String getAddress() {
        return address;
    }
    public String getGender() {
        return gender;
    }
    public String getCity() {
        return city;
    }
    public String getRoles() {
        return roles;
    }
    public UUID getUuid() {
        return uuid;
    }


    @Override
    public String getPassword() {return password;}

    @Override
    public String getUsername() {
        return userName;
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
