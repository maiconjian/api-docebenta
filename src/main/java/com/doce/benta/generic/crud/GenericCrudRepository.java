package com.doce.benta.generic.crud;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface GenericCrudRepository <T extends AbstractModel,ID> extends CrudRepository<T,ID> {
}
