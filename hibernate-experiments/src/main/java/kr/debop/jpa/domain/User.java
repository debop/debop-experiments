package kr.debop.jpa.domain;

import com.google.common.base.Objects;
import kr.debop4j.core.tools.HashTool;
import kr.debop4j.data.model.AnnotatedEntityBase;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * kr.debop.jpa.domain.User
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 18. 오후 5:57
 */
@Entity
@Table(name="`USER`")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
public class User extends AnnotatedEntityBase {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Override
    public int hashCode() {
        return isPersisted() ? HashTool.compute(id)
                : HashTool.compute(name);

    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("name", name);
    }

    private static final long serialVersionUID = -2984784913856835525L;
}
