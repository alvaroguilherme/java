package br.com.atividadepratica2b.model.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class ADAO<T>{

protected Class classe;

public ADAO(Class classe){
    this.classe = classe;
}

protected EntityManager getEntityManager(){
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("AtividadePratica2BPU");
    return emf.createEntityManager();
}

public void insert(T objeto) {
        
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(objeto);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public void update(T objeto) {
        
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            em.merge(objeto);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }    
    
    public void delete(T objeto, Long id) {
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            objeto = (T)em.find(this.classe, id);
            em.remove(objeto);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }     

    public void delete(Long id) {
        EntityManager em = this.getEntityManager();
        em.getTransaction().begin();
        try {
            T objeto = (T)em.find(this.classe, id);
            em.remove(objeto);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    
    public T findById(Long id) {
        
        EntityManager em = this.getEntityManager(); 
        T objeto = null;
        try {
            objeto = (T)em.find(this.classe, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        return objeto;
    }        
    
    public List<T> findAll(){
        
        EntityManager em = this.getEntityManager();    
        List<T> objetos = null;
        try {
            Query query = em.createQuery("from "+this.classe.getSimpleName()+" objetos");
            objetos = query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        return objetos;
    }         
}
