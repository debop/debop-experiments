/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package kr.experiments.avro.rpc;

@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public interface EntityService {
    public static final org.apache.avro.Protocol PROTOCOL = org.apache.avro.Protocol.parse("{\"protocol\":\"EntityService\",\"namespace\":\"kr.experiments.avro.rpc\",\"types\":[{\"type\":\"record\",\"name\":\"Entity\",\"fields\":[{\"name\":\"id\",\"type\":\"long\"},{\"name\":\"createdTime\",\"type\":\"long\"},{\"name\":\"body\",\"type\":\"string\"},{\"name\":\"attrs\",\"type\":{\"type\":\"map\",\"values\":\"string\",\"java_class\":\"java.util.HashMap\"}}]}],\"messages\":{\"ping\":{\"request\":[],\"response\":\"string\"},\"persist\":{\"request\":[{\"name\":\"entity\",\"type\":\"Entity\"}],\"response\":\"boolean\"},\"persistAll\":{\"request\":[{\"name\":\"entities\",\"type\":{\"type\":\"array\",\"items\":\"Entity\"}}],\"response\":\"boolean\"},\"delete\":{\"request\":[{\"name\":\"entity\",\"type\":\"Entity\"}],\"response\":\"boolean\"},\"deleteById\":{\"request\":[{\"name\":\"id\",\"type\":\"long\"}],\"response\":\"boolean\"},\"clearAll\":{\"request\":[],\"response\":\"null\"}}}");

    java.lang.CharSequence ping() throws org.apache.avro.AvroRemoteException;

    boolean persist(kr.experiments.avro.rpc.Entity entity) throws org.apache.avro.AvroRemoteException;

    boolean persistAll(java.util.List<kr.experiments.avro.rpc.Entity> entities) throws org.apache.avro.AvroRemoteException;

    boolean delete(kr.experiments.avro.rpc.Entity entity) throws org.apache.avro.AvroRemoteException;

    boolean deleteById(long id) throws org.apache.avro.AvroRemoteException;

    java.lang.Void clearAll() throws org.apache.avro.AvroRemoteException;

    @SuppressWarnings("all")
    public interface Callback extends EntityService {
        public static final org.apache.avro.Protocol PROTOCOL = kr.experiments.avro.rpc.EntityService.PROTOCOL;

        void ping(org.apache.avro.ipc.Callback<java.lang.CharSequence> callback) throws java.io.IOException;

        void persist(kr.experiments.avro.rpc.Entity entity, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;

        void persistAll(java.util.List<kr.experiments.avro.rpc.Entity> entities, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;

        void delete(kr.experiments.avro.rpc.Entity entity, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;

        void deleteById(long id, org.apache.avro.ipc.Callback<java.lang.Boolean> callback) throws java.io.IOException;

        void clearAll(org.apache.avro.ipc.Callback<java.lang.Void> callback) throws java.io.IOException;
    }
}