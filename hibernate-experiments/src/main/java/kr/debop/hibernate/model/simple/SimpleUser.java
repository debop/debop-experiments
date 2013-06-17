package kr.debop.hibernate.model.simple;

import com.google.common.base.Objects;
import kr.debop4j.core.tools.HashTool;
import kr.debop4j.data.model.AnnotatedEntityBase;

import javax.persistence.*;

/**
 * kr.debop.hibernate.model.simple.SimpleUser
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 6. 17. 오후 10:00
 */
@Entity
@Table( name = "`SimpleUser`" )
public class SimpleUser extends AnnotatedEntityBase {

    @Id
    @GeneratedValue
    private Long id;

    @Column( name = "UserName", length = 128, nullable = false )
    private String name;


    @Override
    public int hashCode() {
        if (isPersisted())
            return HashTool.compute(id);
        return HashTool.compute(name);
    }

    @Override
    protected Objects.ToStringHelper buildStringHelper() {
        return super.buildStringHelper()
                .add("id", id)
                .add("name", name);
    }

    private static final long serialVersionUID = -9070595098465309055L;
}
