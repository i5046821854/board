package com.springExec.board.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "email", unique = true),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy")
})
@Entity
public class UserAccount extends AuditingFields{

    @Id
    @Column(length = 50)
    private String userId;
    @Setter @Column(nullable = false) private String userPassword;
    @Setter @Column(length = 100) private String nickname;
    @Setter @Column(length = 100) private String email;
    @Setter private String memo;

    protected UserAccount() {
    }

    private UserAccount(String userId, String userPassword, String nickname, String email, String memo) {
        this.userId = userId;
        this.userPassword = userPassword;
        this.nickname = nickname;
        this.email = email;
        this.memo = memo;
    }

    public static UserAccount of(String userId, String userPassword, String nickname, String email, String memo)
    {
        return new UserAccount(userId, userPassword, nickname, email, memo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAccount that = (UserAccount) o;
        return userId!= null && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
