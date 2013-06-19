package kr.debop.jpa.auditing;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractAuditable;

import javax.persistence.Entity;

/**
 * kr.debop.jpa.auditing.AuditableUser
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 2:09
 */
@Entity
public class AuditableUser extends AbstractAuditable<AuditableUser, Long> {

    @Getter
    @Setter
    private String username;
}
