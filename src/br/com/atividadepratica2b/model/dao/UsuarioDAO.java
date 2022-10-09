package br.com.atividadepratica2b.model.dao;

import br.com.atividadepratica2b.model.entity.Usuario;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UsuarioDAO extends ADAO<Usuario>{
  
    public UsuarioDAO() {
        super(Usuario.class);
    }
    
    public Boolean verify(String email, String senha) {
        
        EntityManager em = this.getEntityManager(); 
        Boolean resultado = false;
        
        try {        
            Query query = em.createQuery("from Usuario u where u.email = :email and u.senha = :senha", Usuario.class);
            query.setParameter("email", email);
            query.setParameter("senha", senha);  
            
            if(query.getResultList().size() > 0){
                resultado = true;
            }
   
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        
        return resultado;
    }     
  
}
