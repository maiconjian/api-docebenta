package com.doce.benta.generic;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.Transformers;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public abstract class AbstractDao {

    @PersistenceContext
    EntityManager entityManager;

    public EntityManager getEntityManager(){
        return entityManager;
    }

    public Query createQuery(String sql){
        return getEntityManager().createQuery(sql);
    }

    public Query createNativeQuery(String sql){
        return getEntityManager().createNativeQuery(sql);
    }

    public List returnMappedQueryList(Query query){
        return query.unwrap(NativeQuery.class).setResultListTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
    }

    public void executeNativeQuery(String sql){
        createNativeQuery(sql).executeUpdate();
    }
}
