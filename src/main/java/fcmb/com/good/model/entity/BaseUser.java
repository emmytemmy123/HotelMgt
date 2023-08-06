package fcmb.com.good.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Getter
@Setter
public class BaseUser extends BaseEntity {

    private String name;
    private String email;
    private String phone;
    private String address;
    private String photo;
    private String gender;
    private String country;
    private String city;
    private String username;
    private String password;
    private String roles;
    private boolean active;


}
