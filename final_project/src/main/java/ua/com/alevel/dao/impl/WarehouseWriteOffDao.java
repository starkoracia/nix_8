package ua.com.alevel.dao.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ua.com.alevel.dao.DaoWarehouseWriteOff;
import ua.com.alevel.entities.WarehouseWriteOff;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class WarehouseWriteOffDao  implements DaoWarehouseWriteOff {

    EntityManager entityManager;

    @Override
    public Boolean create(WarehouseWriteOff writeOff) {
        entityManager.persist(writeOff);
        if(writeOff.getId() != null) {
            return true;
        }
        return false;
    }

    @Override
    public void update(WarehouseWriteOff writeOff) {
        entityManager.merge(writeOff);
    }

    @Override
    public void delete(WarehouseWriteOff writeOff) {
        entityManager.remove(writeOff);
    }

    @Override
    public boolean existById(Long id) {
        Query query = entityManager
                .createQuery("select count(wo) from WarehouseWriteOff wo where wo.id = :id")
                .setParameter("id", id);
        return (Long) query.getSingleResult() == 1;
    }

    @Override
    public Optional<WarehouseWriteOff> findById(Long id) {
        Query query = entityManager
                .createQuery("select wo from WarehouseWriteOff wo where wo.id = :id")
                .setParameter("id", id);
        WarehouseWriteOff writeOff = (WarehouseWriteOff) query.getSingleResult();
        return Optional.ofNullable(writeOff);
    }

    @Override
    public List<WarehouseWriteOff> findAll() {
        List<WarehouseWriteOff> writeOffList = (List<WarehouseWriteOff>) entityManager
                .createQuery("select wo from WarehouseWriteOff wo")
                .getResultList();
        return writeOffList;
    }

    @Override
    public Long count() {
        return (Long) entityManager
                .createQuery("select count(wo) from WarehouseWriteOff wo")
                .getSingleResult();
    }

}
