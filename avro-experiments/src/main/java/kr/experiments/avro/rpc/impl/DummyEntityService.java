package kr.experiments.avro.rpc.impl;

import kr.experiments.avro.rpc.Entity;
import kr.experiments.avro.rpc.EntityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.AvroRemoteException;

import java.util.List;

/**
 * kr.experiments.avro.rpc.impl.DummyEntityService
 *
 * @author 배성혁 sunghyouk.bae@gmail.com
 * @since 13. 8. 10. 오후 8:13
 */
@Slf4j
public class DummyEntityService implements EntityService {
    @Override
    public CharSequence ping() throws AvroRemoteException {
        log.trace("called ping()... return PONG");
        return "PONG";
    }

    @Override
    public boolean persist(Entity entity) throws AvroRemoteException {
        log.trace("persist entity... entity=[{}]", entity);
        return true;
    }

    @Override
    public boolean persistAll(List<Entity> entities) throws AvroRemoteException {
        log.trace("persist entities... entities=[{}]", entities);
        return true;
    }

    @Override
    public boolean delete(Entity entity) throws AvroRemoteException {
        log.trace("delete entity... entity=[{}]", entity);
        return true;
    }

    @Override
    public boolean deleteById(long id) throws AvroRemoteException {
        log.trace("delete by id... id=[{}]", id);
        return true;
    }

    @Override
    public Void clearAll() throws AvroRemoteException {
        try {
            Thread.sleep(1000);
            log.trace("clear all...");
        } catch (InterruptedException ignored) {}
        return null;
    }
}
