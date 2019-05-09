package SNET.dao;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import SNET.domain.entity.LogIn;
 
@Repository
@Transactional
public class LogInDAO {
 
    @Autowired
    private EntityManager entityManager;
 
    public LogIn findUserAccount(String userName) {
        try {
            String sql = "Select e from " + LogIn.class.getName() + " e " //
                    + " Where e.userName = :userName ";
 
            Query query = entityManager.createQuery(sql, LogIn.class);
            query.setParameter("userName", userName);
 
            return (LogIn) query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
 
}