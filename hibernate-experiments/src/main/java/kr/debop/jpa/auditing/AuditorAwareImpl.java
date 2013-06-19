package kr.debop.jpa.auditing;

import lombok.Setter;
import org.springframework.data.domain.AuditorAware;

/**
 * kr.debop.jpa.auditing.AuditorAwareImpl
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 19. 오후 2:11
 */
public class AuditorAwareImpl implements AuditorAware<AuditableUser> {

    @Setter
    private AuditableUser auditor;

    @Override
    public AuditableUser getCurrentAuditor() {
        return auditor;
    }
}
