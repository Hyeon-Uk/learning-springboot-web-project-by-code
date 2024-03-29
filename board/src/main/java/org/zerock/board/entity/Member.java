package org.zerock.board.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Member extends BaseEntity{
    @Id
    private String email;

    private String password;

    private String name;
}
