package kr.debop.jpa.domain;

import com.google.common.base.Objects;
import kr.debop4j.core.tools.HashTool;
import kr.debop4j.data.model.AnnotatedEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

/**
 * kr.debop.jpa.domain.User
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 5:57
 */
@Entity
@NamedQuery(name = "User.findByUsername", query = "from User u where u.username=?1")
@Table(name = "`USER`")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class User extends AnnotatedEntityBase {

    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String username;

    private String firstname;
    private String lastname;

    @Override
    public int hashCode() {
        return isPersisted() ? HashTool.compute(id)
                : HashTool.compute(username);

    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                    .add("name", username);
    }

    private static final long serialVersionUID = -2984784913856835525L;
}
